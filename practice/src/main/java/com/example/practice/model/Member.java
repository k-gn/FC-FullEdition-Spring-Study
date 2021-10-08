package com.example.practice.model;

import com.example.practice.constant.Auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    private Long id;

    private String email;

    private String password;

    private List<Order> orderList;

    private Auth auth;

    private String provider;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
