package sheetplus.checking.domain.dto;

import lombok.*;
import sheetplus.checking.domain.entity.enums.ReceiveCons;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DrawEventRequestDto {

    private Long memberId;
    private Long ContestId;
    private String prizeType;
    private ReceiveCons receiveCons;

}
