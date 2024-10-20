package sheetplus.checkings.domain.dto;


import lombok.*;
import sheetplus.checkings.domain.entity.enums.ContestCons;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ContestRequestDto {

    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private ContestCons condition;

}
