package org.lab.samples.mongo.api.rest;

import static io.restassured.RestAssured.get;

import org.hamcrest.Matchers;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.ApiApplication;
import org.lab.samples.mongo.api.model.Person;
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
		.assertThat().statusCode(200)
		.assertThat().body("_embedded", Matchers.notNullValue())
		.assertThat().body("page.size", Matchers.is(10))
		.assertThat().body("page.totalElements", Matchers.is(3));
		//@formatter:on
	}

	@Test
	public void testSearchById() {
		Person person = personRepository.findByIdCardNumber("70111222A").get();
		//@formatter:off
		get("/persons/" + person.getId())
		.then()
		.assertThat().statusCode(200)
		.assertThat().body("person", Matchers.notNullValue())
		.assertThat().body("person.id", Matchers.is(person.getId()));
		//@formatter:on
	}

	@Test
	public void testSearchByNotFound() {
		//@formatter:off
		get("/persons/XYZ")
		.then()
		.assertThat().statusCode(404);
		//@formatter:on
	}

	@Test
	public void testSearchByIdCardNumber() {
		//@formatter:off
		get("/persons?search=idCard.number==70111222A")
		.then()
		.assertThat().statusCode(200).and()
		.assertThat().body("_embedded", Matchers.notNullValue())
		.assertThat().body("page.size", Matchers.is(10))
		.assertThat().body("page.totalElements", Matchers.is(1));
		//@formatter:on
	}

	@Test
	public void testSearchByIdCardNumberNotFound() {
		//@formatter:off
		get("/persons?search=idCard.number==XYZ")
		.then()
		.assertThat().statusCode(200).and()
		.assertThat().body("_embedded", Matchers.nullValue());
		//@formatter:on
	}

	// TODO No funciona
	@Test
	public void testSearchBefore() {
		//@formatter:off
		get("/persons?search=birthDate=lt=1980-01-01")
		.then()
		.assertThat().statusCode(200).and()
		.assertThat().body("_embedded", Matchers.notNullValue())
		.assertThat().body("page.size", Matchers.is(10))
		.assertThat().body("page.totalElements", Matchers.is(2));
		//@formatter:on
	}
}
