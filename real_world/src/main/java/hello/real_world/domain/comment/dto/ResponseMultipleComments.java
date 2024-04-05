package hello.real_world.domain.comment.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseMultipleComments {

    private List<ResponseSingleComment.CommentInfo> comments;

    public ResponseMultipleComments() {
    }

    public ResponseMultipleComments(List<ResponseSingleComment.CommentInfo> comments) {
        this.comments = comments;
    }

}
