package sheetplus.checkings.domain.dto;

import lombok.*;

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
