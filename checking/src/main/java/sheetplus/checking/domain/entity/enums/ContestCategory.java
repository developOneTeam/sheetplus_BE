package sheetplus.checking.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContestCategory {

    ONE("개회식"),
    TWO("프로젝트 발표"),
    THREE("졸업생 토크 콘서트"),
    FOUR("전문가 특강"),
    FIVE("게임 경진대회"),
    SIX("폐회식");

    private String message;




}
