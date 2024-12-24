package sheetplus.checkings.business.qrcode.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrcodeCreateResponseDto {

    private String secureId;
    private String secretKey;

}
