package sheetplus.checkings.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReceiveCons {

    PRIZE_RECEIVED("수령"),
    PRIZE_NOT_RECEIVED("미수령");

    private String message;
}
