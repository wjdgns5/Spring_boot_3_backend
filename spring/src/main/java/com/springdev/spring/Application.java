package com.springdev.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 엔티티의 생성 시간/수정 시간(그리고 작성자/수정자)을 자동으로 채워 주는 기능
@EnableJpaAuditing // created_at, updated_at 자동 업데이트 Auditing : 감시
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
