package sheetplus.checkings.domain.dto;

import lombok.*;
import sheetplus.checkings.domain.entity.enums.MemberType;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class LoginDto {

    Long id;
    private String email;
    private MemberType memberType;

}
