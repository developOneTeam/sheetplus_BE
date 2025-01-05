package sheetplus.checkings.business.email.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class EmailCheckRequestDto {
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Email(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$",
                message = "이메일 형식이 올바르지 않습니다.")
        @Schema(description = "Member Email",
                example = "1234@sch.ac.kr", type = "String", maxLength = 64,
                pattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$")
        private String email;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "인증코드",
                example = "1a2fsd2", type = "String")
        private String code;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Email Request Dto", contentMediaType = "application/json")
    public static class EmailRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Email(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$",
                message = "이메일 형식이 올바르지 않습니다.")
        @Schema(description = "Member Email",
                example = "1234@sch.ac.kr", type = "String", maxLength = 64,
        pattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$")
        private String receiver;
    }
}
