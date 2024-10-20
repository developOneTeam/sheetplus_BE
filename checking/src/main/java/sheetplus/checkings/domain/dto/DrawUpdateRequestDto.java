package sheetplus.checkings.domain.dto;

import lombok.*;
import sheetplus.checkings.domain.entity.enums.ReceiveCons;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrawUpdateRequestDto {

    private Long drawId;
    private ReceiveCons receiveCons;

}
