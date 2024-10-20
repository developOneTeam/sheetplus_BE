package sheetplus.checkings.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sheetplus.checkings.error.ErrorCodeIfs;
import sheetplus.checkings.exception.ApiException;
import sheetplus.checkings.response.Api;

import static sheetplus.checkings.error.ErrorCode.HTTP_INPUT_NOT_READABLE;

@RestControllerAdvice
@Slf4j
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiExceptionHandler(
            ApiException apiException
    ){

        log.error("{}", apiException.getErrorDescription());

        ErrorCodeIfs errorCodeIfs = apiException.getErrorCodeIfs();
        return ResponseEntity
                .status(errorCodeIfs.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCodeIfs.getHttpStatusCode(),
                                apiException.getErrorDescription())
                );
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Api<?>> httpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException e
    ){

        return ResponseEntity
                .status(HTTP_INPUT_NOT_READABLE.getHttpStatusCode())
                .body(Api.ERROR(HTTP_INPUT_NOT_READABLE.getHttpStatusCode(),
                        HTTP_INPUT_NOT_READABLE.getErrorDescription()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Api<?>> illegalArgumentExceptionHandler(
            IllegalArgumentException e
    ){
        log.info("{}", e.getMessage());

        return ResponseEntity
                .status(HTTP_INPUT_NOT_READABLE.getHttpStatusCode())
                .body(Api.ERROR(HTTP_INPUT_NOT_READABLE.getHttpStatusCode(),
                        HTTP_INPUT_NOT_READABLE.getErrorDescription()));
    }

}