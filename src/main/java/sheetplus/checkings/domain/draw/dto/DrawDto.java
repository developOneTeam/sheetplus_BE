package sheetplus.checkings.domain.draw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Draw Request Dto", contentMediaType = "application/json")
    public static class DrawEventRequestDto{

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Member PK",
                example = "1", type = "Long")
        private Long memberId;

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Contest PK",
                example = "1", type = "Long")
        private Long ContestId;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "수상 유형",
                example = "1등상", type = "String")
        private String prizeType;

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "상품 수령 여부",
                example = "PRIZE_RECEIVED", type = "enum", enumAsRef = true)
        private ReceiveCons receiveCons;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Draw Update Response Dto", contentMediaType = "application/json")
    public static class DrawUpdateRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Draw PK",
                example = "1", type = "Long")
        private Long drawId;

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "상품 수령 여부",
                example = "PRIZE_RECEIVED", type = "enum", enumAsRef = true)
        private ReceiveCons receiveCons;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Draw Response Dto", contentMediaType = "application/json")
    public static class DrawEventResponseDto{
        @Schema(description = "Draw PK",
                example = "1", type = "Long")
        private Long drawId;

        @Schema(description = "Member 이름",
                example = "member", type = "String")
        private String memberName;

        @Schema(description = "Member 학번",
                example = "20191511", type = "String")
        private String memberStudentId;

        @Schema(description = "Contest 명",
                example = "contestName", type = "String")
        private String contestName;

        @Schema(description = "수상 유형",
                example = "1등상", type = "String")
        private String prizeType;

        @Schema(description = "상품 수령 여부 메세지",
                example = "수령", type = "String")
        private String receiveConditionMessage;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Draw Update Response Dto", contentMediaType = "application/json")
    public static class DrawUpdateResponseDto{
        @Schema(description = "수상 유형",
                example = "1등상", type = "String")
        private String prizeType;

        @Schema(description = "상품 수령 여부 메세지",
                example = "수령", type = "String")
        private String receiveConditionMessage;
    }

}
