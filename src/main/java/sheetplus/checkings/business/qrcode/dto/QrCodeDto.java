package sheetplus.checkings.business.qrcode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.util.List;

@Getter
@NoArgsConstructor
public class QrCodeDto {

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class QrcodeCreateResponseDto{
        private String secureId;
        private String secretKey;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class QrcodeRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String secureCode;
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        private String secureExpireTime;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class QrcodeResponseDto{
        private String studentName;
        private String studentId;
        private String eventName;
        private List<Link> link;
    }


}
