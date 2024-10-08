package sheetplus.checking.domain.dto;

import lombok.*;
import sheetplus.checking.domain.entity.enums.EventCategory;
import sheetplus.checking.domain.entity.enums.ContestCondition;
import sheetplus.checking.domain.entity.enums.EventType;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class EventRequestDto {

    private String name;
    private String startTime;
    private String endTime;
    private String location;
    private String building;
    private String speakerName;
    private String major;
    private ContestCondition condition;
    private EventType eventType;
    private EventCategory category;

}
