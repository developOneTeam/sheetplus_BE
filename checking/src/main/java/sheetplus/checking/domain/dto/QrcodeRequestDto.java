package sheetplus.checking.domain.dto;


import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class QrcodeRequestDto {

    private String secureCode;

}
