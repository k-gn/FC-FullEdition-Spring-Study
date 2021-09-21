package com.example.getinline.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 에러를 미리 정의해 놓음으로써 우리가 어떤 에러가 발생할 수 있고, 어떻게 처리하고 있다는 것을 대,내외로 소통하는데 유용하게 사용
    // 미리 정의된 에러를 가지고 처리 -> 프로젝트의 안정성을 높임

    OK(0, ErrorCategory.NORMAL, "Ok"), // 정상 case

    // 4xx error case
    BAD_REQUEST(10000, ErrorCategory.CLIENT_SIDE, "Bad request"),
    SPRING_BAD_REQUEST(10001, ErrorCategory.CLIENT_SIDE, "Spring-detected bad request"),
    VALIDATION_ERROR(10002, ErrorCategory.CLIENT_SIDE, "Validation error"),
    NOT_FOUND(10003, ErrorCategory.CLIENT_SIDE, "Requested resource is not found"),

    // 5xx error case
    INTERNAL_ERROR(20000, ErrorCategory.SERVER_SIDE, "Internal error"),
    SPRING_INTERNAL_ERROR(20001, ErrorCategory.SERVER_SIDE, "Spring-detected internal error"),
    DATA_ACCESS_ERROR(20002, ErrorCategory.SERVER_SIDE, "Data access error");

    private final Integer code; // 임의의 error code
    private final ErrorCategory errorCategory; // error category
    private final String message; // 공통으로 내보낼 error message

    // 사용자 정의 에러 메시지
    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }
    public String getMessage(String message) {
        System.out.println("message : " + message);
        return Optional.ofNullable(message) // null이 넘어올 경우, NPE를 던지지 않고 Optional.empty()와 동일하게 비어 있는 Optional 객체를 얻어옴
                // Predicate<T> 함수형 인터페이스 : T ▶ boolean test(T t) ▶ boolean
                .filter(Predicate.not(String::isBlank)) // 비어있지 않다면, not - 결과를 반전시키는 메서드
                .orElse(this.getMessage());
    }

    // client side error?
    public boolean isClientSideError() {
        return this.getErrorCategory() == ErrorCategory.CLIENT_SIDE;
    }

    // server side error?
    public boolean isServerSideError() {
        return this.getErrorCategory() == ErrorCategory.SERVER_SIDE;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }

    // error category enum
    public enum ErrorCategory {
        NORMAL, CLIENT_SIDE, SERVER_SIDE
    }

}