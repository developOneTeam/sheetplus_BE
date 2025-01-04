package sheetplus.checkings.business.page.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class StudentPageDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class StudentPageActivitiesResponseDto{
        @Schema(description = "모든 Activities", implementation = ActivitiesResponseDto.class)
        private ActivitiesResponseDto activitiesResponseDto;
        @Schema(description = "모든 즐겨찾기들", implementation = FavoriteResponseDto.class)
        private List<FavoriteResponseDto> favorites;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class StudentHomePageResponseDto{
        @Schema(description = "학생 이름",
                example = "studentMember", type = "String")
        private String studentName;
        @Schema(description = "학생 전공",
                example = "studentMajor", type = "String")
        private String studentMajor;
        @Schema(description = "전체 이벤트 수",
                example = "100", type = "String")
        private String eventCounts;

        @Schema(description = "모든 이벤트들", implementation = EventResponseDto.class)
        private List<EventResponseDto> events;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ActivitiesResponseDto{
        @Schema(description = "전체 이벤트 수",
                example = "100", type = "String")
        private String eventCounts;
        @Schema(description = "모든 이벤트들", implementation = EventResponseDto.class)
        private List<EventResponseDto> events;
    }


}
