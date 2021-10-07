package com.example.practice.dto;

import com.example.practice.model.Ani;
import com.example.practice.model.Member;
import com.example.practice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private Long id;

    @NotBlank
    private Long aid;

    @NotBlank
    private Long mid;

    @NotBlank
    private String info; // email + title

    public static Order dtoToEntity(OrderDto dto) {

        return Order.builder()
                .aid(dto.getAid())
                .mid(dto.getMid())
                .info(dto.getInfo())
                .build();
    }

    public static OrderDto entityToDto(Order order) {

        return OrderDto.builder()
                .id(order.getId())
                .aid(order.getAid())
                .mid(order.getMid())
                .info(order.getInfo())
                .build();
    }
}
