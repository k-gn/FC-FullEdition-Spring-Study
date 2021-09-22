package com.example.getinline.service;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  Event Service
 *
 *  Interface + Class 정석 방식
 *
 */
public interface EventService {

    /**
     * 검색어를 받아서 이벤트 리스트를 반환
     *
     * @param placeId 장소 ID
     * @param eventName 이벤트 이름
     * @param eventStatus 이벤트 상태
     * @param eventStartDatetime 시작시간
     * @param eventEndDatetime 종료시간
     * @return
     */
    List<EventDTO> findEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    );


}
