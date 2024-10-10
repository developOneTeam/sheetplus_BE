package sheetplus.checking.domain.dto;


import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ContestResponseDto {

    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private String condition;

}
