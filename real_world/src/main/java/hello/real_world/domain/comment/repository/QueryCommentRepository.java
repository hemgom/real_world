package hello.real_world.domain.comment.repository;

import hello.real_world.domain.article.Article;
import hello.real_world.domain.comment.Comment;
import hello.real_world.domain.comment.dto.ResponseSingleComment;

import java.util.List;

public interface QueryCommentRepository {

    // 댓글 정보 응답 객체 전달
    ResponseSingleComment.CommentInfo getCommentInfo(Comment comment);

    // 댓글 조회 (기사, 댓글 ID 이용)
    Comment findByArticleAndId(Article findArticle, Long commentId);

    // 댓글 작성자 확인 (로그인 사용자 = 댓글 작성자 인지 체크)
    void checkAuthor(String commentAuthor, String loginMemberUsername);

    // 특정 게시물의 모든 댓글 조회
    List<ResponseSingleComment.CommentInfo> getAllCommentsInfo(Article findArticle);

}
