package sheetplus.checkings.business.email.dto;


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
        private String email;
        private String code;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class EmailRequestDto{
        private String receiver;
    }
}
