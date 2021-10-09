package com.example.practice.controller;

import com.example.practice.dto.MemberDto;
import com.example.practice.service.MemberService;
import com.example.practice.util.Script;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public String index(int id) {
        System.out.println(id);
        return "/member/index";
    }

    @GetMapping("/signup")
    public String signup() {
        return "/signup";
    }

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@Valid MemberDto memberDto) {
        memberService.save(memberDto);
        return Script.confirm("회원가입 성공!");
    }
}
