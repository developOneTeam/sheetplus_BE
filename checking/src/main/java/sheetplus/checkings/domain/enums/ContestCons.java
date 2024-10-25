package sheetplus.checkings.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContestCons {

    EVENT_BEFORE("시작전"),
    EVENT_PROGRESS("진행중"),
    EVENT_FINISH("종료");

    private String message;

}
