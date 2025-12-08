//package com.springdev.spring.config;
//
//import com.springdev.spring.config.jwt.TokenProvider;
//import com.springdev.spring.service.UserDetailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//
//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig {
//
//    /**
//     * “어떤 URL은 로그인 없이 열고, 어떤 URL은 로그인 필요하게 만들려고 만든 설정 클래스”
//     */
//
//    private final UserDetailService userDetailService;
//    private final TokenProvider tokenProvider; // ✅ 추가
//
//    // 1. 스프링 시큐리티 기능 비활성화
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) ->  web.ignoring()
//                .requestMatchers(toH2Console())
//                .requestMatchers("/static/**");
//    }
//
//    // 2. 특정 HTTP 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/signup", "/user", "/api/token").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/articles", true)
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true)
//                )
//                .csrf(csrf -> csrf.disable());
//
//                // ✅ JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 끼워 넣기
//                http.addFilterBefore(
//                        new TokenAuthenticationFilter(tokenProvider),
//                        UsernamePasswordAuthenticationFilter.class
//        );
//
//        return http.build();  // ✅ 이제 메서드 리턴 타입과 딱 맞음
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            HttpSecurity http,
//            BCryptPasswordEncoder bCryptPasswordEncoder,
//            UserDetailService userDetailService) throws Exception {
//
//        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
//
//        auth
//                .userDetailsService(userDetailService)      // 파라미터 이름과 맞추기
//                .passwordEncoder(bCryptPasswordEncoder);    // 비밀번호 인코더 설정
//
//        return auth.build();                                // AuthenticationManager 반환
//    }
//
//    // 패스워드 인코더로 사용할 빈 등록
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
