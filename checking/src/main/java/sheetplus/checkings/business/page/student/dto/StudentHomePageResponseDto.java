package sheetplus.checkings.business.page.student.dto;

import lombok.*;
import sheetplus.checkings.domain.event.dto.response.EventResponseDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentHomePageResponseDto {

    private String studentName;
    private String studentMajor;

    private String eventCounts;
    private List<EventResponseDto> events;

}
