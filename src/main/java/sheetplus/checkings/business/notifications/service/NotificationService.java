package sheetplus.checkings.business.notifications.service;

import com.google.firebase.ErrorCode;
import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import sheetplus.checkings.domain.enums.SendingType;
import sheetplus.checkings.exception.exceptionMethod.CustomFirebaseMessagingException;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService implements Job {

    private final FirebaseMessaging fcm;
    private final EventSchedulerService eventSchedulerService;


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
            String response = fcmSend(message);
            log.info("메세지 발송 성공: {}", response);
            if(title.equals("이벤트 시작 10분 전 알림")){
                eventSchedulerService.sendStatusChange((Long) jobDetail.getJobDataMap().get("eventId"),
                        SendingType.EVENT_BEFORE_10MINUTES);
            }

            if(title.equals("이벤트 시작 알림")){
                eventSchedulerService.sendStatusChange((Long) jobDetail.getJobDataMap().get("eventId"),
                        SendingType.EVENT_START_NOW);
            }

        } catch (CustomFirebaseMessagingException | FirebaseException e) {
            log.error("토픽구독과정 오류 발생: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Retryable(
            retryFor = {CustomFirebaseMessagingException.class},
            exceptionExpression = "errorCode == ErrorCode.UNAVAILABLE || errorCode == ErrorCode.INTERNAL",
            maxAttempts = 5,
            backoff = @Backoff(
                    random = true,
                    delay = 10000,
                    multiplier = 3,
                    maxDelay = 600000
            )
    )
    public String fcmSend(Message message) throws FirebaseMessagingException {
        try{
            return fcm.send(message);
        }catch (FirebaseMessagingException e) {
            log.error("예외 발생: {}", e.getErrorCode());
            throw new CustomFirebaseMessagingException(e);
        }
    }

}
