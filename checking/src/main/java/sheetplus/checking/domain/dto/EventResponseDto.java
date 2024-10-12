package sheetplus.checking.domain.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class EventResponseDto {

    private String secureId;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String building;
    private String speakerName;
    private String major;
    private String conditionMessage;
    private String eventTypeMessage;
    private String categoryMessage;

}
