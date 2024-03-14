package hello.real_world.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtInfo {

    private String grantType;       // Authorization 필드에 담기는 인증 타입
    private String accessToken;
    private String refreshToken;

}
