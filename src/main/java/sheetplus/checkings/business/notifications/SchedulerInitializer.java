package sheetplus.checkings.business.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sheetplus.checkings.business.notifications.service.EventSchedulerService;

import javax.annotation.PreDestroy;


@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final EventSchedulerService eventSchedulerService;
    private final Scheduler scheduler;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        eventSchedulerService.initializeScheduler();
    }


    @PreDestroy
    public void shutdownScheduler(){
        try {
            scheduler.shutdown(true);
            log.info("스케줄러 종료 완료");
        } catch (SchedulerException e) {
            log.error("스케줄러 종료 작업 에러 발생: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
