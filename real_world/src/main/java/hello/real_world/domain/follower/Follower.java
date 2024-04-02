package hello.real_world.domain.follower;

import hello.real_world.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

// 사용자를 팔로우하는 정보
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // PK

    private String followerName;    // 팔로우 신청 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;    // 팔로우 대상 id, Member PK 를 참조하는 FK

}
