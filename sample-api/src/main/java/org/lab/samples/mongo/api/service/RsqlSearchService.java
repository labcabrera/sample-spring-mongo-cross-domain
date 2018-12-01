package org.lab.samples.mongo.api.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;

public abstract class RsqlSearchService<E> {

	private final Class<E> entityClass;

	@Autowired
	private MongoTemplate mongoTemplate;

	protected RsqlSearchService(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public Optional<E> findById(String id) {
		return Optional.ofNullable(mongoTemplate.findById(id, entityClass));
	}

	public Page<E> findAll(Pageable pageable) {
		return findAll(null, pageable);
	}

	public Page<E> findAll(String search, Pageable pageable) {
		Query query;
		if (StringUtils.isNotBlank(search)) {
			QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();
			Condition<GeneralQueryBuilder> condition = pipeline.apply(search, entityClass);
			Criteria criteria = condition.query(new MongoVisitor());
			query = new Query(criteria);
		}
		else {
			query = new Query();
		}
		long count = mongoTemplate.count(query, entityClass);
		query.with(pageable);
		List<E> results = mongoTemplate.find(query, entityClass);
		return new PageImpl<E>(results, pageable, count);
	}
}
