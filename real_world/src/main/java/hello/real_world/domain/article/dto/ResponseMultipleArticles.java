package hello.real_world.domain.article.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseMultipleArticles {

    private List<ResponseSingleArticle.ArticleInfo> articles;

    public ResponseMultipleArticles() {
    }

    public ResponseMultipleArticles(List<ResponseSingleArticle.ArticleInfo> articles) {
        this.articles = articles;
    }

}
