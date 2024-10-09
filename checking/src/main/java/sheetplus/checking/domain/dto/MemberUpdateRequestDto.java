package sheetplus.checking.domain.dto;


import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class MemberUpdateRequestDto {


    private String name;
    private String studentId;
    private String major;

}
