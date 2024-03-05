package hello.real_world.repository;

import lombok.Getter;
import lombok.Setter;

// 단순 데이터 전달 객체로 사용하기에 DTO로 명명
@Getter @Setter
public class MemberUpdateDto {

    private String email;
    private String password;

    public MemberUpdateDto() {
    }

    public MemberUpdateDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
