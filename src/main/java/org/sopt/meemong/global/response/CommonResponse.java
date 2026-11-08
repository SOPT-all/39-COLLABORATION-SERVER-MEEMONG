package org.sopt.meemong.global.response;

import org.sopt.meemong.global.exception.ErrorCode;

public record CommonResponse<T>(
        String code,
        T data
) {
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>("SUCCESS", data);
    }

    public static <T> CommonResponse<T> failure(ErrorCode code) {
        return new CommonResponse<>(code.getCode(), null);
    }
}
