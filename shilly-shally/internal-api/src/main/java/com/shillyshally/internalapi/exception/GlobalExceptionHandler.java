package com.shillyshally.internalapi.exception;


import com.shillyshally.internalapi.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
        log.info(String.format("Application Exception : %s", e));
        return ResponseEntity.status(e.getErrorType().getHttpStatus()).body(new ErrorResponse(e.getErrorType()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> runtimeException(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        log.error(String.format("UnHandled Exception : %s\n" + "%s:%s:%s", e, stackTrace[0].getClassName(),
                stackTrace[0].getMethodName(), stackTrace[0].getLineNumber()), e);
        return ResponseEntity.internalServerError().body(new ErrorResponse());
    }
}
