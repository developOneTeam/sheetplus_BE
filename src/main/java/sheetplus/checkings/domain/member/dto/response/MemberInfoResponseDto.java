package sheetplus.checkings.domain.member.dto.response;


import lombok.*;
import sheetplus.checkings.domain.enums.MemberType;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class MemberInfoResponseDto {

    private Long id;
    private String name;
    private String studentId;
    private String major;
    private String email;
    private MemberType memberType;

}
