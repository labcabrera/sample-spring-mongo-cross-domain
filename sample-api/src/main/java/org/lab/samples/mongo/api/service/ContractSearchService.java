package org.lab.samples.mongo.api.service;

import org.lab.samples.mongo.api.model.Contract;
import org.springframework.stereotype.Service;

@Service
public class ContractSearchService extends RsqlSearchService<Contract> {

	public ContractSearchService() {
		super(Contract.class);
	}

}
