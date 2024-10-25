package sheetplus.checkings.domain.member.dto.request;


import lombok.*;
import sheetplus.checkings.domain.enums.MemberType;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MemberRequestDto {

    private String name;
    private String studentId;
    private String major;
    private String universityEmail;
    private MemberType memberType;
    private String code;

}
