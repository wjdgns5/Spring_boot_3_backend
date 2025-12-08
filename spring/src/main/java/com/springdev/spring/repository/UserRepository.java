package com.springdev.spring.repository;

import com.springdev.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * “User 엔티티를 DB에 CRUD + 이메일로 조회하기 위해 만든 인터페이스”
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // Optional<T>은 “null 일 수도 있는 값을 감싸놓은 상자” --> null이 나올 수 있는 것들은 Optional로 정의가능
    Optional<User> findByEmail(String email); // email로 사용자 정보를 가져옴
}
