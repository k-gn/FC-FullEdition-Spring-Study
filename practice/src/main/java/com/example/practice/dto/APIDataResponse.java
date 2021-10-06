package com.example.practice.dto;

import com.example.practice.constant.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// 응답 클래스
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class APIDataResponse<T> extends APIErrorResponse {
    private final T data; // 에러 없을 경우 응답할 데이터

    public APIDataResponse(T data) {
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    // 해당 데이터를 가진 응답 데이터 생성
    public static <T> APIDataResponse<T> of(T data) {
        return new APIDataResponse<>(data);
    }

    // 빈 응답 데이터 생성
    public static <T> APIDataResponse<T> empty() {
        return new APIDataResponse<>(null);
    }



}
