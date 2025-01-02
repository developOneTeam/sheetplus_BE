package sheetplus.checkings.business.page.student.dto;

import jakarta.validation.constraints.NotNull;
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
        private ActivitiesResponseDto activitiesResponseDto;
        private List<FavoriteResponseDto> favorites;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class StudentHomePageResponseDto{
        private String studentName;
        private String studentMajor;
        private String eventCounts;

        private List<EventResponseDto> events;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ActivitiesResponseDto{
        private String eventCounts;
        private List<EventResponseDto> events;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class StudentPageRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        private Long contestId;
    }


}
