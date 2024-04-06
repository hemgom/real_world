package hello.real_world.domain.article.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.article.Article;
import hello.real_world.domain.article.dto.RequestFindArticles;
import hello.real_world.domain.article.dto.ResponseSingleArticle;
import hello.real_world.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static hello.real_world.domain.article.QArticle.article;
import static hello.real_world.domain.favorite.QFavorite.favorite;
import static hello.real_world.domain.member.QMember.member;
import static hello.real_world.domain.tag.QTag.tag1;

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
    public ResponseSingleArticle.ArticleInfo getArticleInfo(Article article) {
        return ResponseSingleArticle.ArticleInfo.builder()
                .slug(article.getSlug())
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .tagList(article.getTagList().stream().map(Tag::getTag).toList())
                .createAt(article.getCreateAt())
                .updateAt(article.getUpdateAt())
                .favoriteCount(article.getFavoriteCount())
                .build();
    }

    @Override
    public List<Article> findRecentArticles(RequestFindArticles request) {
        return Optional.of(query
                        .selectFrom(article)
                        .join(article.member, member)
                        .leftJoin(article.tagList, tag1)
                        .leftJoin(article.favorites, favorite) // outer join ?
                        .where(
                                tagNameEq(request.getTagName()),
                                authorNameEq(request.getAuthorName()),
                                favoriteUsernameEq(request.getFavoriteUsername())
                        )
                        .orderBy(article.createAt.desc())
                        .limit(request.getLimitCount())
                        .offset(request.getOffsetCount())
                        .distinct()
                        .fetch())
                .orElseThrow(() -> new NoSuchElementException("조건에 맞는 기사가 없습니다."));
    }

    public BooleanExpression tagNameEq(String tagName) {
        return StringUtils.hasText(tagName) ? tag1.tag.eq(tagName) : null;
    }

    public BooleanExpression authorNameEq(String authorName) {
        return StringUtils.hasText(authorName) ? member.username.eq(authorName) : null;
    }

    public BooleanExpression favoriteUsernameEq(String favoriteUsername) {
        return StringUtils.hasText(favoriteUsername) ? favorite.username.eq(favoriteUsername) : null;
    }

}
