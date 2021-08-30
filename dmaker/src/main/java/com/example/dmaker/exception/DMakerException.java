package com.example.dmaker.exception;

import lombok.Getter;

// 사용자 정의 예외 클래스
@Getter
public class DMakerException extends RuntimeException {

    private DMakerErrorCode dMakerErrorCode;
    private String detailMessage;

    public DMakerException(DMakerErrorCode errorCode) {
        super(errorCode.getMessage()); // 에러메시지 적용 (상위 클래스의 생성자를 호출하여 예외 메세지를 넘김)
        this.dMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public DMakerException(DMakerErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.dMakerErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
