package hello.real_world.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class RequestUpdateMember {

    private UpdateInfo user;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateInfo {

        private String email;
        private String username;
        private String password;
        private String bio;
        private String image;

    }

}
