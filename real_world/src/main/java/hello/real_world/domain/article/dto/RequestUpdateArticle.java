package hello.real_world.domain.article.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class RequestUpdateArticle {

    private UpdateInfo article;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateInfo {

        private String title;
        private String description;
        private String body;

    }

}
