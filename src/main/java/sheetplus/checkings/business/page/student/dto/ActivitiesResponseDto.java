package sheetplus.checkings.business.page.student.dto;

import lombok.*;
import sheetplus.checkings.domain.event.dto.response.EventResponseDto;
import sheetplus.checkings.domain.favorite.dto.response.FavoriteResponseDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivitiesResponseDto {

    private String eventCounts;
    private List<EventResponseDto> events;

}
