package com.example.springbootstudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/get")
    public void get(String name) {
        System.out.println(name);
    }

    @PostMapping("/post")
    public void post(String name) {
        System.out.println(name);
    }
}
