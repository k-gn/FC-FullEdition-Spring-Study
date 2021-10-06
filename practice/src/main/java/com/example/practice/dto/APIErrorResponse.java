package com.example.practice.dto;

import com.example.practice.constant.ErrorCode;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class APIErrorResponse {

    private final Boolean success; // 에러 발생 여부
    private final Integer errorCode; // 에러 코드
    private final String message; // 에러 메시지

    public static APIErrorResponse of(Boolean success, Integer errorCode, String message) {
        return new APIErrorResponse(success, errorCode, message);
    }

    public static APIErrorResponse of(Boolean success, ErrorCode errorCode) {
        return new APIErrorResponse(success, errorCode.getCode(), errorCode.getMessage());
    }

    public static APIErrorResponse of(Boolean success, ErrorCode errorCode, Exception e) {
        return new APIErrorResponse(success, errorCode.getCode(), errorCode.getMessage(e));
    }

    public static APIErrorResponse of(Boolean success, ErrorCode errorCode, String message) {
        return new APIErrorResponse(success, errorCode.getCode(), errorCode.getMessage(message));
    }
}
