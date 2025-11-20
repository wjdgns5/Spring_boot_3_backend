package com.springdev.spring.service;

import com.springdev.spring.domain.Article;
import com.springdev.spring.dto.AddArticleRequest;
import com.springdev.spring.dto.UpdateArticleRequest;
import com.springdev.spring.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    // AddArticleRequest --> DTO
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        System.out.println("----------BlogService----------");
        System.out.println("long id : " + id);
        System.out.println("request.getTitle() : " + request.getTitle());
        System.out.println("request.getContent() : " + request.getContent());
        System.out.println("-----------------------------------");

        article.update(request.getTitle(), request.getContent());

        return article;
    }

}
