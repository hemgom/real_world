package hello.real_world.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

// 사용자 정보
@Getter @Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 사용자 식별 ID


    @NotEmpty
    private String email;       // email 주소

    @NotEmpty
    private String password;    // 패스워드

    public Member() {
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
