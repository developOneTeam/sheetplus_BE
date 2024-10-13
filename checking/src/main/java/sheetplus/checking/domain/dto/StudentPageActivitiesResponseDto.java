package sheetplus.checking.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentPageActivitiesResponseDto {

    private String eventCounts;
    private List<EventResponseDto> events;

}