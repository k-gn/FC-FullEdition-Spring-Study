package com.example.practice.controller.api;

import com.example.practice.dto.APIDataResponse;
import com.example.practice.dto.MemberDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class APIMemberController {

    @GetMapping
    public APIDataResponse<?> findAll() {
        return null;
    }

    @GetMapping("/{id}")
    public APIDataResponse<?> findById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public APIDataResponse<?> register(MemberDto memberDto) {
        return null;
    }

    @PutMapping("/{id}")
    public APIDataResponse<?> modify(@PathVariable Long id, MemberDto memberDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public APIDataResponse<?> remove(@PathVariable Long id) {
        return null;
    }
}
