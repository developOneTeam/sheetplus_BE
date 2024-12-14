package sheetplus.checkings.config;


import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sheetplus.checkings.business.notifications.service.EventSchedulerService;

@Configuration
public class SchedulerConfig{

    @Bean
    public JobDetail eventScheduleJob() {
        return JobBuilder.newJob(EventSchedulerService.class)
                .withIdentity("eventNotification", "dailyEventNotification")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger eventScheduleTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(eventScheduleJob())
                .withIdentity("eventNotificationTrigger", "dailyEventNotificationTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(23, 41))
                .startNow()
                .build();
    }
}