package com.springdev.spring.service;

import com.springdev.spring.domain.Article;
import com.springdev.spring.dto.AddArticleRequest;
import com.springdev.spring.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    // AddArticleRequest --> DTO
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

}
