package sheetplus.checking.domain.dto;


import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class EventResponseDto {

    private String secureId;
    private String name;
    private String startTime;
    private String endTime;
    private String location;
    private String building;
    private String speakerName;
    private String major;
    private String conditionMessage;
    private String eventTypeMessage;
    private String categoryMessage;

}
