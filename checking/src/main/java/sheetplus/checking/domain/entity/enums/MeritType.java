package sheetplus.checking.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeritType {

    NON_TARGET("비대상"),
    TARGET("수령대상");

    private String message;

}
