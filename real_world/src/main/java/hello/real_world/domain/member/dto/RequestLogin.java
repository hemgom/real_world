package hello.real_world.domain.member.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 요청된 데이터(user)에서 로그인 정보를 추출
 */
@Getter
public class RequestLogin {

    @Valid
    private LoginInfo user;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginInfo {

        @NotBlank(message = "로그인 할 계정의 email 을 입력하세요.")
        @Email(message = "email 양식에 맞추어 입력하세요.")
        private String email;       // 로그인 ID - 사용자 email
        @NotBlank(message = "로그인 할 계정의 password 를 입력하세요.")
        private String password;    // 로그인 PW - 사용자 password

        public static LoginInfo setLoginInfo(RequestLogin request) {

            return LoginInfo.builder()
                    .email(request.user.getEmail())
                    .password(request.user.getPassword())
                    .build();
        }

    }

}
