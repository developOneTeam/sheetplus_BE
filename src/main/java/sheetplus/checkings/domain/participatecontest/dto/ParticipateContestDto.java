package sheetplus.checkings.domain.participatecontest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParticipateContestDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ParticipateInfoResponseDto{
        private Long completeEventMemberCounts;
        private Long moreThanOneCounts;
        private Long moreThanFiveCounts;

    }

}
