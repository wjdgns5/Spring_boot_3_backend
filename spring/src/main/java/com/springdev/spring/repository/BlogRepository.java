package com.springdev.spring.repository;

import com.springdev.spring.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * “Article 엔티티를 DB에 CRUD 하기 위해 만든 인터페이스”
 */
public interface BlogRepository extends JpaRepository<Article, Long> {
}
