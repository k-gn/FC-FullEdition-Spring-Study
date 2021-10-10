package com.example.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebExchange;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final MessageSource messageSource;

    @GetMapping("/change")
    @ResponseBody
    public String hello(String lang) {
        return lang;
    }

    @GetMapping("/")
    public String index(Model model, Locale locale) {
        model.addAttribute("language", locale.getLanguage());
        return "index";
    }
}
