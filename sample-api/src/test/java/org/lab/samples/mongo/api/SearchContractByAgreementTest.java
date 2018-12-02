package org.lab.samples.mongo.api;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.model.Contract;
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
public class SearchContractByAgreementTest {

	@Autowired
	private MongoOperations mongoOperations;

	@Test
	public void findUsingQuery() {
		Query query = new Query(Criteria.where("agreement.id").is("10001"));
		List<Contract> results = mongoOperations.find(query, Contract.class);
		System.out.println("Contract search by agreement using Query results:");
		results.forEach(e -> System.out.println(e));
		Assert.assertFalse(results.isEmpty());
	}

	@Test
	public void findUsingRSQL() {
		QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();
		String rsql = "agreement.id==10001";
		Condition<GeneralQueryBuilder> condition = pipeline.apply(rsql, Contract.class);
		Criteria query = condition.query(new MongoVisitor());
		List<Contract> results = mongoOperations.find(new Query(query), Contract.class);
		System.out.println("Contract search by agreement using RSQL results:");
		results.forEach(e -> System.out.println(e));
		Assert.assertFalse(results.isEmpty());
	}

}
