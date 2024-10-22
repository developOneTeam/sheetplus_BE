package sheetplus.checkings.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import sheetplus.checkings.domain.entity.enums.EventCategory;
import sheetplus.checkings.domain.entity.enums.ContestCons;
import sheetplus.checkings.domain.entity.enums.EventType;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class EventRequestDto {

    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endTime;
    private String location;
    private String building;
    private String speakerName;
    private String major;
    private ContestCons condition;
    private EventType eventType;
    private EventCategory category;

}
