package com.example.getinline.controller.api;

import com.example.getinline.dto.APIDataResponse;
import com.example.getinline.dto.AdminRequest;
import com.example.getinline.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody , view 가 아닌 data 자체를 리턴해준다.
@RequestMapping("/api")
public class APIAuthController {

    @PostMapping("/sign-up")
    public APIDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
        return APIDataResponse.empty();
    }

    @PostMapping("/login")
    public APIDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return APIDataResponse.empty();
    }
}
