package org.lab.samples.mongo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgreement is a Querydsl query type for Agreement
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAgreement extends EntityPathBase<Agreement> {

    private static final long serialVersionUID = -1353928118L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgreement agreement = new QAgreement("agreement");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final QProduct product;

    public QAgreement(String variable) {
        this(Agreement.class, forVariable(variable), INITS);
    }

    public QAgreement(Path<? extends Agreement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAgreement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAgreement(PathMetadata metadata, PathInits inits) {
        this(Agreement.class, metadata, inits);
    }

    public QAgreement(Class<? extends Agreement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product")) : null;
    }

}

