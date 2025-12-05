package com.springdev.spring.service;

import com.springdev.spring.domain.User;
import com.springdev.spring.dto.AddUserRequest;
import com.springdev.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// GPT가 JWT 구현 하기 위해서 import 추가 하라고 함
import com.springdev.spring.config.jwt.TokenProvider;
import com.springdev.spring.domain.RefreshToken;
import com.springdev.spring.repository.RefreshTokenRepository;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserService {

    /**
     * “회원가입 기능을 담당하기 위해 만든 서비스”
     */

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenProvider tokenProvider;                // ✅ 추가
    private final RefreshTokenRepository refreshTokenRepository; // ✅ 추가

    // 회원가입
    public Long save(AddUserRequest dto) {
        // 1) 유저 저장
        User user = userRepository.save(User.builder()
                .email(dto.getEmail())
                // 패스워드 암호화
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build());

        // 2) 회원가입 시 Refresh Token 생성 (연습용으로 여기서 만듦)
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(14));

        // 3) Refresh Token DB 저장
        refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));

        // 4) 원래처럼 userId 리턴
        return user.getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

}
