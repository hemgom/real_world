package hello.real_world.domain.article.repository;

import hello.real_world.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, QueryArticleRepository {
}
