package com.example.practice.service;

import com.example.practice.controller.error.exception.GeneralException;
import com.example.practice.dto.OrderDto;
import com.example.practice.model.Order;
import com.example.practice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderDto::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new GeneralException("없는 주문 입니다."));
        return OrderDto.entityToDto(order);
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
        Order order = OrderDto.dtoToEntity(orderDto);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Long update(Long id, OrderDto orderDto) {
        Order order = OrderDto.dtoToEntity(orderDto);
        order.setId(id);
        return orderRepository.update(order);
    }

    @Override
    public Long delete(Long id) {
        return orderRepository.delete(id);
    }
}
