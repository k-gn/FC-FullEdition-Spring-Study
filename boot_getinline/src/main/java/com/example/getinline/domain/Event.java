package com.example.getinline.domain;

import com.example.getinline.constant.EventStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    private Long id;

    private Long placeId;
    private String eventName;
    private EventStatus eventStatus;
    private LocalDateTime eventStartDatetime;
    private LocalDateTime eventEndDatetime;
    private Integer currentNumberOfPeople; // 현재 사람 수
    private Integer capacity; // 최대 수용 인원
    private String memo;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}