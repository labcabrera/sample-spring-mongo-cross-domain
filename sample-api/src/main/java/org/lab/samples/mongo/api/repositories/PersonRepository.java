package org.lab.samples.mongo.api.repositories;

import java.util.Optional;

import org.lab.samples.mongo.api.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {

	Optional<Person> findByIdCardNumber(String idCardNumber);
}
