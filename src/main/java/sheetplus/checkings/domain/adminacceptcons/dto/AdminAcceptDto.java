package sheetplus.checkings.domain.adminacceptcons.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "AdminAccept and Create Request DTO", contentMediaType = "application/json")
    public static class AdminAcceptAndCreateRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @Email(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$",
                message = "이메일 형식이 올바르지 않습니다.")
        @Schema(description = "Member Email",
                example = "1234@sch.ac.kr", type = "String", maxLength = 64,
                pattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$")
        private String email;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "AdminAccept Get Request DTO", contentMediaType = "application/json")
    public static class AdminAcceptListResponseDto{
        @Schema(description = "Member Email",
                example = "1234@sch.ac.kr", type = "String", maxLength = 64,
                pattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$")
        private String email;

        @Schema(description = "Member 이름",
                example = "황제연", type = "String")
        private String name;

        @Schema(description = "Member 학번",
                example = "20191511", type = "String")
        private String studentId;

        @Schema(description = "Member 전공",
                example = "사물인터넷", type = "String")
        private String major;

        @Schema(description = "Admin 승인 상태",
                example = "승인된 ADMIN 계정입니다", type = "String")
        private String acceptCons;
    }

}
