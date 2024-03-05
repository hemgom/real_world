package hello.real_world.repository;

import lombok.Getter;
import lombok.Setter;

// 검색 조건
@Getter @Setter
public class MemberSearchCond {

    private String email;

    public MemberSearchCond() {
    }

    public MemberSearchCond(String email) {
        this.email = email;
    }
}
