package hello.real_world.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor     // 해당 애노테이션은 추후에 변경 필요
public class JwtDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
