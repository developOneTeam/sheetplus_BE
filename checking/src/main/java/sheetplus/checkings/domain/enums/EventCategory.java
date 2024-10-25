package sheetplus.checkings.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventCategory {

    EVENT_ONE("개회식"),
    EVENT_TWO("프로젝트 발표"),
    EVENT_THREE("졸업생 토크 콘서트"),
    EVENT_FOUR("전문가 특강"),
    EVENT_FIVE("게임 경진대회"),
    EVENT_SIX("폐회식");

    private String message;




}
