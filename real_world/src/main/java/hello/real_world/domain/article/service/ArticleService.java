package hello.real_world.domain.article.service;

import hello.real_world.domain.article.dto.RequestAddArticle;
import hello.real_world.domain.article.dto.RequestUpdateArticle;
import hello.real_world.domain.article.dto.ResponseSingleArticle;
import hello.real_world.domain.tag.dto.ResponseTags;

public interface ArticleService {

    // 기사 생성
    ResponseSingleArticle createArticle(RequestAddArticle request, String userEmail);

    // (인증 없이) 기사 조회
    ResponseSingleArticle getArticleNotAuth(String slug);

    // 기사 조회 (인증)
    ResponseSingleArticle getArticle(String slug, String userEmail);

    // 기사 수정 (업데이트)
    ResponseSingleArticle updateArticle(RequestUpdateArticle request, String slug, String userEmail);

    // 기사 삭제
    void delArticle(String slug);

    // 즐겨찾기 추가
    ResponseSingleArticle addFavoriteArticle(String slug, String userEmail);

    // 즐겨찾기 삭제
    ResponseSingleArticle delFavoriteArticle(String slug, String userEmail);

    // 모든 태그 반환 (리스트)
    ResponseTags getAllTags();

}
