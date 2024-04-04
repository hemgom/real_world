package hello.real_world.domain.tag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryTagRepositoryImpl implements QueryTagRepository {

    private final JPAQueryFactory query;



}
