package sheetplus.checkings.domain.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberType {

    STUDENT("학생"),
    ADMIN("관리자"),
    SUPER_ADMIN("최고관리자");

    private String message;

}
