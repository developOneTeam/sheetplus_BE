package sheetplus.checkings.domain.token.dto;

import lombok.*;
import sheetplus.checkings.domain.member.dto.response.MemberInfoResponseDto;

@Getter @Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String accessToken;
    private MemberInfoResponseDto memberInfo;

}
