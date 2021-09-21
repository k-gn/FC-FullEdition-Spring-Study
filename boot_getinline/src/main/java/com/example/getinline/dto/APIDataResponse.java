package com.example.getinline.dto;

import com.example.getinline.constant.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// 응답 클래스
@Getter
@ToString
@EqualsAndHashCode(callSuper = true) // 상위 클래스도 포함
public class APIDataResponse<T> extends APIErrorResponse { // 타입 안정성을 위해 제네릭 적용
    // 일정한 포맷을 위해 상속, 정상이든 에러든 항상 사용 측에서 늘 일정한 필드를 보장 (따로 검사가 필요하지 않도록 설계)
    private final T data; // 에러 없을 경우 응답할 데이터

    public APIDataResponse(T data) {
        // APIDataResponse 를 쓴다는건 에러가 없다는 것 -> 정상 상황이라 무조건 성공으로 설정
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
