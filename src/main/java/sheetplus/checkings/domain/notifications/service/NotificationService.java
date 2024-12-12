package sheetplus.checkings.domain.notifications.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sheetplus.checkings.domain.notifications.dto.RedisEventDto;



@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final FirebaseMessaging fcm;

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
                .setTopic(event.getEventId() + "-" + event.getContestId())
                .build();


        try {
            String response = fcm.send(message);
            log.info("메세지 발송 성공: {}", response);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }


    }
}