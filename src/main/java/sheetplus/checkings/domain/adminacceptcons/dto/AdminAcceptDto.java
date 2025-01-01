package sheetplus.checkings.domain.adminacceptcons.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminAcceptDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class AdminAcceptAndCreateRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @Email(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$",
                message = "이메일 형식이 올바르지 않습니다.")
        private String email;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class AdminAcceptListResponseDto{
        private String email;
        private String name;
        private String studentId;
        private String major;
        private String acceptCons;
    }

}
