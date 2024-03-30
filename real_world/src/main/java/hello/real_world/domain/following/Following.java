package hello.real_world.domain.following;

import hello.real_world.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

// 사용자가 팔로우하는 정보
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // PK

    private String username;    // 팔로우 대상 이름

    @ManyToOne
    @JoinColumn
    private Member followerId;    // 팔로우 신청 id, Member PK 를 참조하는 FK

}
