package sheetplus.checkings.exception.error;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ApiError implements ErrorCodeIfs {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버정보를 찾을 수 없음"),
    CONTEST_NOT_FOUND(HttpStatus.NOT_FOUND, "대회정보를 찾을 수 없음"),
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "이벤트정보를 찾을 수 없음"),
    PARTICIPATE_NOT_FOUND(HttpStatus.NOT_FOUND,"참여자정보를 찾을 수 없음"),
    DRAW_NOT_FOUND(HttpStatus.NOT_FOUND,"추첨정보를 찾을 수 없음"),
    ENTRY_NOT_FOUND(HttpStatus.NOT_FOUND,"작품정보를 찾을 수 없음"),
    TEMPORARY_NOT_FOUND(HttpStatus.NOT_FOUND, "임시멤버정보를 찾을 수 없음"),
    TEMPORARY_NOT_VALID_CODE(HttpStatus.UNAUTHORIZED, "인증된 인증코드가 아닙니다."),
    UNIVERSITY_EMAIL_NOT_VALID(HttpStatus.UNAUTHORIZED, "학교 이메일이 아닙니다."),
    EMAIL_LENGTH_TOO_LONG(HttpStatus.BAD_REQUEST, "이메일 ID 길이가 초과되었습니다. 확인해주세요"),
    EMAIL_NOT_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 일치하지 않습니다. 확인해주세요"),
    EMAIL_NOT_AUTHENTICATE(HttpStatus.UNAUTHORIZED, "인증된 이메일이 아닙니다."),
    TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "유효한 토큰이 아닙니다."),
    SUPER_ADMIN_REGISTER_BLOCK(HttpStatus.FORBIDDEN, "SUPER_ADMIN 회원가입은 개발자에게 요청하세요"),
    ADMIN_NOT_ACCEPT(HttpStatus.FORBIDDEN, "SUPER_ADMIN이 승인한 ADMIN이 아닙니다."),
    ADMIN_EXISTS(HttpStatus.CONFLICT, "가입 등록한 ADMIN 계정이 존재합니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "ADMIN 정보를 찾을 수 없음"),
    MEMBER_EXISTS(HttpStatus.CONFLICT, "이미 회원가입한 멤버입니다."),
    MEMBER_TYPE_DISCREPANCY(HttpStatus.CONFLICT, "요청한 멤버의 타입과 회원가입한 멤버의 타입이 일치하지 않습니다."),
    MEMBER_EMAIL_DISCREPANCY(HttpStatus.CONFLICT, "이메일이 일치하지 않습니다."),
    QR_NOT_VALID(HttpStatus.CONFLICT, "QR코드 인증 이벤트가 아닙니다."),
    EVENT_NOT_PROGRESS(HttpStatus.CONFLICT, "현재 진행중인 이벤트가 아닙니다."),
    EVENT_ALREADY_PARTICIPATE(HttpStatus.CONFLICT, "이미 참여한 행사입니다."),
    REFRESH_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰이거나 찾을 수 없습니다."),
    COMMON_START_AFTER_END(HttpStatus.CONFLICT, "시작시간이 종료시간보다 뒤에 있습니다."),
    CONTEST_EVENT_START_AFTER_END(HttpStatus.CONFLICT, "이벤트 시간은 대회 시작/종료 기간 안에 포함되어야 합니다."),
    FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND, "즐겨찾기정보를 찾을 수 없음"),
    FAVORITE_EXISTS(HttpStatus.CONFLICT, "이미 등록된 즐겨찾기 이벤트입니다."),
    EXPIRED_QR_CODES(HttpStatus.UNAUTHORIZED, "이미 만료된 QR코드 요청입니다."),
    ROLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없는 사용자입니다."),
    HTTP_INPUT_NOT_READABLE(HttpStatus.BAD_REQUEST, "잘못된 HTTP 입력 요청"),
    SUBSCRIBE_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "구독 상태정보를 찾을 수 없습니다."),
    QR_EXPIRED_TIME_NOT_VALID(HttpStatus.BAD_REQUEST, "QR코드 만료시간 복호화에 실패했습니다. 암호화한 날짜 데이터를 확인해주세요")
    ;


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
