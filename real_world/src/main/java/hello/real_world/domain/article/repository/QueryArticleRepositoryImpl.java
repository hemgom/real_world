package hello.real_world.domain.article.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.article.Article;
import hello.real_world.domain.article.dto.RequestUpdateArticle;
import hello.real_world.domain.article.dto.ResponseSingleArticle;
import hello.real_world.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static hello.real_world.domain.article.QArticle.article;

@Repository
@RequiredArgsConstructor
public class QueryArticleRepositoryImpl implements QueryArticleRepository {

    private final JPAQueryFactory query;

    @Override
    public Article findBySlug(String slug) {
        return Optional.ofNullable(query
                        .select(article)
                        .from(article)
                        .where(article.slug.eq(slug))
                        .fetchOne())
                .orElseThrow(() -> new NoSuchElementException("기사를 찾을 수 없습니다."));
    }

    @Override
    public ResponseSingleArticle.ArticleInfo getArticleInfoNotAuth(Article article) {
        return ResponseSingleArticle.ArticleInfo.builder()
                .slug(article.getSlug())
                .title(article.getTitle())
                .description(article.getDescription())
                .tagList(article.getTagList().stream().map(Tag::getTag).toList())
                .createAt(article.getCreateAt())
                .updateAt(article.getUpdateAt())
                .favoriteCount(article.getFavoriteCount())
                .build();
    }

    @Override
    public Article updateArticle(RequestUpdateArticle.UpdateInfo updateInfo, Article findArticle) {
        findArticle.updateArticle(updateInfo);
        return findArticle;
    }

}
