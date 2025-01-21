package sheetplus.checkings.business.page.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryExceptLinksResponseDto;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryResponseDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventExceptLinksResponseDto;
import sheetplus.checkings.domain.participatecontest.dto.ParticipateContestDto.ParticipateInfoResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class AdminPageDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Admin Home Request Dto", contentMediaType = "application/json")
    public static class AdminHomeRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Contest PK",
                example = "1", type = "Long")
        private Long contestId;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Contest Info With Counts", contentMediaType = "application/json")
    public static class ContestInfoWithCounts{
        @Schema(description = "이벤트 명",
                example = "contestName", type = "String")
        private String name;
        @Schema(description = "Contest 시작시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startDate;
        @Schema(description = "Contest 종료시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endDate;

        @Schema(description = "대회 상태",
                example = "대회 진행중", type = "String")
        private String cons;
        @Schema(description = "이벤트 개수",
                example = "50", type = "Integer")
        private Integer eventCounts;
        @Schema(description = "작품 개수",
                example = "50", type = "Integer")
        private Integer entryCounts;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "어드민 페이지 스탬프 미션 완료 통계", contentMediaType = "application/json")
    public static class AdminStampStatsDto{
        private Long memberCounts;
        private ParticipateInfoResponseDto participateInfoResponseDto;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "어드민 페이지 대회정보 통계", contentMediaType = "application/json")
    public static class AdminContestStatsDto{
        // 우상 제목
        @Schema(description = "Contest 이름",
                example = "contest", type = "String")
        private String contestName;
        // 우상 1
        @Schema(description = "Contest 시작시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime contestStart;
        @Schema(description = "Contest 종료시간",
                example = "2025-01-04 12:09:01", type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime contestEnd;
        // 우상 2

        @Schema(description = "이벤트 장소명",
                example = "location", type = "String")
        private String locationName;

        @Schema(description = "이벤트 장소 수",
                example = "100", type = "String")
        private Long locationCounts;
        // 우상 3
        @Schema(description = "남은 이벤트 수",
                example = "30", type = "String")
        private Long remainEvents;
        @Schema(description = "종료한 이벤트 수",
                example = "50", type = "String")
        private Long finishEvents;
        @Schema(description = "오늘 시작하지 않는 이벤트 수",
                example = "20", type = "String")
        private Long notTodayEvents;
        // 우상 3
        @Schema(description = "출품작 학과 수",
                example = "7", type = "String")
        private Long entryMajorCounts;
        @Schema(description = "출품작 수",
                example = "1500", type = "String")
        private Long entryCounts;
        @Schema(description = "예선 출품작",
                example = "1400", type = "String")
        private Long entryPreliminaryCounts;
        @Schema(description = "본선 출품작",
                example = "100", type = "String")
        private Long entryFinalCounts;
    }



    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "어드민 페이지 이벤트 정보 통계", contentMediaType = "application/json")
    public static class AdminEventStatsDto{
        @Schema(description = "전체 이벤트 수",
                example = "100", type = "String")
        private Integer eventCounts;
        @Schema(description = "모든 이벤트들", implementation = EventExceptLinksResponseDto.class)
        private List<EventExceptLinksResponseDto> allEvents;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "어드민 페이지 작품 정보 통계", contentMediaType = "application/json")
    public static class AdminEntryStatsDto{

        @Schema(description = "모든 출품작들", implementation = EntryResponseDto.class)
        private List<EntryExceptLinksResponseDto> entryPageable;
    }

}
