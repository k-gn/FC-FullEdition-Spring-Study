package com.example.practice.controller.api;

import com.example.practice.dto.APIDataResponse;
import com.example.practice.dto.MemberDto;
import com.example.practice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class APIMemberController {

    private final MemberService memberService;

    @GetMapping
    public APIDataResponse<?> findAll() {
        return null;
    }

    @GetMapping("/{id}")
    public APIDataResponse<?> findById(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}")
    public APIDataResponse<?> modify(@PathVariable Long id, @Valid MemberDto memberDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public APIDataResponse<?> remove(@PathVariable Long id) {
        return null;
    }
}
