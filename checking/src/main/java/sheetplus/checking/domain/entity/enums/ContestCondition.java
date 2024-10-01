package sheetplus.checking.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContestCondition {

    BEFORE("시작전"),
    PROGRESS("진행중"),
    FINISH("종료");

    private String message;

}
