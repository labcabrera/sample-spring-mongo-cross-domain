package org.lab.samples.mongo.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApiApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ContractAgreementRelationTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void test() {
		Query query = new Query(Criteria.where("contractNumber").is("100000000001"));
		Contract contract = mongoTemplate.findOne(query, Contract.class);
		Assert.assertNotNull(contract);
		Assert.assertNotNull(contract.getAgreement());
		Assert.assertNotNull(contract.getAgreement().getId());
		Assert.assertNotNull(contract.getAgreement().getName());
	}

}
