package com.springdev.spring.controller;

import com.springdev.spring.domain.Article;
import com.springdev.spring.dto.ArticleListViewResponse;
import com.springdev.spring.dto.ArticleViewResponse;
import com.springdev.spring.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    /**
     * “/articles, /new-article 같은 화면(HTML)을 보여주려고 만든 컨트롤러”
     */

    private final BlogService blogService;

    /**
     * http://localhost:8080/articles
     * @param model
     * @return
     */
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        System.out.println("/articles/{id} 통과 되었다?" + article);
        return "article";
    }

    
    /** 수정 **/
    @GetMapping("/new-article")
    // id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑(id는 없을 수도 있음)
    public String newArticle(@RequestParam(required = false) /**  파라미터 없어도 에러 내지 말고 허용 **/
                                Long id, Model model) {

        if (id == null) { // id가 없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else { // id 가 있으면 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }




}
