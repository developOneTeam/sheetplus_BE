package sheetplus.checkings.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AcceptCons {

    ADMIN_ACCEPT("승인된 ADMIN 계정입니다"),
    ADMIN_NOT_ACCEPT("미승인된 ADMIN 계정입니다.");

    private String message;

}
