package com.example.practice.service;

import com.example.practice.dto.OrderDto;
import com.example.practice.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    // 주문 전체 조회
    List<OrderDto> findAll();

    // 주문 상세 조회
    OrderDto findById(Long id);
    OrderDto findByMid(Long mid);
    OrderDto findByAid(Long aid);

    // 주문 등록
    Long save(OrderDto orderDto);

    // 주문 수정
    Long update(Long id, OrderDto orderDto);

    // 주문 삭제
    Long delete(Long id);
}
