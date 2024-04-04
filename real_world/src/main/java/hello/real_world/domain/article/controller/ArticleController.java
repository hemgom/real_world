package hello.real_world.domain.article.controller;

import hello.real_world.domain.article.dto.RequestAddArticle;
import hello.real_world.domain.article.dto.ResponseSingleArticle;
import hello.real_world.domain.article.service.ArticleService;
import hello.real_world.domain.member.service.MemberService;
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

    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @PutMapping("/articles/{slug}")
    public ResponseSingleArticle updateArticle(@RequestBody String s,
                                               @PathVariable("slug") String slug,
                                               Authentication authentication) {

        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/articles/{slug}")
    public void delArticle(@PathVariable("slug") String slug, Authentication authentication) {

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles/{slug}/favorite")
    public ResponseSingleArticle addFavoriteArticle(@PathVariable("slug") String slug,
                                                    Authentication authentication) {
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/articles/{slug}/favorite")
    public ResponseSingleArticle delFavoriteArticle(@PathVariable("slug") String slug,
                                                    Authentication authentication) {
        return null;
    }

}
