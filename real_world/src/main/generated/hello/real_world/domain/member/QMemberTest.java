package hello.real_world.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberTest is a Querydsl query type for MemberTest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberTest extends EntityPathBase<MemberTest> {

    private static final long serialVersionUID = -1923424745L;

    public static final QMemberTest memberTest = new QMemberTest("memberTest");

    public final StringPath address = createString("address");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath profileImg = createString("profileImg");

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QMemberTest(String variable) {
        super(MemberTest.class, forVariable(variable));
    }

    public QMemberTest(Path<? extends MemberTest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberTest(PathMetadata metadata) {
        super(MemberTest.class, metadata);
    }

}

