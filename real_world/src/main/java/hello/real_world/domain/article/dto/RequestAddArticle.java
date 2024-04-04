package hello.real_world.domain.article.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class RequestAddArticle {

    private CreateInfo article;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateInfo {

        @NotNull
        private String title;

        @NotNull
        private String description;

        @NotNull
        private String body;

        private List<String> tagList;

        public static CreateInfo setCreateInfo(RequestAddArticle request) {

            return CreateInfo.builder()
                    .title(request.article.getTitle())
                    .description(request.article.getDescription())
                    .body(request.article.getBody())
                    .tagList(request.article.getTagList())
                    .build();
        }

    }

}
