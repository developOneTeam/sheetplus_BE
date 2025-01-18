package sheetplus.checkings.business.page.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sheetplus.checkings.domain.event.dto.EventDto.EventExceptLinksResponseDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class StudentPageDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Student Page Activities Response Dto", contentMediaType = "application/json")
    public static class StudentPageActivitiesResponseDto{
        @Schema(description = "모든 Activities", implementation = ActivitiesResponseDto.class)
        private ActivitiesResponseDto activitiesResponseDto;
        @Schema(description = "모든 즐겨찾기들", implementation = FavoriteResponseDto.class)
        private List<FavoriteResponseDto> favorites;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Member 및 Stamp정보 응답 Dto", contentMediaType = "application/json")
    public static class StudentHomeMemberAndStampInfoResponseDto{
        @Schema(description = "학생 이름",
                example = "studentMember", type = "String")
        private String studentName;
        @Schema(description = "학생 전공",
                example = "studentMajor", type = "String")
        private String studentMajor;
        @Schema(description = "이벤트 참여 수",
                example = "2", type = "String")
        private Integer participateEventCounts;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Student Home Page Response Dto", contentMediaType = "application/json")
    public static class StudentHomeEventsInfoResponseDto{

        @Schema(description = "오늘 참여할 수 있는 이벤트들", implementation = EventExceptLinksResponseDto.class)
        private List<EventExceptLinksResponseDto> events;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Activities Response Dto", contentMediaType = "application/json")
    public static class ActivitiesResponseDto{
        @Schema(description = "참여한 이벤트 수",
                example = "3", type = "String")
        private Integer eventCounts;
        @Schema(description = "참여한 대회들", implementation = EventExceptLinksResponseDto.class)
        private List<EventExceptLinksResponseDto> events;
    }


}
