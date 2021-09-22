package com.example.getinline.dto;

import com.example.getinline.constant.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

// 응답 데이터 테스트의 경우 스프링이 쓰이지 않아서 따로 애노테이션이 필요 없다.
// 처음부터 테스트를 시작하거나, 이미 구현된 코드를 분석해서 테스트를 하거나
// 테스트를 먼저 작성하고, 대응하는 구현 코드 작성 (어려울 경우 조금씩 구현코드 부터 해도 됨)
// red -> green -> refactor 의 반복 (TDD)
@DisplayName("데이터 - API 기본 응답")
class APIDataResponseTest {

    @DisplayName("문자열 데이터가 주어지면, 표준 성공 응답을 생성한다.")
    @Test
    void givenStringData_whenCreatingResponse_thenReturnsSuccessfulResponse() {
        // Given
        String data = "test data";

        // When
        APIDataResponse<String> response = APIDataResponse.of(data);

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("success", true) // 필드의 값 검증
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", data);
    }

    @DisplayName("데이터가 없을 때, 비어있는 표준 성공 응답을 생성한다.")
    @Test
    void givenNothing_whenCreatingResponse_thenReturnsEmptySuccessfulResponse() {
        // Given

        // When
        APIDataResponse<String> response = APIDataResponse.empty();

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", null);
    }
}