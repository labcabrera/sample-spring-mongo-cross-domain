package org.lab.samples.mongo.api.service;

import java.util.Optional;

import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

@Service
public class ContractSearchService {

	@Autowired
	private ContractRepository contractRepository;

	public Optional<Contract> findById(String id) {
		return contractRepository.findById(id);
	}

	public Page<Contract> findAll(Pageable pageable) {
		return contractRepository.findAll(pageable);
	}

	public Page<Contract> findAll(Predicate predicate, Pageable pageable) {
		return contractRepository.findAll(predicate, pageable);
	}

}
