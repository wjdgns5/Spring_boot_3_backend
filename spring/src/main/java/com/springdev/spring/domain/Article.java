package com.springdev.spring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article {
    /**
     * DB 테이블과 1:1 매핑되는 엔티티
     * 블로그 글 1개를 DB에 저장하고 수정할 때 쓰려고 만든 클래스
     * 게시글 작성/조회/수정/삭제 전체의 “핵심 데이터 모델”
     */

    @Id // Id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 +1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false) // title 이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    // Builder 패턴으로 객체 생성
    @Builder
    public Article(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    /**
     * 수정 기능에서 제목/내용을 바꿀 때 사용하려고 만든 메서드.
     * @param title
     * @param content
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @CreatedDate // 엔티티가 생성될 때 생성 시간 지정
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
