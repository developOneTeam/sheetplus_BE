package sheetplus.checking.domain.dto;

import lombok.*;
import sheetplus.checking.domain.entity.enums.ReceiveCondition;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrawUpdateRequestDto {

    private Long drawId;
    private ReceiveCondition receiveCondition;

}
