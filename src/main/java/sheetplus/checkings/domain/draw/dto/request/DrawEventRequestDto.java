package sheetplus.checkings.domain.draw.dto.request;

import lombok.*;
import sheetplus.checkings.domain.enums.ReceiveCons;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DrawEventRequestDto {

    private Long memberId;
    private Long ContestId;
    private String prizeType;
    private ReceiveCons receiveCons;

}
