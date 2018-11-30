package org.lab.samples.mongo.api.controller;

import org.lab.samples.mongo.api.resources.AgreementResource;
import org.lab.samples.mongo.shared.model.Agreement;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/agreements", produces = "application/hal+json")
@Api(tags = "Agreements")
public class AgreementController {

	private MongoTemplate sharedMongoTemplate;

	public AgreementController() {
		MongoClient mongoClient = new MongoClient("localhost");
		sharedMongoTemplate = new MongoTemplate(mongoClient, "mongo-sample-shared");
	}

	@GetMapping("/{id}")
	public ResponseEntity<AgreementResource> findById(@PathVariable String id) {
		Agreement agreement = sharedMongoTemplate.findById(id, Agreement.class);
		return ResponseEntity.ok(new AgreementResource(agreement));
	}

}
