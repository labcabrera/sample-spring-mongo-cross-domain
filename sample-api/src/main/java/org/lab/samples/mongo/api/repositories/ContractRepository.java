package org.lab.samples.mongo.api.repositories;

import java.util.Map;
import java.util.Optional;

import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.model.QContract;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.google.common.collect.ImmutableMap;
import com.querydsl.core.types.Path;

public interface ContractRepository extends MongoRepository<Contract, String>, QuerydslPredicateExecutor<Contract> {

	@SuppressWarnings("rawtypes")
	Map<String, Path> PATH_MAP = ImmutableMap.<String, Path>builder() //@formatter:off
		.put("id", QContract.contract.id)
		.put("contractNumber", QContract.contract.contractNumber)
		//.put("agreement", QContract.contract.agreement)
		.put("agreement.id", QContract.contract.agreement.id)
		.build(); //@formatter:on

	Optional<Contract> findByContractNumber(String code);
}
