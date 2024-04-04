package hello.real_world.domain.tag;

import hello.real_world.domain.article.Article;
import hello.real_world.domain.article.dto.RequestAddArticle;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public static Tag setTagInfo(String tag, Article article) {
        return Tag.builder()
                .tag(tag)
                .article(article)
                .build();
    }

}
