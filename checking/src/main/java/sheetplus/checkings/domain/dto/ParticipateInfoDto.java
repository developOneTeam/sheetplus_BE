package sheetplus.checkings.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipateInfoDto {

    private Long completeEventMemberCounts;
    private Long moreThanOneCounts;
    private Long moreThanFiveCounts;

}
