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
public class Member {

    private Long id;

    private String email;

    private String password;

    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
