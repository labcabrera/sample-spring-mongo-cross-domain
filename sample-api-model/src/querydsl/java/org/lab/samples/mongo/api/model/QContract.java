package org.lab.samples.mongo.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContract is a Querydsl query type for Contract
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContract extends EntityPathBase<Contract> {

    private static final long serialVersionUID = 1432220469L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContract contract = new QContract("contract");

    public final org.lab.samples.mongo.shared.model.QAgreement agreement;

    public final StringPath contractNumber = createString("contractNumber");

    public final QPerson holder;

    public final StringPath id = createString("id");

    public final ListPath<Person, QPerson> recipients = this.<Person, QPerson>createList("recipients", Person.class, QPerson.class, PathInits.DIRECT2);

    public QContract(String variable) {
        this(Contract.class, forVariable(variable), INITS);
    }

    public QContract(Path<? extends Contract> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContract(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContract(PathMetadata metadata, PathInits inits) {
        this(Contract.class, metadata, inits);
    }

    public QContract(Class<? extends Contract> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agreement = inits.isInitialized("agreement") ? new org.lab.samples.mongo.shared.model.QAgreement(forProperty("agreement"), inits.get("agreement")) : null;
        this.holder = inits.isInitialized("holder") ? new QPerson(forProperty("holder")) : null;
    }

}

