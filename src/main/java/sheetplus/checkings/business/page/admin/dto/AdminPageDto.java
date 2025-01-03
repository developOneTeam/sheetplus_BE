package sheetplus.checkings.business.page.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryResponseDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class AdminPageDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class AdminHomeRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Contest PK",
                example = "1", type = "Long")
        private Long contestId;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class AdminHomeResponseDto{
        // 좌상
        @Schema(description = "Member 수",
                example = "30", type = "String")
        private String memberCounts;
        @Schema(description = "모든 이벤트 유형에 참가한 Member 수",
                example = "0", type = "String")
        private String completeEventMemberCounts;
        @Schema(description = "이벤트 참여 횟수가 1 이상인 Member 수",
                example = "15", type = "String")
        private String moreThanOneCounts;
        @Schema(description = "이벤트 참여 횟수가 5 이상인 Member 수",
                example = "4", type = "String")
        private String moreThanFiveCounts;

        // 우상 제목
        @Schema(description = "Contest 이름",
                example = "contest", type = "String")
        private String contestName;
        // 우상 1
        @Schema(description = "Contest 시작시간",
                example = "2025-01-04 12:09:01", type = "LocalDateTime", pattern = "yyyy-MM-dd HH:mm:ss" )
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime contestStart;
        @Schema(description = "Contest 종료시간",
                example = "2025-01-04 12:09:01", type = "LocalDateTime", pattern = "yyyy-MM-dd HH:mm:ss" )
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime contestEnd;
        // 우상 2

        @Schema(description = "이벤트 장소명",
                example = "location", type = "String")
        private String locationName;

        @Schema(description = "이벤트 장소 수",
                example = "100", type = "String")
        private String locationCounts;
        // 우상 3
        @Schema(description = "남은 이벤트 수",
                example = "30", type = "String")
        private String remainEvents;
        @Schema(description = "종료한 이벤트 수",
                example = "50", type = "String")
        private String finishEvents;
        @Schema(description = "오늘 시작하지 않는 이벤트 수",
                example = "20", type = "String")
        private String notTodayEvents;
        // 우상 3
        @Schema(description = "출품작 학과 수",
                example = "7", type = "String")
        private String entryMajorCounts;
        @Schema(description = "출품작 수",
                example = "1500", type = "String")
        private String entryCounts;
        @Schema(description = "예선 출품작",
                example = "1400", type = "String")
        private String entryPreliminaryCounts;
        @Schema(description = "본선 출품작",
                example = "100", type = "String")
        private String entryFinalCounts;
        // 좌하

        @Schema(description = "전체 이벤트 수",
                example = "100", type = "String")
        private String eventCounts;
        @Schema(description = "모든 이벤트들", implementation = EventResponseDto.class)
        private List<EventResponseDto> allEvents;

        // 우하
        @Schema(description = "모든 출품작들", implementation = EntryResponseDto.class)
        private List<EntryResponseDto> entryPageable;
    }


}
