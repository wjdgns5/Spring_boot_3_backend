package com.springdev.spring.dto;

import com.springdev.spring.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    // 생성자를 사용해서 객체 생성
    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }

}
