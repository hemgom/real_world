package hello.real_world.domain.article.dto;

import hello.real_world.domain.member.dto.ResponseProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class ResponseSingleArticle {

    private ArticleInfo article;

    public ResponseSingleArticle() {
    }

    public ResponseSingleArticle(ArticleInfo article) {
        this.article = article;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ArticleInfo {

        private String slug;
        private String title;
        private String description;
        private String body;
        private List<String> tagList;
        private String createAt;
        private String updateAt;

        @Builder.Default
        private String favorite = "false";

        private Long favoriteCount;

        private ResponseProfile.ProfileInfo author;

        public static ArticleInfo setArticleInfo(RequestAddArticle.CreateInfo request) {
            return ArticleInfo.builder()
                    .slug(request.getTitle().toLowerCase())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .body(request.getBody())
                    .tagList(request.getTagList())
                    .build();
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public void setUpdateAt(String updateAt) {
            this.updateAt = updateAt;
        }

        public void setFavorite(String status) {
            this.favorite = status;
        }

        public void setFavoriteCount(Long favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public void setAuthor(ResponseProfile.ProfileInfo author) {
            this.author = author;
        }

    }

}
