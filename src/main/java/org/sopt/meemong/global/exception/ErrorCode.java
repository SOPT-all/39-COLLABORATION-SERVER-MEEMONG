package org.sopt.meemong.global.exception;

import org.springframework.http.HttpStatus;

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
