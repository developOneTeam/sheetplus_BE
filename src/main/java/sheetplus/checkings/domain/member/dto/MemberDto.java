package sheetplus.checkings.domain.member.dto;

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
    public static class MemberLoginRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Email(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+.[a-zA-Z0-9]+$",
                message = "이메일 형식이 올바르지 않습니다.")
        private String email;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String code;
        @NotNull(message = "null은 허용하지 않습니다.")
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
    public static class MemberInfoResponseDto{
        private Long id;
        private String name;
        private String studentId;
        private String major;
        private String email;
        private MemberType memberType;

    }


}
