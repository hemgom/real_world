package hello.real_world.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 프로필 응답 DTO
 * realWorld 응답 spec 에 맞춰 작성
 */
@Getter
public class ResponseProfile {

    private ProfileInfo profile;

    public ResponseProfile() {
    }

    public ResponseProfile(ProfileInfo profile) {
        this.profile = profile;
    }

    // 응답 할 사용자 프로필 정보 저장
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileInfo {

        private String username;    // 사용자 이름
        private String bio;         // 사용자 소개
        private String image;       // 사용자 프로필 사진
        private String following;   // 사용자 팔로우 상태

        public void setFollowing(String state) {
            this.following = state;
        }

    }

}
