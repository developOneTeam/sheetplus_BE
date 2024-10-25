package sheetplus.checkings.domain.notifications.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("notifications")
@Getter @NoArgsConstructor
@AllArgsConstructor @Builder
public class Notifications {

    @Id
    private Long id; // 이벤트의 고유 ID

    private Long contestId; // 연결된 Contest ID

    private LocalDateTime startTime;
    private String eventName;
    private String contestName;

}
