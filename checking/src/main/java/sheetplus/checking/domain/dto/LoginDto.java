package sheetplus.checking.domain.dto;

import lombok.*;
import sheetplus.checking.domain.entity.enums.MemberType;

@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class LoginDto {

    Long id;
    private String email;
    private MemberType memberType;

}
