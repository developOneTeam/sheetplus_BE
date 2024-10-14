package sheetplus.checking.domain.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sheetplus.checking.domain.dto.RedisEventDto;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final FirebaseMessaging fcm;
    @Value("${firebase.config.token}")
    private String token;

    /**
     * Firebase Cloud Functions를 통해 모든 사용자에게 알림을 전송하기 위한 요청을 Firestore에 기록
     * @param event 이벤트 정보
     * @param messageType 알림 타입 ("10분 전 알림", "이벤트 시작 알림")
     */
    public void sendNotification(RedisEventDto event, String messageType) {
        String title;
        String body;

        if ("10분 전 알림".equals(messageType)) {
            title = "이벤트 시작 10분 전";
            body = event.getEventName() + " 행사가 10분 후에 시작됩니다.";
        } else if ("이벤트 시작 알림".equals(messageType)) {
            title = "이벤트 시작";
            body = event.getEventName() + " 행사가 지금 시작됩니다.";
        } else {
            title = "이벤트 알림";
            body = event.getEventName() + " 행사가 대한 알림입니다.";
        }

        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )
                .putData("eventName", event.getEventName())
                .setToken(token)
                .build();

        try {
            String response = fcm.send(message);
            log.info("message content {}", message);
            log.info("Successfully sent message: {}", response);
        } catch (Exception e) {
            log.error("알림 전송 실패", e);
        }
    }
}