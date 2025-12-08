package com.springdev.spring.dto;

import com.springdev.spring.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    /**
     * 글 작성 요청(request body)을 받기 위해 만든 DTO
     */
    private String title;
    private String content;

    // 생성자를 사용해서 객체 생성
    public Article toEntity(String author) {
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
