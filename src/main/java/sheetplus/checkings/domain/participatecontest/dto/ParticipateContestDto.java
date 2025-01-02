package sheetplus.checkings.domain.participatecontest.dto;

import jakarta.validation.constraints.NotNull;
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
        @NotNull(message = "응답 데이터 검증 오류: null은 허용하지 않습니다.")
        private Long completeEventMemberCounts;
        @NotNull(message = "응답 데이터 검증 오류: null은 허용하지 않습니다.")
        private Long moreThanOneCounts;
        @NotNull(message = "응답 데이터 검증 오류: null은 허용하지 않습니다.")
        private Long moreThanFiveCounts;

    }

}
