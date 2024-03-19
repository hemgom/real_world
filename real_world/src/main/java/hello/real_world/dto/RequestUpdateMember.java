package hello.real_world.dto;

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

        public static UpdateInfo setUpdateInfo(RequestUpdateMember request) {

            return UpdateInfo.builder()
                    .email(request.getUser().getEmail())
                    .username(request.getUser().getUsername())
                    .password(request.getUser().getPassword())
                    .bio(request.getUser().getBio())
                    .image(request.getUser().getImage())
                    .build();

        }

    }

}
