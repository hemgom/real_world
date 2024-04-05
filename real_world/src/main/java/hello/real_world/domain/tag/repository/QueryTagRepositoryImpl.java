package hello.real_world.domain.tag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static hello.real_world.domain.tag.QTag.tag1;

@Repository
@RequiredArgsConstructor
public class QueryTagRepositoryImpl implements QueryTagRepository {

    private final JPAQueryFactory query;

    @Override
    public List<String> findAllTags() {
        return Optional.of(query
                        .select(tag1)
                        .from(tag1)
                        .stream()
                        .map(Tag::getTag)
                        .distinct()
                        .toList())
                .orElseThrow(() -> new NoSuchElementException("가져 올 태그가 존재하지 않습니다"));
    }

}
