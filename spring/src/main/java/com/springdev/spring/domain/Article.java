package com.springdev.spring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id // Id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 +1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", updatable = false) // title 이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", updatable = false)
    private String content;

    // Builder 패턴으로 객체 생성
    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
