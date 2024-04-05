package hello.real_world.domain.comment.repository;

import hello.real_world.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, QueryCommentRepository {
}
