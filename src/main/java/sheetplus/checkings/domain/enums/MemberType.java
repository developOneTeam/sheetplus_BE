package sheetplus.checkings.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberType {

    NOT_ACCEPT_ADMIN("미승인 관리자"),
    STUDENT("학생"),
    ADMIN("관리자"),
    SUPER_ADMIN("최고관리자");

    private String message;

}
