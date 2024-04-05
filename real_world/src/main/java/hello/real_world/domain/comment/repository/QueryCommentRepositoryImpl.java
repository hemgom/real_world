package hello.real_world.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.article.Article;
import hello.real_world.domain.comment.Comment;
import hello.real_world.domain.comment.dto.ResponseSingleComment;
import hello.real_world.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static hello.real_world.domain.comment.QComment.comment;

@Repository
@RequiredArgsConstructor
public class QueryCommentRepositoryImpl implements QueryCommentRepository {

    private final JPAQueryFactory query;

    @Override
    public ResponseSingleComment.CommentInfo getCommentInfo(Comment comment) {
        return ResponseSingleComment.CommentInfo.builder()
                .id(comment.getId())
                .createAt(comment.getCreateAt())
                .updateAt(comment.getUpdateAt())
                .body(comment.getBody())
                .build();
    }

    @Override
    public Comment findByArticleAndId(Article findArticle, Long commentId) {
        return Optional.ofNullable(query
                        .select(comment)
                        .from(comment)
                        .where(comment.article.eq(findArticle), comment.id.eq(commentId))
                        .fetchOne())
                .orElseThrow(() -> new NoSuchElementException("해당 게시물에 조건에 맞는 댓글이 존재하지 않습니다."));
    }

    @Override
    public void checkAuthor(String commentAuthor, String loginMemberUsername) throws CustomException {
        if (!commentAuthor.equals(loginMemberUsername)) {
            throw new RuntimeException("댓글 작성자와 로그인 사용자 불일치");
        }
    }

    @Override
    public List<ResponseSingleComment.CommentInfo> getAllCommentsInfo(Article findArticle) {
        return Optional.of(query
                        .select(comment)
                        .from(comment)
                        .where(comment.article.eq(findArticle))
                        .stream()
                        .map(this::getCommentInfo)
                        .toList())
                .orElseThrow(() -> new NoSuchElementException("해당 게시물에 댓글이 존재하지 않습니다"));
    }

}
