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
import java.util.stream.Collectors;

import static sheetplus.checkings.exception.error.ApiError.EVENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventSchedulerService {


    private final EventRepository eventRepository;
    private final ContestRepository contestRepository;
    private final EventSendingRepository eventSendingRepository;
    private final CryptoUtil cryptoUtil;
    private final Scheduler scheduler;

    /**
     * 특정 Contest의 이벤트를 업데이트
     * 1. 오늘 시작하는 이벤트목록 모두 탐색
     * 2. 이벤트 발송 DB 적재
     * 3. 예약 스케줄 작업 진행
     */
    @Transactional
    public void updateContestEvents(Long contestId) {

        // 1. 오늘 이벤트 조회
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusSeconds(1);
        Set<Event> todayEvents = eventRepository
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

        if(!eventSends.isEmpty()){
            return;
        }

        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new ApiException(EVENT_NOT_FOUND));

        // 3번 로직
        scheduleNotifications(Collections.singleton(event));

        // 4번 로직
        scheduleEvent(event);
    }


    /**
     * 스케줄링에 등록한 이벤트를 수정한 경우
     * 1. 스케줄러에 등록한 이벤트 job 삭제
     * 2. 오늘 시작하는 이벤트인지 확인
     * 3. 업데이트한 이벤트 스케줄러에 등록
     * 4. 연결된 이벤트 발송목록 불러와서, SENDING이면 PENDING으로 변경
     */
    @Transactional
    public void scheduleUpdateEvent(EventResponseDto eventResponseDto){
        Long eventId = cryptoUtil.decrypt(eventResponseDto.getSecureId());
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new ApiException(EVENT_NOT_FOUND));
        // 1번 로직
        deleteJob(event);

        // 2번 로직
        if(!eventResponseDto.getStartTime().toLocalDate()
                .isEqual(LocalDate.now())){
            return;
        }

        // 3번 로직
        scheduleNotifications(Collections.singleton(event));

        // 4번 로직
        List<EventSending> eventSends = eventSendingRepository
                .findByEventSendingEvent_Id(eventId);

        for (EventSending eventSend : eventSends) {
            // 발송 상태가 SENDING인 경우 PENDING 변경 필요
            if(eventSend.getSendingStatus().equals(SendingStatus.SENDING)){
                // 현재시간이 등록시간보다 이전이면 PENDING으로 변경

                // 시작 10분전 알림 설정
                if(LocalDateTime.now().isBefore(eventResponseDto.getStartTime().minusMinutes(10))
                        && eventSend.getSendingType().equals(SendingType.EVENT_BEFORE_10MINUTES)){
                    eventSend.changeStatus(SendingStatus.PENDING);
                    continue;
                }

                // 시작 알림 설정
                if(LocalDateTime.now().isBefore(eventResponseDto.getStartTime())
                        && eventSend.getSendingType().equals(SendingType.EVENT_START_NOW)){
                    eventSend.changeStatus(SendingStatus.PENDING);
                }
            }
        }
    }


    /**
     * 스케줄링에 등록한 이벤트를 삭제한 경우
     * 1. 스케줄러에 등록한 이벤트 작업 삭제
     */
    @Transactional
    public void scheduleDeleteEvent(Long eventId){
        // 1번 로직
        Event event = eventRepository.findById(eventId).orElseThrow
                (() -> new ApiException(EVENT_NOT_FOUND));
        deleteJob(event);
        eventSendingRepository.deleteByEventSendingEvent_Id(eventId);
    }

    /**
     * WAS 재시작 시, 초기화 작업
     */
    @Transactional
    public void initializeScheduler(){
        Set<Event> events = eventSendingRepository
                .findBySendingStatusNot(SendingStatus.SENDING)
                .stream()
                .map(EventSending::getEventSendingEvent)
                .collect(Collectors.toSet());

        scheduleNotifications(events);
        log.info("스케줄러 초기화 작업 완료");
    }

    @Transactional
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
                .sendingType(SendingType.EVENT_START_NOW)
                .build();
        eventSendingRepository.save(eventNow);
    }

    /**
     * 모든 Contest ID를 가져오는 메서드
     */
    @Transactional
    public List<Long> getAllContestIds() {
        return contestRepository.findAll()
                .stream()
                .map(Contest::getId)
                .toList();
    }


    /**
     * 1. 전달받은 이벤트들을 불러와서 변수로 활용
     * 2. 이벤트 시작 10분전 알림 작업 + 트리거 설정
     * 3. 이벤트 시작 알림 작업 + 트리거 설정
     * 4. 예약 스케줄링 등록
     */
    private void scheduleNotifications(Set<Event> TodayEvents) {
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
                    .withIdentity("EventJob_" + NotificationSchedule + "_BeforeEvent")
                    .usingJobData("eventId", event.getId())
                    .usingJobData("notification Type", "10분 전 알림")
                    .usingJobData("contestId", event.getEventContest().getId())
                    .usingJobData("eventName", event.getName())
                    .build();

            Trigger triggerBeforeEvent = TriggerBuilder.newTrigger()
                    .forJob(jobDetailBeforeEvent)
                    .withIdentity("Trigger_" + NotificationSchedule + "_BeforeEvent")
                    .startAt(Date.from(notificationTimeBefore
                            .atZone(ZoneId.systemDefault())
                            .toInstant()))
                    .withSchedule(
                            SimpleScheduleBuilder
                                    .simpleSchedule()
                                    .withMisfireHandlingInstructionFireNow()
                    )
                    .build();

            // 3번 작업 시작 시점 알림
            JobDetail jobDetailStartEvent = JobBuilder.newJob(NotificationService.class)
                    .withIdentity("EventJob_" + NotificationSchedule + "_StartEvent")
                    .usingJobData("eventId", event.getId())
                    .usingJobData("notification Type", "이벤트 시작 알림")
                    .usingJobData("contestId", event.getEventContest().getId())
                    .usingJobData("eventName", event.getName())
                    .build();

            Trigger triggerStartEvent = TriggerBuilder.newTrigger()
                    .forJob(jobDetailStartEvent)
                    .withIdentity("Trigger_" + NotificationSchedule + "_StartEvent")
                    .startAt(Date.from(startTime
                            .atZone(ZoneId.systemDefault())
                            .toInstant()))
                    .withSchedule(
                            SimpleScheduleBuilder
                                    .simpleSchedule()
                                    .withMisfireHandlingInstructionFireNow()
                    )
                    .build();

            // 4번 작업
            try {
                if(LocalDateTime.now().isBefore(notificationTimeBefore)){
                    scheduler.scheduleJob(jobDetailBeforeEvent, triggerBeforeEvent);
                    log.info("10분전 알림 등록: {}", notificationTimeBefore);
                }

                if(LocalDateTime.now().isBefore(startTime)){
                    scheduler.scheduleJob(jobDetailStartEvent, triggerStartEvent);
                    log.info("시작 알림 등록: {}", startTime);
                }
            } catch (SchedulerException e) {
                log.error("스케줄링 예외 발생: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteJob(Event event){
        try {
            StringBuilder deleteScheduleJob = new StringBuilder()
                    .append(event.getName())
                    .append("-")
                    .append(event.getId())
                    .append("_")
                    .append(event.getStartTime());

            scheduler.deleteJob(JobKey
                    .jobKey("EventJob_" + deleteScheduleJob + "_BeforeEvent"));
            scheduler.pauseTrigger(TriggerKey
                    .triggerKey("Trigger_" + deleteScheduleJob + "_BeforeEvent"));
            scheduler.deleteJob(JobKey
                    .jobKey("EventJob_" + deleteScheduleJob + "_StartEvent"));
            scheduler.pauseTrigger(TriggerKey
                    .triggerKey("Trigger_" + deleteScheduleJob + "_StartEvent"));

            log.info("이벤트 삭제 완료");
        } catch (SchedulerException e) {
            log.error("삭제요청 작업 삭제중 예외 발생: {}",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void sendStatusChange(Long eventId, SendingType type) {
        EventSending eventSending = eventSendingRepository
                .findByEventSendingEvent_IdAndSendingType(eventId, type);
        eventSending.changeStatus(SendingStatus.SENDING);
        eventSendingRepository.saveAndFlush(eventSending);
    }

}
