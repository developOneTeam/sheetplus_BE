package sheetplus.checkings.error;


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
    TOKEN_NOT_VALID(403, "유효한 토큰이 아닙니다."),
    QR_NOT_VALID(403, "QR코드 인증 대상이 아닙니다."),
    EVENT_NOT_PROGRESS(403, "현재 진행중인 행사가 아닙니다."),
    EVENT_ALREADY_PARTICIPATE(403, "이미 참여한 행사입니다.");


    private final Integer httpStatusCode;
    private final String errorDescription;
}
