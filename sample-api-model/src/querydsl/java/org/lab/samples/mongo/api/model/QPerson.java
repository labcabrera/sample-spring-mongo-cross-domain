package org.lab.samples.mongo.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPerson is a Querydsl query type for Person
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPerson extends EntityPathBase<Person> {

    private static final long serialVersionUID = 1142204984L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPerson person = new QPerson("person");

    public final org.lab.samples.mongo.shared.model.QCountry birthCountry;

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath id = createString("id");

    public final QIdCard idCard;

    public final StringPath name = createString("name");

    public final ListPath<org.lab.samples.mongo.shared.model.Country, org.lab.samples.mongo.shared.model.QCountry> nationalities = this.<org.lab.samples.mongo.shared.model.Country, org.lab.samples.mongo.shared.model.QCountry>createList("nationalities", org.lab.samples.mongo.shared.model.Country.class, org.lab.samples.mongo.shared.model.QCountry.class, PathInits.DIRECT2);

    public final StringPath surname = createString("surname");

    public QPerson(String variable) {
        this(Person.class, forVariable(variable), INITS);
    }

    public QPerson(Path<? extends Person> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPerson(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPerson(PathMetadata metadata, PathInits inits) {
        this(Person.class, metadata, inits);
    }

    public QPerson(Class<? extends Person> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.birthCountry = inits.isInitialized("birthCountry") ? new org.lab.samples.mongo.shared.model.QCountry(forProperty("birthCountry")) : null;
        this.idCard = inits.isInitialized("idCard") ? new QIdCard(forProperty("idCard")) : null;
    }

}

