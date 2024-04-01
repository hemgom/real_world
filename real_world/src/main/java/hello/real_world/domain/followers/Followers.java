package hello.real_world.domain.followers;

import hello.real_world.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

// 사용자를 팔로우하는 정보
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Followers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // PK

    private String followerName;    // 팔로우 신청 이름

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;    // 팔로우 대상 id, Member PK 를 참조하는 FK

}
