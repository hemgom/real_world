package hello.real_world.dto;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import static hello.real_world.domain.member.QMember.member;

// 저장된 사용자 정보에서 조건(email, password)에 해당하는 사용자 조회
@Getter @Setter
public class FindEntityDto {

    public static BooleanExpression likeEmail(String email) {

        if (StringUtils.hasText(email)) {
            return member.email.like("%" + email + "%");
        }

        return null;

    }

    public static BooleanExpression likePassword(String password) {

        if (StringUtils.hasText(password)) {
            return member.password.like("%" + password + "%");
        }

        return null;

    }

}
