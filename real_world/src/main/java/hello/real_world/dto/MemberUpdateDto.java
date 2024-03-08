package hello.real_world.dto;

import lombok.Getter;
import lombok.Setter;

// 단순 데이터 전달 객체로 사용하기에 DTO로 명명
@Getter @Setter
public class MemberUpdateDto {

    private String email;
    private String password;
    private String username;
    private String bio;
    private String image;

    public MemberUpdateDto() {
    }

    public MemberUpdateDto(String email, String password, String username, String bio, String image) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

}
