package hello.real_world.dto;

import hello.real_world.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

// Member 객체를 저장(포장)하는 클래스
// realWorld specs 에 맞춰 요청/응답을 받기 위해 작성
@Getter @Setter
public class UserDto {

    private Member user;

    public UserDto() {
    }

    public UserDto(Member user) {
        this.user = user;
    }

}
