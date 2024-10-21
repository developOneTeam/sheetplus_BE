package sheetplus.checkings.domain.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidCons {

    EMAIL_VALID("이메일 검증 미완료"),
    EMAIL_NOT_VALID("이메일 검증 완료");

    private String message;

}
