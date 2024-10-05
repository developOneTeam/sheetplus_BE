package sheetplus.checking.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReceiveCondition {

    RECEIVED("수령"),
    NOT_RECEIVED("미수령");

    private String message;
}
