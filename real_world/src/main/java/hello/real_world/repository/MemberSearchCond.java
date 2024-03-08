package hello.real_world.repository;

import lombok.Getter;
import lombok.Setter;

// 검색 조건 - 이름
@Getter @Setter
public class MemberSearchCond {

    private String username;

    public MemberSearchCond() {
    }

    public MemberSearchCond(String username) {
        this.username = username;
    }

}
