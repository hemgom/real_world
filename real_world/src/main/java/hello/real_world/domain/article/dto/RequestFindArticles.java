package hello.real_world.domain.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestFindArticles {

    private String tagName;
    private String authorName;
    private String favoriteUsername;
    private Long limitCount;
    private Long offsetCount;

}
