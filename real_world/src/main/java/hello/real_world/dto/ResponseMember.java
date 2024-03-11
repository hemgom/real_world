package hello.real_world.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/*
 * 사용자 정보 응답 DTO
 * realWorld 응답 spec 에 맞춰 작성
 */
@Getter
public class ResponseMember {

    private UserInfo user;

    public ResponseMember() {
    }

    public ResponseMember(UserInfo user) {
        this.user = user;
    }

    // 응답 할 사용자 정보 저장
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {

        private String email;       // 사용자 ID
        private String username;    // 사용자 이름
        private String bio;         // 사용자 소개
        private String image;       // 사용자 프로필 사진

        public static UserInfo getJoinInfo(RequestAddMember.JoinInfo joinInfo) {

            return UserInfo.builder()
                    .email(joinInfo.getEmail())
                    .username(joinInfo.getUsername())
                    .build();
        }

    }

}
