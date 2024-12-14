package sheetplus.checkings.business.notifications.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.enums.SendingStatus;
import sheetplus.checkings.domain.enums.SendingType;
import sheetplus.checkings.domain.event.dto.response.EventResponseDto;
import sheetplus.checkings.domain.eventSending.entity.EventSending;
import sheetplus.checkings.domain.eventSending.repository.EventSendingRepository;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.event.repository.EventRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.CryptoUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static sheetplus.checkings.exception.error.ApiError.EVENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventSchedulerService implements Job {


    private final EventRepository eventRepository;
    private final ContestRepository contestRepository;
    private final EventSendingRepository eventSendingRepository;
    private final CryptoUtil cryptoUtil;
    private final Scheduler scheduler;

    /**
     * 매일 12시 30분에 실행되는 스케줄러
     * 1. 이전날 이벤트 모두 불러와서 태스크 삭제
     * 2. 당일 이벤트 모두 불러와서 태스크 등록
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // 1. 이전날 이벤트 모두 불러와서 태스크 삭제
        try {
            scheduler.clear();
            log.info("스케줄러에 등록된 모든 이벤트 삭제");
        } catch (SchedulerException e) {
            log.info("스케줄러에 등록된 모든 이벤트 삭제도중 예외 발생: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        // 2. 당일 이벤트 모두 불러와서 태스크 등록
        List<Long> contestIds = getAllContestIds();
        log.info("스케줄러 실행, 스케줄링 대회 수: {} ", contestIds.size());
        for (Long contestId : contestIds) {
            updateContestEvents(contestId);
        }
    }

    /**
     * 모든 Contest ID를 가져오는 메서드
     * 실제 구현에서는 ContestRepository를 통해 가져옵니다.
     */
    private List<Long> getAllContestIds() {
        return contestRepository.findAll().stream()
                .map(Contest::getId)
                .toList();
    }

    /**
     * 특정 Contest의 이벤트를 업데이트
     * 1. 오늘 시작하는 이벤트목록 모두 탐색
     * 2. 이벤트 발송 DB 적재
     * 3. 예약 스케줄 작업 진행
     */
    public void updateContestEvents(Long contestId) {

        // 1. 오늘 이벤트 조회
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusSeconds(1);
        List<Event> todayEvents = eventRepository
                .findByEventContestIdAndStartTimeBetween(contestId, startOfDay, endOfDay);
        log.info("오늘 이벤트 조회, 총 이벤트 수: {}", todayEvents.size());

        // 2. 이벤트발송 데이터 적재
        for (Event todayEvent : todayEvents) {
            scheduleEvent(todayEvent);
        }
        log.info("오늘 이벤트 추가 완료");

        // 3. 알림 스케줄링
        scheduleNotifications(todayEvents);
    }

    public void scheduleEvent(Event event) {
        EventSending eventBefore = EventSending.builder()
                .sendingStatus(SendingStatus.PENDING)
                .eventSendingEvent(event)
                .sendingType(SendingType.EVENT_BEFORE_10MINUTES)
                .build();
        eventSendingRepository.save(eventBefore);
        EventSending eventNow = EventSending.builder()
                .sendingStatus(SendingStatus.PENDING)
                .eventSendingEvent(event)
                .sendingType(SendingType.EVENT_BEFORE_10MINUTES)
                .build();
        eventSendingRepository.saveAndFlush(eventNow);
    }

    /**
     * 1. 전달받은 이벤트들을 불러와서 변수로 활용
     * 2. 이벤트 시작 10분전 알림 작업 + 트리거 설정
     * 3. 이벤트 시작 알림 작업 + 트리거 설정
     * 4. 예약 스케줄링 등록
     */
    private void scheduleNotifications(List<Event> TodayEvents) {
        for (Event event : TodayEvents) {

            // 1번 작업
            LocalDateTime startTime = event.getStartTime();
            LocalDateTime notificationTimeBefore = startTime.minusMinutes(10);
            StringBuilder NotificationSchedule = new StringBuilder()
                    .append(event.getName())
                    .append("-")
                    .append(event.getId())
                    .append("_")
                    .append(event.getStartTime());

            // 2번 작업 시작 10분 전에 알림
            JobDetail jobDetailBeforeEvent = JobBuilder.newJob(NotificationService.class)
                    .withIdentity("EventJob_" + NotificationSchedule + "_BeforeEvent"
                            , "Notification")
                    .usingJobData("eventId", event.getId())
                    .usingJobData("notification Type", "10분 전 알림")
                    .usingJobData("contestId", event.getEventContest().getId())
                    .usingJobData("eventName", event.getName())
                    .build();

            Trigger triggerBeforeEvent = TriggerBuilder.newTrigger()
                    .forJob(jobDetailBeforeEvent)
                    .withIdentity("Trigger_" + NotificationSchedule + "_BeforeEvent"
                            , "Notification")
                    .startAt(Date.from(notificationTimeBefore
                            .atZone(ZoneId.systemDefault())
                            .toInstant()))
                    .build();

            // 3번 작업 시작 시점 알림
            JobDetail jobDetailStartEvent = JobBuilder.newJob(NotificationService.class)
                    .withIdentity("EventJob_" + NotificationSchedule + "_StartEvent"
                            , "Notification")
                    .usingJobData("eventId", event.getId())
                    .usingJobData("notification Type", "이벤트 시작 알림")
                    .usingJobData("contestId", event.getEventContest().getId())
                    .usingJobData("eventName", event.getName())
                    .build();

            Trigger triggerStartEvent = TriggerBuilder.newTrigger()
                    .forJob(jobDetailStartEvent)
                    .withIdentity("Trigger_" + NotificationSchedule + "_StartEvent"
                            , "Notification")
                    .startAt(Date.from(startTime
                            .atZone(ZoneId.systemDefault())
                            .toInstant()))
                    .build();

            // 4번 작업
            try {
                scheduler.scheduleJob(jobDetailBeforeEvent, triggerBeforeEvent);
                scheduler.scheduleJob(jobDetailStartEvent, triggerStartEvent);
                log.info("예약 등록 완료");
            } catch (SchedulerException e) {
                log.error("스케줄링 예외 발생: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }




    /**
     * 스케줄링 끝난 상태에서 신규 이벤트 생성할 경우
     * 1. 오늘 시작하는 이벤트인지 확인
     * 2. 참이라면, 이벤트 발송 DB에 데이터 있는지 확인
     * 3. 없다면 스케줄러에 예약 등록
     * 4. 이어서 INSERT 진행
     */
    @Transactional
    public void scheduleNewEvent(EventResponseDto eventResponseDto){

        // 1번 로직
        if(!eventResponseDto.getStartTime().toLocalDate()
                .isEqual(LocalDate.now())){
            return;
        }

        // 2번 로직
        Long eventId = cryptoUtil.decrypt(eventResponseDto.getSecureId());
        List<EventSending> eventSends = eventSendingRepository
                .findByEventSendingEvent_Id(eventId);

        if(!eventSends.isEmpty()
                || eventSends.getFirst().getSendingStatus()
                .equals(SendingStatus.SENDING)){
            return;
        }

        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new ApiException(EVENT_NOT_FOUND));

        // 3번 로직
        scheduleNotifications(Collections.singletonList(event));


        // 4번 로직
        EventSending eventSending = EventSending.builder()
                .sendingStatus(SendingStatus.PENDING)
                .eventSendingEvent(event)
                .build();

        eventSendingRepository.save(eventSending);
    }


    /**
     * 스케줄링에 등록한 이벤트를 수정한 경우
     * 1. 스케줄러에 등록한 이벤트 job 삭제
     * 2. 업데이트된 시간이 오늘 시작하는 이벤트인지 확인
     * 3. 참이라면 스케줄 예약
     * 4. 이벤트 발송 DB에 INSERT
     *
     */
    @Transactional
    public void scheduleUpdateEvent(EventResponseDto eventResponseDto){
        Long eventId = cryptoUtil.decrypt(eventResponseDto.getSecureId());
        // 1번 로직
        try {
            scheduler.deleteJob(JobKey.jobKey(eventId.toString()));
        } catch (SchedulerException e) {
            log.error("수정요청 작업 삭제중 예외 발생: {}",e.getMessage());
            throw new RuntimeException(e);
        }

        scheduleNewEvent(eventResponseDto);

    }

    /**
     * 스케줄링에 등록한 이벤트를 삭제한 경우
     * 1. 스케줄러에 등록한 이벤트 작업 삭제
     *
     */
    @Transactional
    public void scheduleDeleteEvent(Long eventId){
        // 1번 로직
        try {
            scheduler.deleteJob(JobKey.jobKey(eventId.toString()));
        } catch (SchedulerException e) {
            log.error("삭제요청 작업 삭제중 예외 발생: {}",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * WAS 재시작 시, 초기화 작업
     */
    @Transactional
    public void initializeScheduler(){
        List<Event> events = eventSendingRepository
                .findBySendingStatusNot(SendingStatus.SENDING)
                .stream()
                .map(EventSending::getEventSendingEvent)
                        .toList();
        scheduleNotifications(events);
    }

}
