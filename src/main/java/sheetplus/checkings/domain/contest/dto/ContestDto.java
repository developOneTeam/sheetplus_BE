package sheetplus.checkings.domain.contest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sheetplus.checkings.domain.enums.ContestCons;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ContestDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Contest Request Dto", contentMediaType = "application/json")
    public static class ContestRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "Contest 이름",
                example = "contest", type = "String")
        private String name;

        @NotNull(message = "null은 허용하지 않습니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "Contest 시작시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startDateTime;

        @NotNull(message = "null은 허용하지 않습니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "Contest 종료시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endDateTime;

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Contest 유형",
                example = "EVENT_PROGRESS", type = "enum", enumAsRef = true)
        private ContestCons condition;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Contest Response Dto", contentMediaType = "application/json")
    public static class ContestResponseDto{
        @Schema(description = "Contest PK",
                example = "1", type = "Long")
        private Long id;

        @Schema(description = "Contest 이름",
                example = "contest", type = "String")
        private String name;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "Contest 시작시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "Contest 종료시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endDate;
        @Schema(description = "Contest 진행 상태 메세지",
                example = "진행중", type = "String")
        private String condition;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Contest Info Response Dto", contentMediaType = "application/json")
    public static class ContestInfoResponseDto{
        @Schema(description = "Contest PK",
                example = "1", type = "Long")
        private Long contestId;
        @Schema(description = "Contest 이름",
                example = "contest", type = "String")
        private String contestName;
    }

}
