package sheetplus.checkings.domain.draw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
        @NotNull(message = "null은 허용하지 않습니다.")
        private Long memberId;
        @NotNull(message = "null은 허용하지 않습니다.")
        private Long ContestId;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String prizeType;
        @NotNull(message = "null은 허용하지 않습니다.")
        private ReceiveCons receiveCons;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class DrawUpdateRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        private Long drawId;
        @NotNull(message = "null은 허용하지 않습니다.")
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
