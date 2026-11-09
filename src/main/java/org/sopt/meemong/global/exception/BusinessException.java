package org.sopt.meemong.global.exception;

/**
 * 비즈니스 규칙 위반 시 발생시키는 공통 예외입니다.
 */
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    /**
     * 지정된 에러 코드를 갖는 비즈니스 예외를 생성합니다.
     *
     * @param errorCode 예외 응답에 사용할 에러 코드
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
