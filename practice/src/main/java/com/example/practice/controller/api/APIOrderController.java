package com.example.practice.controller.api;

import com.example.practice.dto.APIDataResponse;
import com.example.practice.dto.OrderDto;
import com.example.practice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class APIOrderController {

    private final OrderService orderService;

    @GetMapping
    public APIDataResponse<?> findAll() {
        return APIDataResponse.of(orderService.findAll());
    }

    @GetMapping("/{id}")
    public APIDataResponse<?> findById(@PathVariable Long id) {
        return APIDataResponse.of(orderService.findById(id));
    }
}
