package sheetplus.checkings.business.auth.dto;

import lombok.*;
import sheetplus.checkings.domain.enums.MemberType;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class LoginDto {

    Long id;
    private String email;
    private MemberType memberType;

}
