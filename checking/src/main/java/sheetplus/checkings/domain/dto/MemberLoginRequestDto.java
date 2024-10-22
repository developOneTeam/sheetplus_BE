package sheetplus.checkings.domain.dto;

import lombok.*;
import sheetplus.checkings.domain.entity.enums.MemberType;

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
