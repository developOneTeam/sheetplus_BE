package sheetplus.checking.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor @Builder
public class RedisEventDto {

    private LocalDateTime startTime;
    private String eventName;
    private String contestName;

}
