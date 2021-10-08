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

        return APIDataResponse.of(memberService.findAll());
    }

    @GetMapping("/{id}")
    public APIDataResponse<?> findById(@PathVariable Long id) {

        return APIDataResponse.of(memberService.findById(id));
    }

    @PutMapping("/{id}")
    public APIDataResponse<?> modify(@PathVariable Long id, @Valid MemberDto memberDto) {
        memberService.update(id, memberDto);
        return APIDataResponse.empty();
    }

    @DeleteMapping("/{id}")
    public APIDataResponse<?> remove(@PathVariable Long id) {
        memberService.delete(id);
        return APIDataResponse.empty();
    }
}
