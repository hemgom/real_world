package hello.real_world.domain.tag.repository;

import hello.real_world.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, QueryTagRepository {
}
