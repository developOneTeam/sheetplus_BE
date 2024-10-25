package sheetplus.checkings.business.email.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EmailCheckRequestDto {

    private String email;
    private String code;
}
