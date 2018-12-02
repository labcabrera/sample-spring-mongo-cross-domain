package org.lab.samples.mongo.api.rest;

import static io.restassured.RestAssured.get;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.ApiApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApiApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class RestClientAgreementTest {

	@Test
	public void testSearchById() {
		//@formatter:off
		get("/agreements/10001")
		.then()
		.assertThat().statusCode(200).and()
		.assertThat().body("agreement.id", Matchers.is("10001"));
		//@formatter:on
	}

	@Test
	public void testSearchByIdNotFound() {
		//@formatter:off
		get("/agreements/XYZ")
		.then()
		.assertThat().statusCode(404);
		//@formatter:on
	}

}
