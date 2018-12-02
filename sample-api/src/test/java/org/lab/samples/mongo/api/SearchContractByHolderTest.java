package org.lab.samples.mongo.api;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.model.Person;
import org.lab.samples.mongo.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApiApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SearchContractByHolderTest {

	@Autowired
	private PersonService personService;

	@Autowired
	private MongoOperations mongoOperations;

	@Test
	public void findUsingQuery() {
		Person person = personService.findByIdCardNumber("70111222A").get();
		Query query = new Query(Criteria.where("holder.id").is(person.getId()));
		List<Contract> results = mongoOperations.find(query, Contract.class);
		System.out.println("Search contract by holder using Query:");
		results.forEach(e -> System.out.println(e.toString()));
		Assert.assertFalse(results.isEmpty());
	}

	@Test
	public void findUsingRSQL() {
		QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();
		Person person = personService.findByIdCardNumber("70111222A").get();
		String rsql = "holder.id==" + person.getId();
		Condition<GeneralQueryBuilder> condition = pipeline.apply(rsql, Contract.class);
		Criteria criteria = condition.query(new MongoVisitor());
		Query query = new Query(criteria);
		List<Contract> contracts = mongoOperations.find(query, Contract.class);
		System.out.println("Search contract by holder using RSQL:");
		contracts.forEach(e -> System.out.println(e.toString()));
		Assert.assertFalse(contracts.isEmpty());
	}

}
