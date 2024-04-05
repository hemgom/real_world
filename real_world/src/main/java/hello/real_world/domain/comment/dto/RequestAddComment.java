package hello.real_world.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class RequestAddComment {

    private CommentInfo comment;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentInfo {

        private String body;

    }

}
