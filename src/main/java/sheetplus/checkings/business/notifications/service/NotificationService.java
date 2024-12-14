package sheetplus.checkings.business.notifications.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;
import sheetplus.checkings.domain.enums.SendingStatus;
import sheetplus.checkings.domain.enums.SendingType;
import sheetplus.checkings.domain.eventSending.entity.EventSending;
import sheetplus.checkings.domain.eventSending.repository.EventSendingRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService implements Job {

    private final FirebaseMessaging fcm;
    private final EventSendingRepository eventSendingRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        JobDetail jobDetail = jobExecutionContext.getJobDetail();

        String title = "이벤트 시작 알림";
        String body = jobDetail.getJobDataMap().get("eventName") + " 행사가 지금 시작됩니다.";

        if (jobDetail.getJobDataMap().get("notification Type").equals("10분 전 알림")) {
            title = "이벤트 시작 10분 전 알림";
            body = jobDetail.getJobDataMap().get("eventName") + " 행사가 10분 후에 시작됩니다.";
        }

        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )
                .setTopic(jobDetail.getJobDataMap().get("eventId")
                        + "-"
                        + jobDetail.getJobDataMap().get("contestId"))
                .build();
        try {
            String response = fcm.send(message);
            log.info("메세지 발송 성공: {}", response);
            if(title.equals("이벤트 시작 10분 전 알림")){
                sendStatusChange((Long) jobDetail.getJobDataMap().get("eventId"),
                        SendingType.EVENT_BEFORE_10MINUTES);
            }

            if(title.equals("이벤트 시작 알림")){
                sendStatusChange((Long) jobDetail.getJobDataMap().get("eventId"),
                        SendingType.EVENT_START_NOW);
            }

        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendStatusChange(Long eventId, SendingType type) {
        EventSending eventSending = eventSendingRepository
                .findByEventSendingEvent_IdAndSendingType(eventId, type);
        eventSending.changeStatus(SendingStatus.SENDING);
        eventSendingRepository.saveAndFlush(eventSending);
    }



}