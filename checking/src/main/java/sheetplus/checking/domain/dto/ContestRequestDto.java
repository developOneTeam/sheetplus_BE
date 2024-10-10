package sheetplus.checking.domain.dto;


import lombok.*;
import sheetplus.checking.domain.entity.enums.ContestCondition;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ContestRequestDto {

    private String name;
    private String startDateTime;
    private String endDateTime;
    private ContestCondition condition;

}
