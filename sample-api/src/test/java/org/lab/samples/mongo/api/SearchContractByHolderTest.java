package org.lab.samples.mongo.api;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.model.Person;
import org.lab.samples.mongo.api.model.QContract;
import org.lab.samples.mongo.api.repositories.ContractRepository;
import org.lab.samples.mongo.api.repositories.PersonRepository;
import org.lab.samples.mongo.api.search.PredicateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.core.types.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchContractByHolderTest {

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private PredicateParser predicateParser;

	// Nota al no poder hacer joins en mongo debemos resolver primero el id
	@Test
	public void findUsingQuery() {
		Person person = personRepository.findByIdCardNumber("70111222A").get();
		Query query = new Query(Criteria.where("holder.id").is(person.getId()));
		List<Contract> results = mongoOperations.find(query, Contract.class);
		Assert.assertFalse(results.isEmpty());

		System.out.println("Search contract by holder using Query:");
		results.forEach(e -> System.out.println(e.toString()));
	}

	// contract.holder.id = 5c0265c1faea554cb78ec921
	@Test
	public void findUsingPredicate() {
		Person person = personRepository.findByIdCardNumber("70111222A").get();
		Predicate predicate = QContract.contract.holder.id.eq(person.getId());
		List<Contract> results = contractRepository.findAll(predicate, PageRequest.of(0, 10)).getContent();
		Assert.assertFalse(results.isEmpty());
		System.out.println("Search contract by holder using Predicate:");
		results.forEach(e -> System.out.println(e.toString()));
	}

	// TODO no funciona
	// eqIc(contract.holder.id,5c0265c1faea554cb78ec921)
	@Test
	public void findUsingRSQL() {
		Person person = personRepository.findByIdCardNumber("70111222A").get();
		String rsql = "holder.id==" + person.getId();
		Predicate predicate = predicateParser.buildPredicate(rsql, ContractRepository.PATH_MAP).get();
		List<Contract> results = contractRepository.findAll(predicate, PageRequest.of(0, 10)).getContent();
		Assert.assertFalse(results.isEmpty());
		System.out.println("Search contract by holder using Predicate:");
		results.forEach(e -> System.out.println(e.toString()));
	}

}
