package org.lab.samples.mongo.api.repositories;

import java.util.Map;
import java.util.Optional;

import org.lab.samples.mongo.api.model.Person;
import org.lab.samples.mongo.api.model.QPerson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.google.common.collect.ImmutableMap;
import com.querydsl.core.types.Path;

public interface PersonRepository extends MongoRepository<Person, String>, QuerydslPredicateExecutor<Person> {

	@SuppressWarnings("rawtypes")
	Map<String, Path> PATH_MAP = ImmutableMap.<String, Path>builder() //@formatter:off
		.put("id", QPerson.person.id)
		.put("name", QPerson.person.name)
		.put("surname", QPerson.person.surname)
		.put("idCard.number", QPerson.person.idCard.number)
		.put("birthDate", QPerson.person.birthDate)
		.put("birthCountry.id", QPerson.person.birthCountry.id)
		.build(); //@formatter:on

	Optional<Person> findByIdCardNumber(String idCardNumber);
}
