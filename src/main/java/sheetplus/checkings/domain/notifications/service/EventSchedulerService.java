package sheetplus.checkings.domain.notifications.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.notifications.dto.RedisEventDto;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.notifications.entity.Notifications;
import sheetplus.checkings.domain.notifications.repository.EventRedisRepository;
import sheetplus.checkings.domain.event.repository.EventRepository;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventSchedulerService {


    private final EventRedisRepository eventRedisRepository;
    private final EventRepository eventRepository;
    private final TaskScheduler taskScheduler;
    private final RedisConnectionFactory redisConnectionFactory;
    private final NotificationService notificationService;

    /**
     * 매일 12시 30분에 실행되는 스케줄러
     */
    @Scheduled(cron = "0 30 0 * * ?")
    @Transactional
    public void updateEvents() {
        List<Long> contestIds = getAllContestIds();
        log.info("스케줄러 실행");

        for (Long contestId : contestIds) {
            updateContestEvents(contestId);
        }
    }

    /**
     * 모든 Contest ID를 가져오는 메서드
     * 실제 구현에서는 ContestRepository를 통해 가져옵니다.
     */
    private List<Long> getAllContestIds() {
        return List.of(1L);
    }


    /**
     * 특정 Contest의 이벤트를 업데이트
     */

    public void updateContestEvents(Long contestId) {
        // 1. 이전날 이벤트 삭제
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String yesterdayKeyPattern = "contest:" + contestId + ":events:" + yesterday.toString() + "*";
        log.info("이전날 이벤트 삭제");
        // SCAN을 사용하여 키 패턴에 맞는 모든 키 삭제
        Set<String> keysToDelete = new HashSet<>();
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions()
                    .match(yesterdayKeyPattern).count(1000).build());
            while (cursor.hasNext()) {
                String key = new String(cursor.next(), StandardCharsets.UTF_8);
                keysToDelete.add(key);
            }
            cursor.close();
        }

        if (!keysToDelete.isEmpty()) {
            try (RedisConnection connection = redisConnectionFactory.getConnection()) {
                for (String key : keysToDelete) {
                    connection.del(key.getBytes(StandardCharsets.UTF_8));
                }
            }
        }

        // 2. 오늘 이벤트 조회
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusSeconds(1);
        List<Event> todayEvents = eventRepository
                .findByEventContestIdAndStartTimeBetween(contestId, startOfDay, endOfDay);
        log.info("오늘 이벤트 조회");
        // 3. EventRedisEntity로 변환 및 저장
        List<Notifications> redisEntities = todayEvents.stream().map(event -> Notifications.builder()
                        .id(event.getId())
                        .contestId(contestId)
                        .startTime(event.getStartTime())
                        .eventName(event.getName())
                        .contestName(event.getEventContest() != null
                                ? event.getEventContest().getName() : "이벤트명 없음")
                        .build())
                .collect(Collectors.toList());

        // EventRedisRepository를 사용할 경우, 개별 저장
        eventRedisRepository.saveAll(redisEntities);
        log.info("오늘 이벤트 추가");
        // 4. 알림 스케줄링
        scheduleNotifications(redisEntities);
    }



    /**
     * Redis에 저장된 이벤트를 기반으로 알림을 스케줄링
     */
    private void scheduleNotifications(List<Notifications> redisEntities) {
        for (Notifications event : redisEntities) {
            LocalDateTime startTime = event.getStartTime();
            LocalDateTime notificationTimeBefore = startTime.minusMinutes(10);

            // 시작 10분 전에 알림
            if (notificationTimeBefore.isAfter(LocalDateTime.now())) {
                Instant instantBefore = notificationTimeBefore.atZone(ZoneId.systemDefault()).toInstant();
                taskScheduler.schedule(() ->
                                notificationService.sendNotification(convertToDto(event), "10분 전 알림"),
                        instantBefore
                );
            }

            // 시작 시점에 알림
            if (startTime.isAfter(LocalDateTime.now())) {
                Instant instantStart = startTime.atZone(ZoneId.systemDefault()).toInstant();
                taskScheduler.schedule(() ->
                                notificationService.sendNotification(convertToDto(event), "이벤트 시작 알림"),
                        instantStart
                );
            }
        }
    }

    /**
     * EventRedisEntity를 EventDto로 변환
     */
    private RedisEventDto convertToDto(Notifications entity) {
        return RedisEventDto.builder()
                .startTime(entity.getStartTime())
                .eventName(entity.getEventName())
                .contestName(entity.getContestName())
                .build();
    }

}
