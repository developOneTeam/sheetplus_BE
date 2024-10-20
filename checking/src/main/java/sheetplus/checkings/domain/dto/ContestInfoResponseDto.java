package sheetplus.checkings.domain.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ContestInfoResponseDto {

    private Long contestId;
    private String contestName;

}
