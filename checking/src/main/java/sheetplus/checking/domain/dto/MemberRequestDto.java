package sheetplus.checking.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sheetplus.checking.domain.entity.enums.MemberType;

@Getter @Setter
@NoArgsConstructor
public class MemberRequestDto {

    private String name;
    private String studentId;
    private String major;
    private String universityEmail;
    private MemberType memberType;

}
