package sheetplus.checkings.business.qrcode.dto;

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
        private String secureCode;
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
