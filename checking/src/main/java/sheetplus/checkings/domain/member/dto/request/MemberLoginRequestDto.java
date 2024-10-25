package sheetplus.checkings.domain.member.dto.request;

import lombok.*;
import sheetplus.checkings.domain.enums.MemberType;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginRequestDto {

    private String email;
    private String code;
    private MemberType memberType;

}
