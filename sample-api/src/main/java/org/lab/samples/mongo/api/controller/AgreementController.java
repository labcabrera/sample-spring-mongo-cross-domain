package org.lab.samples.mongo.api.controller;

import java.util.Optional;

import org.lab.samples.mongo.api.resources.AgreementResource;
import org.lab.samples.mongo.shared.model.Agreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/agreements", produces = "application/hal+json")
@Api(tags = "Agreements")
public class AgreementController {

	@Autowired
	@Qualifier("sharedMongoTemplate")
	private MongoTemplate sharedMongoTemplate;

	@GetMapping("/{id}")
	public ResponseEntity<AgreementResource> findById(@PathVariable String id) {
		Agreement agreement = sharedMongoTemplate.findById(id, Agreement.class);
		Optional<AgreementResource> optional = agreement != null ? Optional.of(new AgreementResource(agreement)) : Optional.empty();
		return ResponseEntity.of(optional);
	}

}
