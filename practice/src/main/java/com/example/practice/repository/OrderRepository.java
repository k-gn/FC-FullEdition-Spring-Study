package com.example.practice.repository;

import com.example.practice.model.Member;
import com.example.practice.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderRepository {
    
    // 주문 전체 조회
    List<Order> findAll();

    // 주문 상세 조회
    Optional<Order> findById(Long id);
    Optional<Order> findByMid(Long mid);
    Optional<Order> findByAid(Long aid);

    // 주문 등록
    Long save(Order order);

    // 주문 수정
    Long update(Order order);

    // 주문 삭제
    Long delete(Long id);





}
