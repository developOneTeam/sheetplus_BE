package sheetplus.checkings.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sheetplus.checkings.domain.contest.dto.ContestDto.ContestInfoResponseDto;
import sheetplus.checkings.domain.enums.MemberType;
import sheetplus.checkings.domain.token.dto.TokenDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Member Login Request Dto", contentMediaType = "application/json")
    public static class MemberLoginRequestDto{
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

        @NotNull(message = "null은 허용하지 않습니다.")
        @Schema(description = "Member 유형",
                example = "STUDENT", type = "enum", enumAsRef = true)
        private MemberType memberType;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class MemberRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String name;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String studentId;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String major;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String universityEmail;
        @NotNull(message = "null은 허용하지 않습니다.")
        private MemberType memberType;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String code;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class MemberUpdateRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String name;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String studentId;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String major;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class MemberCreateResponseDto{
        private TokenDto tokenDto;
        private List<ContestInfoResponseDto> contestInfoResponseDto;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Schema(description = "Member Info Response Dto", contentMediaType = "application/json")
    public static class MemberInfoResponseDto{
        @Schema(description = "Member PK",
                example = "1", type = "Long")
        private Long id;
        @Schema(description = "Member 이름",
                example = "황제연", type = "String")
        private String name;
        @Schema(description = "Member 학번",
                example = "20191511", type = "String")
        private String studentId;
        @Schema(description = "Member 전공",
                example = "사물인터넷", type = "String")
        private String major;
        @Schema(description = "Member Email",
                example = "1234@sch.ac.kr", type = "String", maxLength = 64,
                pattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$")
        private String email;
        @Schema(description = "Member 유형",
                example = "STUDENT", type = "enum", enumAsRef = true)
        private MemberType memberType;

    }


}
