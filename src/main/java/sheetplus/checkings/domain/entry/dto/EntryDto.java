package sheetplus.checkings.domain.entry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import sheetplus.checkings.domain.enums.EntryType;

import java.util.List;

@Getter
@NoArgsConstructor
public class EntryDto {


    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Entry Request Dto", contentMediaType = "application/json")
    public static class EntryRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "Entry 이름",
                example = "EntryName", type = "String")
        private String name;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "Entry 위치",
                example = "location", type = "String")
        private String location;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "Entry 건물",
                example = "building", type = "String")
        private String building;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "Entry 팀번호",
                example = "12", type = "String")
        private String teamNumber;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "Entry 지도교수명",
                example = "professor", type = "String")
        private String professorName;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "Entry 학과",
                example = "major", type = "String")
        private String major;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "Entry 리더명",
                example = "leaderName", type = "String")
        private String leaderName;

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Entry 유형 - 예선작/본선작 여부",
                example = "FINALS", type = "enum", enumAsRef = true)
        private EntryType entryType;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Entry Info Response Dto", contentMediaType = "application/json")
    public static class EntryInfoResponseDto{
        @Schema(description = "전공 개수",
                example = "7", type = "Long")
        private Long majorCounts;
        @Schema(description = "예선작 개수",
                example = "1400", type = "Long")
        private Long preliminaryCounts;
        @Schema(description = "본선작 개수",
                example = "100", type = "Long")
        private Long finalCounts;
        @Schema(description = "전체 개수",
                example = "1500", type = "Long")
        private Long totalCounts;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Entry Response Dto", contentMediaType = "application/json")
    public static class EntryResponseDto{
        @Schema(description = "Entry PK",
                example = "1", type = "Long")
        private Long id;

        @Schema(description = "Entry 이름",
                example = "name", type = "String")
        private String name;

        @Schema(description = "Entry 위치",
                example = "location", type = "String")
        private String location;

        @Schema(description = "Entry 건물",
                example = "building", type = "String")
        private String building;

        @Schema(description = "Entry 팀번호",
                example = "12", type = "String")
        private String teamNumber;

        @Schema(description = "Entry 학과",
                example = "major", type = "String")
        private String major;

        @Schema(description = "Entry 지도교수명",
                example = "professor", type = "String")
        private String professorName;

        @Schema(description = "Entry 리더명",
                example = "leaderName", type = "String")
        private String leaderName;

        @Schema(description = "Entry 유형 메세지",
                example = "본선작", type = "String")
        private String entryType;

        @Schema(description = "HATEOAS 링크 - ref와 href만 제공")
        private List<Link> link;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "HATEOAS를 제외한 Entry 응답 Dto", contentMediaType = "application/json")
    public static class EntryExceptLinksResponseDto{
        @Schema(description = "Entry PK",
                example = "1", type = "Long")
        private Long id;

        @Schema(description = "Entry 이름",
                example = "name", type = "String")
        private String name;

        @Schema(description = "Entry 위치",
                example = "location", type = "String")
        private String location;

        @Schema(description = "Entry 건물",
                example = "building", type = "String")
        private String building;

        @Schema(description = "Entry 팀번호",
                example = "12", type = "String")
        private String teamNumber;

        @Schema(description = "Entry 학과",
                example = "major", type = "String")
        private String major;

        @Schema(description = "Entry 지도교수명",
                example = "professor", type = "String")
        private String professorName;

        @Schema(description = "Entry 리더명",
                example = "leaderName", type = "String")
        private String leaderName;

        @Schema(description = "Entry 유형 메세지",
                example = "본선작", type = "String")
        private String entryType;

    }


}
