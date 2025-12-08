package com.springdev.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdev.spring.domain.Article;
import com.springdev.spring.dto.AddArticleRequest;
import com.springdev.spring.dto.UpdateArticleRequest;
import com.springdev.spring.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Spring Context를 통째로 띄워서 실제 빈들을 주입받으며 테스트합니다(통합 테스트 성격)
@AutoConfigureMockMvc // MockMvc(가짜 서블릿 환경에서 MVC를 테스트하는 도구)를 자동 구성
class BlogApiControllerTest {

    @Autowired
    // 스프링이 자동 주입한 MockMvc (AutoConfigureMockMvc 덕분)
    protected MockMvc mockMvc;

    @Autowired
    //  JSON 직렬화/역직렬화를 위한 ObjectMapper
    protected ObjectMapper objectMapper;

    @Autowired
    // 웹 애플리케이션 컨텍스트 (MockMvc 수동 구성에 사용)
    private WebApplicationContext context;

    @Autowired
    // JPA/스프링 데이터 레포지토리 - 실제로 DB에 저장/조회 검증
    BlogRepository blogRepository;

    /**
     * 각 테스트 실행 전 공통 준비
     * - MockMvc를 현재 웹 컨텍스트로부터 다시 빌드
     * - 레포지토리 비우기(테스트 간 데이터 간섭 방지)
     */
    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given: 테스트 입력값과 요청 URL 준비
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // ObjectMapper로 DTO -> JSON 문자열 변환
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when: MockMvc로 POST 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                // // 서버에 JSON을 보낸다고 알림(요청 본문 타입)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                //  // 실제 JSON 본문
                .content(requestBody));

        // then: 201 Created 반환되었는지 확인
        result.andExpect(status().isCreated());

        // 실제 DB(혹은 H2 같은 테스트 DB)에 데이터가 저장되었는지 검증
        List<Article> articles = blogRepository.findAll();

        // 1건 저장되었어야 함
        assertThat(articles.size()).isEqualTo(1);

        // 저장된 엔티티의 필드 값 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception{
        // given: DB에 미리 한 건 저장 (목록 조회 결과가 비어있지 않도록)
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        /**
         * 테스트 시작할 때 DB는 @BeforeEach에서 deleteAll()로 비워진 상태
         * 만약 아무 것도 없는 상태에서 바로 GET /api/articles 를 호출하면, 빈 배열 [] 만 리턴
         * 의도적으로 1건을 저장해두고 → 그게 조회되는지 확인
         * */

        blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                .build());

        // when: GET 요청 전송 (JSON으로 응답 받길 원함을 accept로 전달)
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then:
        resultActions
                .andExpect(status().isOk())
                // 응답 본문이 JSON 배열이라고 가정: 첫 번째 요소의 content가 기대값인지
                .andExpect(jsonPath("$[0].content").value(content))
                // 첫 번째 요소의 title이 기대값인지
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {

        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article saveArticle = blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                .build());

        // when
        mockMvc.perform(delete(url, saveArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article saveArticle = blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                .build());

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, saveArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(saveArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }

}