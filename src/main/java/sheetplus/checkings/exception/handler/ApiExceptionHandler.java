package sheetplus.checkings.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sheetplus.checkings.exception.error.ErrorResponse;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import static sheetplus.checkings.exception.error.ApiError.HTTP_INPUT_NOT_READABLE;

@RestControllerAdvice
@Slf4j
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponse> apiExceptionHandler(
            ApiException apiException
    ){

        log.error("{}", apiException.getErrorDescription());

        return ResponseEntity
                .status(apiException.getErrorCodeIfs().getHttpStatusCode())
                .body(
                        ErrorResponse.ERROR(apiException.getErrorCodeIfs().getHttpStatusCode(),
                                apiException.getErrorDescription())
                );
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException e
    ){

        return ResponseEntity
                .status(HTTP_INPUT_NOT_READABLE.getHttpStatusCode())
                .body(ErrorResponse.ERROR(HTTP_INPUT_NOT_READABLE.getHttpStatusCode(),
                        HTTP_INPUT_NOT_READABLE.getErrorDescription()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(
            IllegalArgumentException e
    ){
        log.error("{}", e.getMessage());

        return ResponseEntity
                .status(HTTP_INPUT_NOT_READABLE.getHttpStatusCode())
                .body(ErrorResponse.ERROR(HTTP_INPUT_NOT_READABLE.getHttpStatusCode(),
                        HTTP_INPUT_NOT_READABLE.getErrorDescription()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> constraintDeclarationExceptionHandler(
            MethodArgumentNotValidException e
    ){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.ERROR(HttpStatus.BAD_REQUEST,
                        e.getBindingResult().getFieldErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .toList().toString()));
    }

}