package sheetplus.checkings.business.qrcode.dto;


import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class QrcodeRequestDto {

    private String secureCode;
    private String secureExpireTime;

}
