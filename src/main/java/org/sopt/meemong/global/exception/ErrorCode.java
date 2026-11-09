package org.sopt.meemong.global.exception;

import org.springframework.http.HttpStatus;

/**
 * API 실패 응답에 사용하는 에러 코드와 HTTP 상태를 정의합니다.
 */
public enum ErrorCode {
    INVALID_POST_ID("INVALID_POST_ID", HttpStatus.BAD_REQUEST),
    POST_NOT_FOUND("POST_NOT_FOUND", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final HttpStatus httpStatus;

    ErrorCode(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
