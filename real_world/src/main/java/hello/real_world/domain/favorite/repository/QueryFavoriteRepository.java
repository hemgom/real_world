package hello.real_world.domain.favorite.repository;

import hello.real_world.domain.article.Article;
import hello.real_world.domain.favorite.Favorite;

public interface QueryFavoriteRepository {

    // 즐겨찾기 조회 (기사, 로그인 사용자명)
    Favorite findByArticleAndUsername(Article article, String username);

    // 즐겨찾기 추가
    Favorite addFavorite(Article article, String username);

    // 즐겨찾기 상태 확인
    String checkFavoriteStatus(Article findArticle, String username);

}
