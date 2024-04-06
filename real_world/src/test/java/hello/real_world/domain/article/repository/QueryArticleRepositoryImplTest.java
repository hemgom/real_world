package hello.real_world.domain.article.repository;

import hello.real_world.domain.article.Article;
import hello.real_world.domain.article.dto.RequestFindArticles;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class QueryArticleRepositoryImplTest {

    @Autowired
    private QueryArticleRepositoryImpl queryArticleRepository;


    @Test
    void findRecentArticlesTest() {
        // given
        RequestFindArticles requestDto = new RequestFindArticles(
                null,
                null,
                null,
                20L,
                0L
        );

        List<Article> recentArticles = queryArticleRepository.findRecentArticles(requestDto);

        Assertions.assertThat(recentArticles).hasSizeGreaterThan(0);
    }

}
