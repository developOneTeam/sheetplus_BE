package sheetplus.checkings.business.qrcode.dto;

import lombok.*;



@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class QrcodeResponseDto {

    private String studentName;
    private String studentId;
    private String eventName;
    
}
