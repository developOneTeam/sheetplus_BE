package sheetplus.checkings.domain.favorite.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FavoriteDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Favorite Request Dto", contentMediaType = "application/json")
    public static class FavoriteRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Contest PK",
                example = "1", type = "Long")
        private Long contestId;

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Event PK",
                example = "1", type = "Long")
        private Long eventId;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "DeviceToken - FCM에서 발급한 유저 디바이스 토큰입니다.",
                example = "test123device4567token", type = "String")
        private String deviceToken;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Favorite Create Response Dto", contentMediaType = "application/json")
    public static class FavoriteCreateResponseDto{
        @Schema(description = "Favorite PK",
                example = "1", type = "Long")
        private Long favoriteId;

        @Schema(description = "Member 학번",
                example = "20191511", type = "String")
        private String studentId;

        @Schema(description = "Contest 이름",
                example = "contest", type = "String")
        private String contestName;

        @Schema(description = "Event 이름",
                example = "event", type = "String")
        private String eventName;

        @Schema(description = "HATEOAS 링크 - ref와 href만 제공")
        private List<Link> links;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Favorite Response Dto", contentMediaType = "application/json")
    public static class FavoriteResponseDto{
        @Schema(description = "Favorite PK",
                example = "1", type = "Long")
        private Long favoriteId;

        @Schema(description = "이벤트 이름",
                example = "1", type = "String")
        private String eventName;

        @Schema(description = "이벤트 시작시간",
                example = "2025-01-04 12:09:01", type = "LocalDateTime", pattern = "yyyy-MM-dd HH:mm:ss" )
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startTime;

        @Schema(description = "이벤트 시작시간",
                example = "2025-01-04 12:09:01", type = "LocalDateTime", pattern = "yyyy-MM-dd HH:mm:ss" )
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endTime;

        @Schema(description = "이벤트 장소",
                example = "location", type = "String")
        private String location;

        @Schema(description = "이벤트 건물",
                example = "building", type = "String")
        private String building;

        @Schema(description = "이벤트 발표자",
                example = "speakerName", type = "String")
        private String speakerName;

        @Schema(description = "Member 전공",
                example = "major", type = "String")
        private String major;

        @Schema(description = "이벤트 진행상태",
                example = "EVENT_PROGRESS", type = "String")
        private String conditionMessage;

        @Schema(description = "이벤트 유형 - 스탬프지급 여부",
                example = "1", type = "CHECKING")
        private String eventTypeMessage;

        @Schema(description = "이벤트 분류 - 이벤트 그룹",
                example = "EVENT_ONE", type = "String")
        private String categoryMessage;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Subscribe Response Dto", contentMediaType = "application/json")
    public static class SubScribeResponseDTO{
        @Schema(description = "구독 작업상태 유형",
                example = "SUCCESS", type = "String")
        private String statusType;

        @Schema(description = "구독 작업상태 메세지",
                example = "작업 성공", type = "String")
        private String statusMessage;

    }

}
