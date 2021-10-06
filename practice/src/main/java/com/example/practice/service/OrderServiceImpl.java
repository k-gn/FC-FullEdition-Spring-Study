package com.example.practice.service;

import com.example.practice.dto.OrderDto;
import com.example.practice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDto> findAll() {
        return null;
    }

    @Override
    public OrderDto findById(Long id) {
        return null;
    }

    @Override
    public OrderDto findByMid(Long mid) {
        return null;
    }

    @Override
    public OrderDto findByAid(Long aid) {
        return null;
    }

    @Override
    public Long save(OrderDto orderDto) {
        return null;
    }

    @Override
    public Long update(Long id, OrderDto orderDto) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }
}
