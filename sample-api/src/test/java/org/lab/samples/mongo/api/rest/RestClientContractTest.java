package org.lab.samples.mongo.api.rest;

import static io.restassured.RestAssured.get;

import org.hamcrest.Matchers;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.ApiApplication;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.model.Person;
import org.lab.samples.mongo.api.repositories.ContractRepository;
import org.lab.samples.mongo.api.repositories.PersonRepository;
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
public class RestClientContractTest {

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void before() {
		Assume.assumeTrue(contractRepository.count() == 12L);
	}

	@Test
	public void testSearchNoArguments() {
		//@formatter:off
		get("/contracts")
		.then()
		.assertThat().statusCode(200).and()
		.assertThat().body("_embedded", Matchers.notNullValue())
		.assertThat().body("page.size", Matchers.is(10))
		.assertThat().body("page.totalElements", Matchers.is(12));
		//@formatter:on
	}

	@Test
	public void testSearchByContractNumber() {
		//@formatter:off
		get("/contracts?search=contractNumber==100000000001")
		.then()
		.assertThat().statusCode(200).and()
		.assertThat().body("_embedded", Matchers.notNullValue())
		.assertThat().body("page.size", Matchers.is(10))
		.assertThat().body("page.totalElements", Matchers.is(1));
		//@formatter:on
	}

	@Test
	public void testSearchByHolderId() {
		Person person = personRepository.findByIdCardNumber("70111222A").get();
		long contractCount = mongoTemplate.count(new Query(Criteria.where("holder.id").is(person.getId())), Contract.class);
		//@formatter:off
		get("/contracts?search=holder.id==" + person.getId())
		.then()
		.assertThat().statusCode(200).and()
		.assertThat().body("_embedded", Matchers.notNullValue())
		.assertThat().body("page.size", Matchers.is(10))
		.assertThat().body("page.totalElements", Matchers.is((int)contractCount));
		//@formatter:on
	}

	// TODO no funciona
	@Test
	public void testSearchByAgreementId() {
		//@formatter:off
		get("/contracts?search=agreement.id==10001")
		.then()
		.assertThat().statusCode(200).and()
		.assertThat().body("_embedded", Matchers.notNullValue())
		.assertThat().body("page.size", Matchers.is(10))
		.assertThat().body("page.totalElements", Matchers.is(5));
		//@formatter:on
	}

}
