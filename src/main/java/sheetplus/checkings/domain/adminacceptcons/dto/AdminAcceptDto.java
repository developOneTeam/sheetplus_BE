package sheetplus.checkings.domain.adminacceptcons.dto;

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
