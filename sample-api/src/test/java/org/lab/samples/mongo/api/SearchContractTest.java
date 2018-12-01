package org.lab.samples.mongo.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.controller.ContractController;
import org.lab.samples.mongo.api.resources.ContractResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchContractTest {

	@Autowired
	private ContractController controller;

	@Test
	public void findUsingEmptuRSQL() {
		ResponseEntity<PagedResources<ContractResource>> response = controller.find("", PageRequest.of(0, 10));
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertNotNull(response.getBody());
		Assert.assertNotNull(response.getBody().getContent());
		Assert.assertFalse(response.getBody().getContent().isEmpty());
	}
}
