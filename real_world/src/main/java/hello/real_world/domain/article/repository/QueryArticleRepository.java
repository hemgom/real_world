package hello.real_world.domain.article.repository;

import hello.real_world.domain.article.Article;
import hello.real_world.domain.article.dto.RequestFindArticles;
import hello.real_world.domain.article.dto.RequestUpdateArticle;
import hello.real_world.domain.article.dto.ResponseSingleArticle;

import java.util.List;

public interface QueryArticleRepository {

    // slug 로 기사 조회
    Article findBySlug(String slug);

    // (인증 없이) 기사 정보 가져오기
    ResponseSingleArticle.ArticleInfo getArticleInfo(Article article);

    // 최근 기사 목록 가져오기
    List<Article> findRecentArticles(RequestFindArticles request);

}
