package hello.real_world.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 요청된 데이터(user)에서 로그인 정보를 추출
 */
@Getter
public class RequestLogin {

    private LoginInfo user;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginInfo {

        private String email;       // 로그인 ID - 사용자 email
        private String password;    // 로그인 PW - 사용자 password

        public static LoginInfo setLoginInfo(RequestLogin request) {

            return LoginInfo.builder()
                    .email(request.user.getEmail())
                    .password(request.user.getPassword())
                    .build();
        }

    }

}
