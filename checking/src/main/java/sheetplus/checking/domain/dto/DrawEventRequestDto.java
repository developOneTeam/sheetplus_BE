package sheetplus.checking.domain.dto;

import lombok.*;
import sheetplus.checking.domain.entity.enums.ReceiveCondition;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DrawEventRequestDto {

    private Long memberId;
    private Long ContestId;
    private String prizeType;
    private ReceiveCondition receiveCondition;

}
