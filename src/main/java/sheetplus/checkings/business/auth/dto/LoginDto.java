package sheetplus.checkings.business.auth.dto;

import lombok.*;
import sheetplus.checkings.domain.enums.MemberType;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class LoginDto {

    private Long id;
    private String email;
    private MemberType memberType;

}
