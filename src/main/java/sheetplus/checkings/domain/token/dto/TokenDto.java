package sheetplus.checkings.domain.token.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;

@Getter @Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Token Dto", contentMediaType = "application/json")
public class TokenDto {

    @Schema(description = "액세스 토큰; 인증 API 요청에 필수적입니다.")
    private String accessToken;
    @Schema(description = "리프래쉬 토큰; 액세스 토큰이 만료되면, 리프래쉬 토큰으로 재발급받습니다.")
    private String refreshToken;
    @Schema(implementation = MemberInfoResponseDto.class)
    private MemberInfoResponseDto memberInfo;

}
