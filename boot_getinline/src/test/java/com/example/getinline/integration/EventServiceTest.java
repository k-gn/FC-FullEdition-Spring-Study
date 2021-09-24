package com.example.getinline.integration;

import com.example.getinline.dto.EventDTO;
import com.example.getinline.repository.EventRepository;
import com.example.getinline.service.EventServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class EventServiceTest {

    @Autowired
    private EventServiceImpl eventService;

    @Test
    void givenNothing_whenSearchingEvents_thenReturnsEntireEventList() {
        // Given

        // When
        List<EventDTO> list = eventService.getEvents(3L, null, null, null, null);

        // Then
        assertThat(list).hasSize(0);
    }

}


// 비즈니스 로직 테스트
// 1. 단위 테스트 : 필요로 하는 것들만 사용하여 하나하나 부분적인 동작을 테스트 (mocking, slice test)
// 2. 통합 테스트 : 인증(security) + api 호출 or api 호출 -> 비즈니스 -> 데이터 접근 -> 응답까지의 flow test, 실제 인스턴스를 만들어 테스트