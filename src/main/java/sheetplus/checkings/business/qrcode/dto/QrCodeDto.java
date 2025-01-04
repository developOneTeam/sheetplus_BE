package sheetplus.checkings.business.qrcode.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(description = "암호화된 Event PK",
                example = "987secure6543Id", type = "String")
        private String secureId;
        @Schema(description = "만료시간을 암호화하는 Key",
                example = "987secure6543Key", type = "String")
        private String secretKey;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class QrcodeRequestDto{
        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "암호화된 Event PK",
                example = "987secure6543code", type = "String")
        private String secureCode;

        @NotNull(message = "null은 허용하지 않습니다.")
        @NotBlank(message = "공백을 허용하지 않습니다.")
        @Schema(description = "암호화된 만료시간",
                example = "12expire345time", type = "String")
        private String secureExpireTime;
    }

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class QrcodeResponseDto{
        @Schema(description = "학생 이름",
                example = "studentMember", type = "String")
        private String studentName;

        @Schema(description = "학생 학번",
                example = "20191511", type = "String")
        private String studentId;

        @Schema(description = "이벤트 명",
                example = "eventName", type = "String")
        private String eventName;

        @Schema(description = "HATEOAS 링크 - ref와 href만 제공")
        private List<Link> link;
    }


}
