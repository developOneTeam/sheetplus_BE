package sheetplus.checkings.business.qrcode.dto;

import lombok.*;
import org.springframework.hateoas.Link;

import java.util.List;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class QrcodeResponseDto {

    private String studentName;
    private String studentId;
    private String eventName;
    private List<Link> link;
    
}
