package hello.real_world.domain.article.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseMultipleArticles {

    private List<ResponseSingleArticle.ArticleInfo> articles;

}
