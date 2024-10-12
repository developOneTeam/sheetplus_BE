package sheetplus.checking.domain.dto;

import lombok.*;

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
