package sheetplus.checkings.exception.exceptionMethod;


import com.google.firebase.ErrorCode;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class CustomFirebaseMessagingException extends RuntimeException{
    public final ErrorCode errorCode;

    public CustomFirebaseMessagingException(FirebaseMessagingException e) {
        this.errorCode = e.getErrorCode();
        log.error("설정 에러코드: {}", errorCode);
    }

    public CustomFirebaseMessagingException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
