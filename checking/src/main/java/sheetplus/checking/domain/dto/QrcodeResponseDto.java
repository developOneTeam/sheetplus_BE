package sheetplus.checking.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class QrcodeResponseDto {

    private String studentName;
    private String studentId;
    private String eventName;
    private LocalDateTime participateCreatedTime;
    private LocalDateTime participateUpdatedTime;
    
}
