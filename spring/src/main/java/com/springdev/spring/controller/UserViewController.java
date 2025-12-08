package com.springdev.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    /**
     * “로그인/회원가입 화면을 보여주려고 만든 컨트롤러”
     *
     * 로그인, 회원가입 경로로 접근하면 View 파일을 연결하는 컨트롤러 생성
     * @return
     */
    @GetMapping("/login")
    public String login() {
        // return "login";
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

}
