package org.sopt.meemong.global.response;

import org.sopt.meemong.global.exception.ErrorCode;

/**
 * API의 성공 및 실패 결과를 일관된 형식으로 전달하는 공통 응답입니다.
 *
 * @param code 응답 결과를 나타내는 코드
 * @param data 성공 시 반환할 데이터
 * @param <T> 응답 데이터 타입
 */
public record CommonResponse<T>(
        String code,
        T data
) {
    /**
     * 데이터를 포함한 성공 응답을 생성합니다.
     *
     * @param data 반환할 데이터
     * @param <T> 응답 데이터 타입
     * @return 성공 공통 응답
     */
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>("SUCCESS", data);
    }

    /**
     * 에러 코드를 포함한 실패 응답을 생성합니다.
     *
     * @param code 응답에 사용할 에러 코드
     * @param <T> 응답 데이터 타입
     * @return 데이터가 없는 실패 공통 응답
     */
    public static <T> CommonResponse<T> failure(ErrorCode code) {
        return new CommonResponse<>(code.getCode(), null);
    }
}
