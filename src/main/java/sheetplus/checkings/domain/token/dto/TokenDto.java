package sheetplus.checkings.domain.token.dto;

import lombok.*;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;

@Getter @Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String accessToken;
    private String refreshToken;
    private MemberInfoResponseDto memberInfo;

}
