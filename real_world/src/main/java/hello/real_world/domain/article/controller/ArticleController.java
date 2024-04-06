package hello.real_world.domain.article.controller;

import hello.real_world.domain.article.dto.*;
import hello.real_world.domain.article.service.ArticleService;
import hello.real_world.domain.comment.dto.RequestAddComment;
import hello.real_world.domain.comment.dto.ResponseMultipleComments;
import hello.real_world.domain.comment.dto.ResponseSingleComment;
import hello.real_world.domain.tag.dto.ResponseTags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles")
    public ResponseSingleArticle createArticle(@RequestBody RequestAddArticle request,
                                               Authentication authentication) {
        log.info("POST 기사 생성");
        String userEmail = authentication.getName();
        return articleService.createArticle(request, userEmail);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/articles/{slug}")
    public ResponseSingleArticle getArticle(@PathVariable("slug") String slug,
                                            Authentication authentication) {
        log.info("GET 기사 조회");
        if (authentication == null) {
            return articleService.getArticleNotAuth(slug);
        } else {
            String userEmail = authentication.getName();
            return articleService.getArticle(slug, userEmail);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/articles/{slug}")
    public ResponseSingleArticle updateArticle(@RequestBody RequestUpdateArticle request,
                                               @PathVariable("slug") String slug,
                                               Authentication authentication) {
        log.info("PUT 기사 수정");
        String userEmail = authentication.getName();
        return articleService.updateArticle(request, slug, userEmail);
    }

    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @DeleteMapping("/articles/{slug}")
    public void delArticle(@PathVariable("slug") String slug) {
        log.info("DELETE 기사 삭제");
        articleService.delArticle(slug);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles/{slug}/favorite")
    public ResponseSingleArticle addFavoriteArticle(@PathVariable("slug") String slug,
                                                    Authentication authentication) {
        log.info("POST 즐겨찾기 추가");
        String userEmail = authentication.getName();
        return articleService.addFavoriteArticle(slug, userEmail);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/articles/{slug}/favorite")
    public ResponseSingleArticle delFavoriteArticle(@PathVariable("slug") String slug,
                                                    Authentication authentication) {
        log.info("DELETE 즐겨찾기 삭제");
        String userEmail = authentication.getName();
        return articleService.delFavoriteArticle(slug, userEmail);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/tags")
    public ResponseTags getTags() {
        log.info("GET 태그 리스트 반환");
        return articleService.getAllTags();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles/{slug}/comments")
    public ResponseSingleComment addComment(@RequestBody RequestAddComment request,
                                            @PathVariable("slug") String slug,
                                            Authentication authentication) {
        log.info("POST 게시글 댓글 추가");
        String userEmail = authentication.getName();
        return articleService.addComment(request, slug, userEmail);
    }

    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @DeleteMapping("/articles/{slug}/comments/{id}")
    public void delComment(@PathVariable("slug") String slug,
                           @PathVariable("id") Long commentId,
                           Authentication authentication) {
        log.info("DELETE 게시글 댓글 삭제");
        String userEmail = authentication.getName();
        articleService.delComment(slug, commentId, userEmail);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/articles/{slug}/comments")
    public ResponseMultipleComments getAllCommentsFromArticle(@PathVariable("slug") String slug,
                                                              Authentication authentication) {
        log.info("GET 게시글의 모든 댓글 조회");
        if (authentication == null) {
            return articleService.getAllCommentsFromArticleNotAuth(slug);
        } else {
            String userEmail = authentication.getName();
            return articleService.getAllCommentsFromArticle(slug, userEmail);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/articles")
    public ResponseMultipleArticles findRecentArticles(@RequestParam(name = "tag", required = false) String tagName,
                                                       @RequestParam(name = "author", required = false) String authorName,
                                                       @RequestParam(name = "favorite", required = false) String favoriteUsername,
                                                       @RequestParam(name = "limit", defaultValue = "20") Long limitCount,
                                                       @RequestParam(name = "offset", defaultValue = "0") Long offsetCount,
                                                       Authentication authentication) {
        RequestFindArticles request = new RequestFindArticles(tagName, authorName, favoriteUsername, limitCount, offsetCount);
        log.info("GET 검색 조건에 맞는 기사들 조회");
        if (authentication == null) {
            return articleService.findRecentArticlesNotAuth(request);
        } else {
            String userEmail = authentication.getName();
            return articleService.findRecentArticles(request, userEmail);
        }
    }

}
