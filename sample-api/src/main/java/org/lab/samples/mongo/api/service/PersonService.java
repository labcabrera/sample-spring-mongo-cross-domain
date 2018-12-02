package org.lab.samples.mongo.api.service;

import java.util.Optional;

import org.lab.samples.mongo.api.model.Person;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends RsqlSearchService<Person> {

	public PersonService() {
		super(Person.class);
	}

	public Optional<Person> findByIdCardNumber(String idCardNumber) {
		Query query = new Query(Criteria.where("idCard.number").is(idCardNumber));
		return Optional.ofNullable(mongoTemplate.findOne(query, Person.class));

	}

}
