package sheetplus.checkings.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminHomeResponseDto {

    // 좌상
    private String memberCounts;
    private String completeEventMemberCounts;
    private String moreThanOneCounts;
    private String moreThanFiveCounts;

    // 우상 제목
    private String contestName;
    // 우상 1
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime contestStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime contestEnd;
    // 우상 2
    private String locationName;
    private String locationCounts;
    // 우상 3
    private String remainEvents;
    private String finishEvents;
    private String notTodayEvents;
    // 우상 3
    private String entryMajorCounts;
    private String entryCounts;
    private String entryPreliminaryCounts;
    private String entryFinalCounts;
    // 좌하
    private String eventCounts;
    private List<EventResponseDto> allEvents;

    // 우하
    private List<EntryResponseDto> entryPageable;

}
