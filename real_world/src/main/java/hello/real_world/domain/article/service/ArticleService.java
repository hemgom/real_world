package hello.real_world.domain.article.service;

import hello.real_world.domain.article.dto.RequestAddArticle;
import hello.real_world.domain.article.dto.ResponseSingleArticle;

public interface ArticleService {

    // 기사 생성
    ResponseSingleArticle createArticle(RequestAddArticle request, String userEmail);

    // (인증 없이) 기사 조회
    ResponseSingleArticle getArticleNotAuth(String slug);

    // 기사 조회 (인증)
    ResponseSingleArticle getArticle(String slug, String userEmail);

}
