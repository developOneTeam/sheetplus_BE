package sheetplus.checkings.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubscribeStatus {
    PENDING("작업 미완료", "PENDING"),
    SUCCESS("작업 성공", "SUCCESS"),
    FAILED("작업 실패", "FAILED")
    ;

    private String message;
    private String status;
}
