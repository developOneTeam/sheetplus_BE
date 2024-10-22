package sheetplus.checkings.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import sheetplus.checkings.domain.entity.enums.ContestCons;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ContestRequestDto {

    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDateTime;
    private ContestCons condition;

}
