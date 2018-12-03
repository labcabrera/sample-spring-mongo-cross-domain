package org.lab.samples.mongo.api.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.mongodb.DBRef;

@Service
public class ContractService extends RsqlSearchService<Contract> {

	@Autowired
	private PersonService personService;

	public ContractService() {
		super(Contract.class);
	}

	public Page<Contract> findByCustomerIdCard(String search, String idCardNumber, Pageable pageable) {
		Query query = new Query();

		if (StringUtils.isNotBlank(search)) {
			Condition<GeneralQueryBuilder> condition = pipeline.apply(search, entityClass);
			Criteria criteria = condition.query(new MongoVisitor());
			query.addCriteria(criteria);
		}

		if (StringUtils.isNotEmpty(idCardNumber)) {
			Optional<Person> optionalPerson = personService.findByIdCardNumber(idCardNumber);
			if (optionalPerson.isPresent()) {
				String personId = optionalPerson.get().getId();
				// @formatter:off
				Criteria criteria = new Criteria().orOperator(
					Criteria.where("holder.id").is(personId),
					Criteria.where("recipients").is(new DBRef("persons", new ObjectId(personId))));
				// @formatter:on
				query.addCriteria(criteria);
			}
		}

		long count = mongoTemplate.count(query, entityClass);
		query.with(pageable);
		List<Contract> results = mongoTemplate.find(query, entityClass);
		return new PageImpl<Contract>(results, pageable, count);
	}

}
