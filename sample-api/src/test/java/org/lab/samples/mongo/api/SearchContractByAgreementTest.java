package org.lab.samples.mongo.api;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.model.QContract;
import org.lab.samples.mongo.api.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.core.types.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchContractByAgreementTest {

	@Autowired
	private ContractRepository repository;

	@Autowired
	private MongoOperations mongoOperations;

	// La busqueda por id de un documento existente en otra base de datos funciona
	@Test
	public void findUsingQuery() {
		Query query = new Query(Criteria.where("agreement.id").is("10001"));
		List<Contract> results = mongoOperations.find(query, Contract.class);
		System.out.println("Contract search by agreement using Query results:");
		results.forEach(e -> System.out.println(e));
		Assert.assertFalse(results.isEmpty());
	}

	// No funciona con las busquedas en otras bases de datos utilizando predicate
	@Test
	public void findUsingPredicate() {
		Predicate predicate = QContract.contract.agreement.id.eq("10001");
		Page<Contract> page = repository.findAll(predicate, PageRequest.of(0, 10));
		System.out.println("Contract search by agreement using Predicate results:");
		page.getContent().forEach(e -> System.out.println(e));
	}

}
