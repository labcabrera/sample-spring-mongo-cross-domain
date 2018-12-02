package org.lab.samples.mongo.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApiApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SearchContractByIdCardNumber {

	@Autowired
	private ContractService contractService;

	@Test
	public void findByIdCard() {
		Page<Contract> page = contractService.findByCustomerIdCard("", "70555111C", PageRequest.of(0, 25));
		Assert.assertNotNull(page);
	}
	
	@Test
	public void findByIdCardOnlyRecipient() {
		Page<Contract> page = contractService.findByCustomerIdCard("", "70666888D", PageRequest.of(0, 25));
		Assert.assertNotNull(page);
	}

	@Test
	public void findByIdCardAndAgreement() {
		Page<Contract> page = contractService.findByCustomerIdCard("agreement.id==30001", "70555111C", PageRequest.of(0, 25));
		Assert.assertNotNull(page);
		Assert.assertNotNull(page.getContent());
		Assert.assertEquals(1, page.getTotalElements());
	}

}
