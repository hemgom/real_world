package hello.real_world.domain.comment.dto;

import hello.real_world.domain.member.dto.ResponseProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ResponseSingleComment {

    private CommentInfo comment;

    public ResponseSingleComment() {
    }

    public ResponseSingleComment(CommentInfo comment) {
        this.comment = comment;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor

    public static class CommentInfo {

        private Long id;
        private String createAt;
        private String updateAt;
        private String body;
        private ResponseProfile.ProfileInfo author;

        public void setAuthor(ResponseProfile.ProfileInfo author) {
            this.author = author;
        }

    }

}
