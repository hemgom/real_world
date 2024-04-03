package hello.real_world.domain.article;

import hello.real_world.domain.comment.Comment;
import hello.real_world.domain.favorite.Favorite;
import hello.real_world.domain.member.Member;
import hello.real_world.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
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

}
