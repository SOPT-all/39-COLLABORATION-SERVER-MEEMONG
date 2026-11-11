package org.sopt.meemong.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.meemong.global.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 애플리케이션 전역에서 발생하는 예외를 공통 응답 형식으로 변환합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 비즈니스 예외를 해당 에러 코드와 HTTP 상태를 포함한 응답으로 변환합니다.
     *
     * @param e 처리할 비즈니스 예외
     * @return 에러 코드에 대응하는 실패 응답
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Void>> handleCustomException(BusinessException e) {
        ErrorCode code = e.getErrorCode();

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(CommonResponse.failure(code));
    }

    /**
     * 요청 인자의 타입 변환 실패를 잘못된 공고 ID 응답으로 변환합니다.
     *
     * @param e 요청 인자의 타입 변환 중 발생한 예외
     * @return 잘못된 공고 ID 실패 응답
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse<Void>> handleTypeMismatch(
            MethodArgumentTypeMismatchException e
    ) {
        ErrorCode code = ErrorCode.INVALID_POST_ID;

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(CommonResponse.failure(code));
    }

    /**
     * 처리되지 않은 예외를 기록하고 내부 서버 오류 응답으로 변환합니다.
     *
     * @param e 처리되지 않은 예외
     * @param request 예외가 발생한 HTTP 요청
     * @return 내부 서버 오류 실패 응답
     */
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
