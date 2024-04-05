package hello.real_world.domain.favorite.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.article.Article;
import hello.real_world.domain.favorite.Favorite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static hello.real_world.domain.favorite.QFavorite.favorite;

@Repository
@RequiredArgsConstructor
public class QueryFavoriteRepositoryImpl implements QueryFavoriteRepository {

    private final JPAQueryFactory query;

    @Override
    public Favorite findByArticleAndUsername(Article article, String username) {
        return Optional.ofNullable(query
                        .select(favorite)
                        .from(favorite)
                        .where(favorite.article.eq(article), favorite.username.eq(username))
                        .fetchOne())
                .orElseThrow(() -> new NoSuchElementException("조건에 맞는 사용자가 없습니다."));
    }

    @Override
    public Favorite addFavorite(Article article, String username) {
        return Favorite.builder()
                .username(username)
                .article(article)
                .build();
    }

    @Override
    public String checkFavoriteStatus(Article findArticle, String username) {
        for (Favorite favorite : findArticle.getFavorites()) {
            if (!favorite.getUsername().equals(username)) continue;

            return "true";
        }
        return "false";
    }

}
