package sheetplus.checkings.domain.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor @Builder
public class RedisEventDto {

    private String eventId;
    private String contestId;
    private LocalDateTime startTime;
    private String eventName;
    private String contestName;

}
