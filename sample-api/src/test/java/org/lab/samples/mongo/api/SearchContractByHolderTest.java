package org.lab.samples.mongo.api;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.model.Person;
import org.lab.samples.mongo.api.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchContractByHolderTest {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private MongoOperations mongoOperations;

	// Nota al no poder hacer joins en mongo debemos resolver primero el id
	@Test
	public void findUsingQuery() {
		Person person = personRepository.findByIdCardNumber("70111222A").get();
		Query query = new Query(Criteria.where("holder.id").is(person.getId()));
		List<Contract> results = mongoOperations.find(query, Contract.class);
		Assert.assertFalse(results.isEmpty());

		System.out.println("Search contract by holder:");
		results.forEach(e -> System.out.println(e.toString()));
	}
}
