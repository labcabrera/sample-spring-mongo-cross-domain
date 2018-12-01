package org.lab.samples.mongo.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QIdCard is a Querydsl query type for IdCard
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QIdCard extends BeanPath<IdCard> {

    private static final long serialVersionUID = 939460014L;

    public static final QIdCard idCard = new QIdCard("idCard");

    public final StringPath number = createString("number");

    public QIdCard(String variable) {
        super(IdCard.class, forVariable(variable));
    }

    public QIdCard(Path<? extends IdCard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIdCard(PathMetadata metadata) {
        super(IdCard.class, metadata);
    }

}

