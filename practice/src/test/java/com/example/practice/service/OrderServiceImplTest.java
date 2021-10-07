package com.example.practice.service;

import com.example.practice.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    void test() {
        orderService.save(OrderDto.builder()
                .aid(2L)
                .mid(1L)
                .info("a1m1")
                .build());
    }

}