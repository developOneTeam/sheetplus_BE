package sheetplus.checkings.domain.notifications.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sheetplus.checkings.config.AsyncConfig;
import sheetplus.checkings.domain.notifications.dto.RedisEventDto;

import java.util.concurrent.ExecutionException;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final FirebaseMessaging fcm;
    private final AsyncConfig notificationFcm;

    /**
     * @param event 이벤트 정보
     * @param messageType 알림 타입 ("10분 전 알림", "이벤트 시작 알림")
     */
    public void sendNotification(RedisEventDto event, String messageType) {
        String title = "이벤트 시작";
        String body = event.getEventName() + " 행사가 지금 시작됩니다.";

        if (messageType.equals("10분 전 알림")) {
            title = "이벤트 시작 10분 전";
            body = event.getEventName() + " 행사가 10분 후에 시작됩니다.";
        }

        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )
                .setTopic(event.getEventId() + event.getContestId())
                .build();

        ApiFuture<String> apiFuture = fcm.sendAsync(message);
        apiFuture.addListener(() ->{
            try {
                String response = apiFuture.get();
                log.info("메세지 발송 성공: {}", response);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, notificationFcm.getNotificationFcmExecutor());


    }
}