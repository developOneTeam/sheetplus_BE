package sheetplus.checkings.domain.dto;


import lombok.*;
import sheetplus.checkings.domain.entity.enums.MemberType;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MemberRequestDto {

    private String name;
    private String studentId;
    private String major;
    private String universityEmail;
    private MemberType memberType;

}
