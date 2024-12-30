package sheetplus.checkings.exception.error;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum TokenError implements ErrorCodeIfs{

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 Access 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "인증 헤더 토큰이 없습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED,"지원하지 않는 토큰입니다."),
    ILLEGAL_TOKEN(HttpStatus.UNAUTHORIZED,"잘못된 작성된 토큰입니다."),
    TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "토큰과 관련해서 알 수 없는 에러가 발생했습니다");

    private final HttpStatus httpStatusCode;
    private final String errorDescription;


    @Override
    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    @Override
    public String getErrorDescription() {
        return errorDescription;
    }
}
