package sheetplus.checkings.domain.draw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sheetplus.checkings.domain.enums.ReceiveCons;

@Getter
@NoArgsConstructor
public class DrawDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class DrawEventRequestDto{
        private Long memberId;
        private Long ContestId;
        private String prizeType;
        private ReceiveCons receiveCons;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class DrawUpdateRequestDto{
        private Long drawId;
        private ReceiveCons receiveCons;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class DrawEventResponseDto{
        private Long drawId;
        private String memberName;
        private String memberStudentId;
        private String contestName;
        private String prizeType;
        private String receiveConditionMessage;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class DrawUpdateResponseDto{
        private String prizeType;
        private String receiveConditionMessage;
    }

}
