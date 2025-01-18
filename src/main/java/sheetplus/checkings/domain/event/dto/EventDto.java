package sheetplus.checkings.domain.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import sheetplus.checkings.domain.enums.ContestCons;
import sheetplus.checkings.domain.enums.EventCategory;
import sheetplus.checkings.domain.enums.EventType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class EventDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Event Request Dto", contentMediaType = "application/json")
    public static class EventRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "이벤트 이름",
                example = "eventName", type = "String")
        private String name;

        @NotNull(message = "null은 허용하지 않습니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 시작시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startTime;

        @NotNull(message = "null은 허용하지 않습니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 시작시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endTime;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "이벤트 장소",
                example = "eventLocation", type = "String")
        private String location;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "이벤트 건물",
                example = "eventBuilding", type = "String")
        private String building;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "이벤트 발표자",
                example = "event 발표자", type = "String")
        private String speakerName;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "이벤트 학과",
                example = "eventMajor", type = "String")
        private String major;

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Contest 진행상태",
                example = "EVENT_PROGRESS", type = "enum", enumAsRef = true)
        private ContestCons condition;

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Event 유형 - 스탬프 지급여부",
                example = "CHECKING", type = "enum", enumAsRef = true)
        private EventType eventType;

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Event 분류 - Event 그룹",
                example = "EVENT_ONE", type = "enum", enumAsRef = true)
        private EventCategory category;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Event Response Dto", contentMediaType = "application/json")
    public static class EventResponseDto{
        @Schema(description = "Event PK",
                example = "1", type = "Long")
        private Long id;

        @Schema(description = "Event 이름",
                example = "eventName", type = "String")
        private String name;

        @Schema(description = "이벤트 시작시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startTime;

        @Schema(description = "이벤트 종료시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
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

        @Schema(description = "HATEOAS 링크 - ref와 href만 제공")
        private List<Link> link;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "HATEOAS를 제외한 Event 응답 DTO", contentMediaType = "application/json")
    public static class EventExceptLinksResponseDto{
        @Schema(description = "Event PK",
                example = "1", type = "Long")
        private Long id;

        @Schema(description = "Event 이름",
                example = "eventName", type = "String")
        private String name;

        @Schema(description = "이벤트 시작시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startTime;

        @Schema(description = "이벤트 종료시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
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

}
