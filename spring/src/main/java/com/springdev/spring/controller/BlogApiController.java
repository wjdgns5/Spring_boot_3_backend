package com.springdev.spring.controller;

import com.springdev.spring.domain.Article;
import com.springdev.spring.dto.AddArticleRequest;
import com.springdev.spring.dto.ArticleResponse;
import com.springdev.spring.dto.UpdateArticleRequest;
import com.springdev.spring.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BlogApiController {

    /**
     * “/api/articles 로 시작하는 REST API를 처리하려고 만든 컨트롤러”
     */

    private final BlogService blogService;

    // AddArticleRequest
    @PostMapping("/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {

        Article savedArticle = blogService.save(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }


    @GetMapping("/articles")
    // ArticleResponse : 응답을 가져오는 DTO
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {

        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        // ArticleResponse::new → 생성자 참조 문법, new ArticleResponse(article)을 줄인 표현

        return ResponseEntity.ok()
                .body(articles);

        /**
         *
         * 📌 3. 자주 쓰는 Stream 연산
         * .map() → 데이터를 변환
         * .filter() → 조건에 맞는 데이터만 남김
         * .sorted() → 정렬
         * .forEach() → 하나씩 꺼내서 처리
         * .toList() / .collect() → 다시 컬렉션으로 변환
         *
         */
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService. delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {

        System.out.println("----------BlogApiController----------");
        System.out.println("PathVariable : " +id);
        System.out.println("request.getTitle() :" + request.getTitle());
        System.out.println("request.getContent() :" + request.getContent());
        System.out.println("-----------------------------------");

        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
