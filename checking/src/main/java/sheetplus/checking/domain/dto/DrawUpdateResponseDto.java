package sheetplus.checking.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrawUpdateResponseDto {

    private String prizeType;
    private String receiveConditionMessage;

}