package com.springdev.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateArticleRequest {
    /**
     * “글 수정 요청을 받기 위한 DTO”
     */
    private String title;
    private String content;
}
