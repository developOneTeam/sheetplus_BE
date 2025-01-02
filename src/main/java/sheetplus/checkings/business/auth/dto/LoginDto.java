package sheetplus.checkings.business.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sheetplus.checkings.domain.enums.MemberType;


@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class LoginDto {

    @NotNull(message = "null은 허용하지 않습니다.")
    private Long id;
    @NotNull(message = "null은 허용하지 않습니다.")
    @NotBlank(message = "공백을 허용하지 않습니다.")
    @Email(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$",
            message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotNull(message = "MemberType이 null입니다.")
    private MemberType memberType;
}
