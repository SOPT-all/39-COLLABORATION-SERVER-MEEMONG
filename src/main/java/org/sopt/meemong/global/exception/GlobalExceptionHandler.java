package org.sopt.meemong.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.meemong.global.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Void>> handleCustomException(BusinessException e) {
        ErrorCode code = e.getErrorCode();

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(CommonResponse.failure(code));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse<Void>> handleTypeMismatch(
            MethodArgumentTypeMismatchException e
    ) {
        ErrorCode code = ErrorCode.INVALID_POST_ID;

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(CommonResponse.failure(code));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleUnexpectedException(
            Exception e,
            HttpServletRequest request
    ) {
        log.error(
                "Unhandled exception: method={}, uri={}",
                request.getMethod(),
                request.getRequestURI(),
                e
        );

        ErrorCode code = ErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(CommonResponse.failure(code));
    }
}
