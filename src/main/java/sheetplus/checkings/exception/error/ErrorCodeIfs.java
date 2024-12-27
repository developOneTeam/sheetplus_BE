package sheetplus.checkings.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorCodeIfs {

    HttpStatus getHttpStatusCode();
    String getErrorDescription();

}