package com.example.practice.controller;

import com.example.practice.config.auth.PrincipalDetails;
import com.example.practice.dto.ImageUploadDto;
import com.example.practice.model.Member;
import com.example.practice.service.AniService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/ani")
@RequiredArgsConstructor
public class AniController {

    private final AniService aniService;

    @Value("${file.path}")
    private String uploadFolder;

    @GetMapping
    public String index(@AuthenticationPrincipal Member member) {

        System.out.println("principalDetails : " + member);
        return "/ani/index";
    }

    @PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ModelAndView imageUpload(
            ImageUploadDto imageUploadDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Model model) {
        System.out.println("imageUpload");
        MultipartFile uploadFile = imageUploadDto.getFile();
        UUID uuid = UUID.randomUUID(); // 파일 구분을 위한 값 (유일성이 보장된 암호)
        String imageFileName = uuid + "_" + uploadFile.getOriginalFilename();

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);
        // 통신, I/O -> 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, uploadFile.getBytes()); // 해당 경로에 파일 저장하기
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("image", imageFileName);

        return new ModelAndView("/ani/index", map);
    }
}
