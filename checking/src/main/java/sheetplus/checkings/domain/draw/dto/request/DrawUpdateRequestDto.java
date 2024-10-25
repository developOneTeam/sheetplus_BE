package sheetplus.checkings.domain.draw.dto.request;

import lombok.*;
import sheetplus.checkings.domain.enums.ReceiveCons;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrawUpdateRequestDto {

    private Long drawId;
    private ReceiveCons receiveCons;

}
