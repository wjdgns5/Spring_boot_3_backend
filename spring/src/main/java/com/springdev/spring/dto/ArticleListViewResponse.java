package com.springdev.spring.dto;

import com.springdev.spring.domain.Article;
import lombok.Getter;

@Getter
public class ArticleListViewResponse {
    /**
     * 뷰에게 데이터를 전달하기 위한 객체를 생성 DTO
     * “게시글 목록 화면에서 한 줄(row)에 보여줄 데이터용 DTO”
     */
    private final long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }

}
