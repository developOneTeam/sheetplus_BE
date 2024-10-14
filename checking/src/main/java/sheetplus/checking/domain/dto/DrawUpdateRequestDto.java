package sheetplus.checking.domain.dto;

import lombok.*;
import sheetplus.checking.domain.entity.enums.ReceiveCons;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrawUpdateRequestDto {

    private Long drawId;
    private ReceiveCons receiveCons;

}
