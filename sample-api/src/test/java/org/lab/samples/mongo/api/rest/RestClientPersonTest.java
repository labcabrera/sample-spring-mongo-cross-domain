package org.lab.samples.mongo.api.rest;

import static io.restassured.RestAssured.get;

import org.hamcrest.Matchers;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.ApiApplication;
import org.lab.samples.mongo.api.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApiApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class RestClientPersonTest {

	@Autowired
	private PersonRepository personRepository;

	@Before
	public void before() {
		Assume.assumeTrue(personRepository.count() == 3L);
	}

	@Test
	public void testSearchNoArguments() {
		//@formatter:off
		get("/persons")
		.then()
		.assertThat().statusCode(200).and()
		.assertThat().body("_embedded", Matchers.notNullValue())
		.assertThat().body("page.size", Matchers.is(10))
		.assertThat().body("page.totalElements", Matchers.is(3));
		//@formatter:on
	}
}
