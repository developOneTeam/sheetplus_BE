package sheetplus.checkings.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs{

    OK(200, "성공"),
    READ(200, "READ"),
    UPDATED(200, "UPDATED"),
    DELETED(200, "DELETED"),
    CREATED(201, "CREATED"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류"),
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN.value(), "권한 없음"),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED.value(), "미인증 요청"),
    HTTP_INPUT_NOT_READABLE(400, "잘못된 HTTP 입력 요청"),
    ROLE_FORBIDDEN_ERROR(403, "접근 권한이 없는 사용자입니다.");

    private final Integer httpStatusCode;   // 클라이언트에 보여줄 status code
    private final String errorDescription;  // 에러 설명
}