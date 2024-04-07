package hello.real_world.domain.article.dto;

import lombok.Getter;

@Getter
public class RequestFindArticles {

    private String tagName;
    private String authorName;
    private String favoriteUsername;
    private Long limitCount;
    private Long offsetCount;

    public RequestFindArticles() {
    }

    public RequestFindArticles(String tagName, String authorName, String favoriteUsername,
                               Long limitCount, Long offsetCount) {
        this.tagName = tagName;
        this.authorName = authorName;
        this.favoriteUsername = favoriteUsername;
        this.limitCount = limitCount;
        this.offsetCount = offsetCount;
    }

    public RequestFindArticles(Long limitCount, Long offsetCount) {
        this.limitCount = limitCount;
        this.offsetCount = offsetCount;
    }

}
