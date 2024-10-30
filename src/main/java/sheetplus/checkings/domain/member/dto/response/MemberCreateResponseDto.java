package sheetplus.checkings.domain.member.dto.response;

import lombok.*;
import sheetplus.checkings.domain.contest.dto.response.ContestInfoResponseDto;
import sheetplus.checkings.domain.token.dto.TokenDto;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateResponseDto {
    private TokenDto tokenDto;
    private List<ContestInfoResponseDto> contestInfoResponseDto;
}
