package sheetplus.checkings.domain.contest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    public static class ContestRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String name;
        @NotNull(message = "null은 허용하지 않습니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startDateTime;
        @NotNull(message = "null은 허용하지 않습니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endDateTime;

        @NotNull(message = "null은 허용하지 않습니다.")
        private ContestCons condition;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ContestResponseDto{
        private Long id;
        private String name;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endDate;
        private String condition;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ContestInfoResponseDto{
        private Long contestId;
        private String contestName;
    }

}
