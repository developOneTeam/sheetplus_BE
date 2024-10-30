package sheetplus.checkings.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenError implements ErrorCodeIfs{

    INVALID_TOKEN(2000, "유효하지 않은 Access 토큰입니다."),
    EXPIRED_TOKEN(2001, "만료된 토큰입니다."),
    TOKEN_NOT_FOUND(2002, "인증 헤더 토큰이 없습니다."),
    UNSUPPORTED_TOKEN(2003,"지원하지 않는 토큰입니다."),
    ILLEGAL_TOKEN(2004,"잘못된 작성된 토큰입니다."),
    TOKEN_EXCEPTION(2005, "토큰관련 알 수 없는 에러가 발생했습니다. 개발자에게 연락하세요");

    private final Integer httpStatusCode;
    private final String errorDescription;
}
