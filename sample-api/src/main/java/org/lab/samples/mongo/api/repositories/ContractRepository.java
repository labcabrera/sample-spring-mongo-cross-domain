package org.lab.samples.mongo.api.repositories;

import java.util.Optional;

import org.lab.samples.mongo.api.model.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContractRepository extends MongoRepository<Contract, String> {

	Optional<Contract> findByContractNumber(String code);
}
