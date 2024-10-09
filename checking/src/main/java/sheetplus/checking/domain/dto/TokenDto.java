package sheetplus.checking.domain.dto;

import lombok.*;
import sheetplus.checking.domain.entity.Member;

@Getter @Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String accessToken;
    private String refreshToken;
    private Member memberInfo;

}
