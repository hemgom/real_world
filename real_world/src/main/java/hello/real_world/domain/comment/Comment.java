package hello.real_world.domain.comment;

import hello.real_world.domain.article.Article;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createAt;
    private String updateAt;
    private String body;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public static Comment setCommentInfo(String commentBody, String username, Article findArticle) {
        return Comment.builder()
                .body(commentBody)
                .username(username)
                .article(findArticle)
                .build();
    }

    public void setCreateAt() {
        Date now = new Date();
        this.createAt = String.valueOf(now);
    }

    public void setUpdateAt() {
        Date now = new Date();
        this.updateAt = String.valueOf(now);
    }

}
