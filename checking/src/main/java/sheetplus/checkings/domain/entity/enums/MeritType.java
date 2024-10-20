package sheetplus.checkings.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeritType {

    PRIZE_NON_TARGET("비대상"),
    PRIZE_TARGET("수령대상");

    private String message;

}
