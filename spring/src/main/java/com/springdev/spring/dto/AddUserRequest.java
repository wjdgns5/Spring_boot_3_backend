package com.springdev.spring.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class AddUserRequest {

    /**
     * “회원가입 폼에서 받은 이메일/비밀번호를 담기 위한 DTO”
     */

    private String email;
    private String password;
}
