package sheetplus.checkings.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sheetplus.checkings.domain.contest.dto.ContestDto;
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
        private String email;
        private String code;
        private MemberType memberType;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class MemberRequestDto{
        private String name;
        private String studentId;
        private String major;
        private String universityEmail;
        private MemberType memberType;
        private String code;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class MemberUpdateRequestDto{
        private String name;
        private String studentId;
        private String major;

    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class MemberCreateResponseDto{
        private TokenDto tokenDto;
        private List<ContestDto.ContestInfoResponseDto> contestInfoResponseDto;

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
