package hello.real_world.domain.member;

import hello.real_world.domain.article.Article;
import hello.real_world.domain.follower.Follower;
import hello.real_world.domain.following.Following;
import hello.real_world.domain.member.dto.RequestAddMember;
import hello.real_world.domain.member.dto.RequestUpdateMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// 사용자 정보
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 접근제한이 PROTECTED 인 기본 생정자를 생성해 줌
@AllArgsConstructor  // 이부분 추후에 지울 예정 현재는 계속 Member 필드 변수가 추가 되서 설정해둠
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;               // 사용자 ID
    private String password;            // 사용자 PW
    private String username;            // 사용자 이름

    private String bio;                 // 사용자 소개
    private String image;               // 사용자 프로필 사진

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Following> followings = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Follower> followers = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();

    public static Member setMemberInfo(RequestAddMember.JoinInfo joinInfo) {
        return Member.builder()
                .email(joinInfo.getEmail())
                .username(joinInfo.getUsername())
                .password(joinInfo.getPassword())
                .build();
    }

    public void updateMemberInfo(RequestUpdateMember.UpdateInfo updateInfo) {

        if (updateInfo.getEmail() != null) {
            this.email = updateInfo.getEmail();
        }

        if (updateInfo.getPassword() != null) {
            this.password = updateInfo.getPassword();
        }

        if (updateInfo.getUsername() != null) {
            this.username = updateInfo.getUsername();
        }

        if (updateInfo.getBio() != null) {
            this.bio = updateInfo.getBio();
        }

        if (updateInfo.getImage() != null) {
            this.image = updateInfo.getImage();
        }

    }

}
