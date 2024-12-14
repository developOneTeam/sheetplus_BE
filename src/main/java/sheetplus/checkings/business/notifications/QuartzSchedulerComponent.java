package sheetplus.checkings.business.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;
import sheetplus.checkings.business.notifications.service.EventSchedulerService;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class QuartzSchedulerComponent implements Job {

    private final Scheduler scheduler;
    private final EventSchedulerService eventSchedulerService;

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
        List<Long> contestIds = eventSchedulerService.getAllContestIds();
        log.info("스케줄러 실행, 스케줄링 대회 수: {} ", contestIds.size());
        for (Long contestId : contestIds) {
            eventSchedulerService.updateContestEvents(contestId);
        }
    }

}
