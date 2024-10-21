package sheetplus.checkings.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminAcceptListResponseDto {

    private String email;
    private String name;
    private String studentId;
    private String major;
    private String acceptCons;

}
