package sheetplus.checkings.exception.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiError implements ErrorCodeIfs {

    MEMBER_NOT_FOUND(404, "멤버정보를 찾을 수 없음"),
    CONTEST_NOT_FOUND(404, "대회정보를 찾을 수 없음"),
    EVENT_NOT_FOUND(404, "이벤트정보를 찾을 수 없음"),
    PARTICIPATE_NOT_FOUND(404,"참여자정보를 찾을 수 없음"),
    DRAW_NOT_FOUND(404,"추첨정보를 찾을 수 없음"),
    ENTRY_NOT_FOUND(404,"작품정보를 찾을 수 없음"),
    TEMPORARY_NOT_FOUND(404, "임시멤버정보를 찾을 수 없음"),
    TEMPORARY_NOT_VALID_CODE(403, "인증된 인증코드가 아닙니다."),
    UNIVERSITY_EMAIL_NOT_VALID(403, "학교 이메일이 아닙니다."),
    EMAIL_LENGTH_TOO_LONG(404, "이메일 ID 길이가 초과되었습니다. 확인해주세요"),
    EMAIL_NOT_FORMAT(404, "이메일 형식이 일치하지 않습니다. 확인해주세요"),
    EMAIL_NOT_AUTHENTICATE(403, "인증된 이메일이 아닙니다."),
    TOKEN_NOT_VALID(403, "유효한 토큰이 아닙니다."),
    SUPER_ADMIN_REGISTER_BLOCK(403, "SUPER_ADMIN 회원가입은 개발자에게 요청하세요"),
    ADMIN_NOT_ACCEPT(403, "SUPER_ADMIN이 승인한 ADMIN이 아닙니다."),
    ADMIN_EXISTS(403, "가입 등록한 ADMIN 계정이 존재합니다."),
    ADMIN_NOT_FOUND(404, "ADMIN 정보를 찾을 수 없음"),
    MEMBER_EXISTS(403, "이미 회원가입한 멤버입니다."),
    MEMBER_TYPE_DISCREPANCY(403, "요청한 멤버의 타입과 회원가입한 멤버의 타입이 일치하지 않습니다."),
    MEMBER_EMAIL_DISCREPANCY(403, "이메일이 일치하지 않습니다. 토큰 변조 위험이 있습니다."),
    QR_NOT_VALID(403, "QR코드 인증 대상이 아닙니다."),
    EVENT_NOT_PROGRESS(403, "현재 진행중인 행사가 아닙니다."),
    EVENT_ALREADY_PARTICIPATE(403, "이미 참여한 행사입니다."),
    REFRESH_TOKEN_EXCEPTION(403, "유효하지 않은 토큰이거나 찾을 수 없습니다."),
    COMMON_START_AFTER_END(400, "시작시간이 종료시간보다 뒤에 있습니다."),
    CONTEST_EVENT_START_AFTER_END(400, "이벤트 시간은 대회 시작/종료 기간 안에 포함되어야 합니다."),
    FAVORITE_NOT_FOUND(404, "즐겨찾기정보를 찾을 수 없음"),
    FAVORITE_EXISTS(403, "이미 등록된 즐겨찾기 이벤트입니다."),
    EXPIRED_QR_CODES(403, "이미 만료된 QR코드 요청입니다."),
    ROLE_ACCESS_DENIED(403, "접근 권한이 없는 사용자입니다.")
    ;


    private final Integer httpStatusCode;
    private final String errorDescription;
}
