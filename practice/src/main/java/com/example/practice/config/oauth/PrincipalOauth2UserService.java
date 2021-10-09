package com.example.practice.config.oauth;

import com.example.practice.config.auth.PrincipalDetails;
import com.example.practice.config.oauth.provider.GoogleUserInfo;
import com.example.practice.config.oauth.provider.NaverUserInfo;
import com.example.practice.config.oauth.provider.OAuth2UserInfo;
import com.example.practice.constant.Auth;
import com.example.practice.model.Member;
import com.example.practice.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRespository memberRespository;

    @Lazy
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 후처리 되는 함수
    // userRequest는 code를 받아서 accessToken을 응답 받은 객체
    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        }

        // 회원가입 진행
        // RegistrationId 로 어떤 소셜인지 확인 가능
        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId();
//        String username = provider + "_" + providerId;
        String password = passwordEncoder.encode("test");
        String email = oAuth2UserInfo.getEmail();

        Member member = memberRespository.findByEmail(email);
        // 최초 로그인 처리
        if(member == null) {
            System.out.println("OAuth 최초 로그인 : 회원가입 처리");
            member = Member.builder()
                    .password(password)
                    .email(email)
                    .auth(Auth.ROLE_USER)
                    .provider(provider)
                    .build();

            memberRespository.save(member);
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }

}
