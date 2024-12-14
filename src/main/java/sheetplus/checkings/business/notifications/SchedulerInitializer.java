package sheetplus.checkings.business.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import sheetplus.checkings.business.notifications.service.EventSchedulerService;


@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final EventSchedulerService eventSchedulerService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        eventSchedulerService.initializeScheduler();
    }
}
