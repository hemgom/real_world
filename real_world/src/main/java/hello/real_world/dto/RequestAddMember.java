package hello.real_world.dto;

import lombok.*;

/*
 * 현재 사용자 정보가 user 로 감싸 입력됨
 * 입력 값에서 사용자 정보를 가져와 User 에 저장
 */
@Getter
public class RequestAddMember {

    private JoinInfo user;

    // 사용자 등록 요청 정보 저장
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinInfo {

        private String username;    // 사용자 이름
        private String email;       // 사용자 ID
        private String password;    // 사용자 PW

        public static JoinInfo addUserInfo(RequestAddMember request) {

            return JoinInfo.builder()
                    .email(request.user.getEmail())
                    .password(request.user.getPassword())
                    .username(request.user.getUsername())
                    .build();
        }

    }

}
