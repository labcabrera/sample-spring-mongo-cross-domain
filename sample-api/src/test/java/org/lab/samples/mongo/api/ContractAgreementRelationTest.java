package org.lab.samples.mongo.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractAgreementRelationTest {

	@Autowired
	private ContractRepository contractRepository;

	@Test
	public void test() {
		Contract contract = contractRepository.findByContractNumber("100000000001").get();
		Assert.assertNotNull(contract.getAgreement());
		Assert.assertNotNull(contract.getAgreement().getId());
		Assert.assertNotNull(contract.getAgreement().getName());
	}

}
