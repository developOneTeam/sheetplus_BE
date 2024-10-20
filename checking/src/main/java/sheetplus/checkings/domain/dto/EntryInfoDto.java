package sheetplus.checkings.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntryInfoDto {

    private Long majorCounts;
    private Long preliminaryCounts;
    private Long finalCounts;
    private Long totalCounts;

}
