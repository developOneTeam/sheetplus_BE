package sheetplus.checkings.domain.contest.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ContestInfoResponseDto {

    private Long contestId;
    private String contestName;

}
