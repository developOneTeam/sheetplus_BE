package sheetplus.checking.domain.dto;


import lombok.*;
import sheetplus.checking.domain.entity.enums.MemberType;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class MemberInfoDto {

    private Long id;
    private String name;
    private String studentId;
    private String major;
    private String email;
    private MemberType memberType;

}
