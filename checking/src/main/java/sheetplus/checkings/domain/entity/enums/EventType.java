package sheetplus.checkings.domain.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {

    NO_CHECKING("스탬프 미대상"),
    CHECKING("스탬프 대상");

    private String message;

}
