package hello.real_world.domain.followers;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFollowers is a Querydsl query type for Followers
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFollowers extends EntityPathBase<Followers> {

    private static final long serialVersionUID = 1069881203L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFollowers followers = new QFollowers("followers");

    public final StringPath followerName = createString("followerName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final hello.real_world.domain.member.QMember memberId;

    public QFollowers(String variable) {
        this(Followers.class, forVariable(variable), INITS);
    }

    public QFollowers(Path<? extends Followers> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFollowers(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFollowers(PathMetadata metadata, PathInits inits) {
        this(Followers.class, metadata, inits);
    }

    public QFollowers(Class<? extends Followers> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberId = inits.isInitialized("memberId") ? new hello.real_world.domain.member.QMember(forProperty("memberId")) : null;
    }

}

