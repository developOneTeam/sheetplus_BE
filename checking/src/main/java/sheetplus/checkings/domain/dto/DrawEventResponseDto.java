package sheetplus.checkings.domain.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DrawEventResponseDto {

    private Long drawId;
    private String memberName;
    private String memberStudentId;
    private String contestName;
    private String prizeType;
    private String receiveConditionMessage;

}
