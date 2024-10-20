package sheetplus.checkings.domain.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sheetplus.checkings.domain.entity.enums.MemberType;

@Getter @Setter
@NoArgsConstructor
public class MemberRequestDto {

    private String name;
    private String studentId;
    private String major;
    private String universityEmail;
    private MemberType memberType;

}
