package com.example.dmaker.dto;

import com.example.dmaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DMakerErrorResponse { // 에러 응답용 dto

    private DMakerErrorCode errorCode;
    private String errorMessage;
}
