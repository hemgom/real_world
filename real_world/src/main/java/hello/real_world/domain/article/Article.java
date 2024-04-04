package hello.real_world.domain.article;

import hello.real_world.domain.article.dto.RequestAddArticle;
import hello.real_world.domain.article.dto.RequestUpdateArticle;
import hello.real_world.domain.comment.Comment;
import hello.real_world.domain.favorite.Favorite;
import hello.real_world.domain.member.Member;
import hello.real_world.domain.member.dto.ResponseProfile;
import hello.real_world.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;                // 슬러그 : title 를 소문자로 바꾼 문자열
    private String title;               // 제목
    private String description;         // 설명
    private String body;                // 본문

    private String createAt;            // 작성일
    private String updateAt;            // 수정일

    @Builder.Default
    private Long favoriteCount = 0L;    // 좋아요 개수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<Tag> tagList = new ArrayList<>();

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public static Article setArticleInfo(RequestAddArticle.CreateInfo createInfo, Member author) {

        return Article.builder()
                .slug(createInfo.getTitle().toLowerCase())
                .title(createInfo.getTitle())
                .description(createInfo.getDescription())
                .body(createInfo.getBody())
                .member(author)
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

    public void updateArticle(RequestUpdateArticle.UpdateInfo updateInfo) {

        if (updateInfo.getTitle() != null) {
            this.title = updateInfo.getTitle();
            this.slug = updateInfo.getTitle().toLowerCase();
        }

        if (updateInfo.getBody() != null) {
            this.body = updateInfo.getBody();
        }

        if (updateInfo.getDescription() != null) {
            this.description = updateInfo.getDescription();
        }

    }

}
