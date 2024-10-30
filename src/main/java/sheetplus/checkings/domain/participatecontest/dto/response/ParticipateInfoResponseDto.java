package sheetplus.checkings.domain.participatecontest.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipateInfoResponseDto {

    private Long completeEventMemberCounts;
    private Long moreThanOneCounts;
    private Long moreThanFiveCounts;

}
