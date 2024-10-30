package sheetplus.checkings.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.response.Api;

import static sheetplus.checkings.exception.error.ErrorCode.HTTP_INPUT_NOT_READABLE;

@RestControllerAdvice
@Slf4j
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiExceptionHandler(
            ApiException apiException
    ){

        log.error("{}", apiException.getErrorDescription());

        return ResponseEntity
                .status(apiException.getErrorCodeIfs().getHttpStatusCode())
                .body(
                        Api.ERROR(apiException.getErrorCodeIfs().getHttpStatusCode(),
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