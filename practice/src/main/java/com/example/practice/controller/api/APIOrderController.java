package com.example.practice.controller.api;

import com.example.practice.dto.APIDataResponse;
import com.example.practice.dto.OrderDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class APIOrderController {

    @GetMapping
    public APIDataResponse<?> findAll() {
        return null;
    }

    @GetMapping("/{id}")
    public APIDataResponse<?> findById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public APIDataResponse<?> register(OrderDto orderDto) {
        return null;
    }

    @PutMapping("/{id}")
    public APIDataResponse<?> modify(@PathVariable Long id, OrderDto orderDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public APIDataResponse<?> remove(@PathVariable Long id) {
        return null;
    }
}
