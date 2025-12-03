package com.springdev.spring.dto;

import com.springdev.spring.domain.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {
    /**
     * “단일 게시글을 API 응답(JSON)으로 돌려줄 때 쓰는 DTO”
     */

    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }

}
