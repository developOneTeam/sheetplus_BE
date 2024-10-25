package sheetplus.checkings.domain.entry.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntryInfoResponseDto {

    private Long majorCounts;
    private Long preliminaryCounts;
    private Long finalCounts;
    private Long totalCounts;

}
