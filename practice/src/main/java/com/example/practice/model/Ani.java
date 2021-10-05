package com.example.practice.model;

import com.example.practice.constant.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ani {

    private Long id;

    private String title;

    private String content;

    private Genre genre;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
