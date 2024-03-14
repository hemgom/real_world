package hello.real_world.domain.member;

import hello.real_world.dto.RequestAddMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String email;       // 사용자 ID
    private String password;    // 사용자 PW
    private String username;    // 사용자 이름

    private String bio;         // 사용자 소개
    private String image;       // 사용자 프로필 사진

    public static Member addMemberInfo(RequestAddMember.JoinInfo joinInfo) {
        return Member.builder()
                .email(joinInfo.getEmail())
                .username(joinInfo.getUsername())
                .password(joinInfo.getPassword())
                .build();
    }

}
