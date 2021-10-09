package com.example.practice.dto;

import com.example.practice.constant.Auth;
import com.example.practice.model.Member;
import com.example.practice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Auth auth;

    private String provider;

    private boolean check;

    private List<Order> orderList;

    public static Member dtoToEntity(MemberDto dto) {

        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public static MemberDto entityToDto(Member member) {

        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .auth(member.getAuth())
                .provider(member.getProvider())
                .orderList(member.getOrderList())
                .build();
    }

}
