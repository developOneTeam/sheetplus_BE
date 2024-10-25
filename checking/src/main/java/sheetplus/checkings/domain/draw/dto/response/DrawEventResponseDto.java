package sheetplus.checkings.domain.draw.dto.response;

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
