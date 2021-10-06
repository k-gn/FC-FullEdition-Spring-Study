package com.example.practice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    private Long id;

    private Long aid;

    private Long mid;

    private String info;

    private Member member;
    private Ani ani;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
