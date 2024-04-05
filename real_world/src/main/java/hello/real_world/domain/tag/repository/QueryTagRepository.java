package hello.real_world.domain.tag.repository;

import java.util.List;

public interface QueryTagRepository {

    // 모든 태그 조회
    List<String> findAllTags();

}
