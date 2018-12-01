package org.lab.samples.mongo.api.controller;

import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.resources.ContractResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/products", produces = "application/hal+json")
@Api(tags = "Products")
public class ProductController {

	private MongoTemplate sharedMongoTemplate;

	public ProductController() {
		MongoClient mongoClient = new MongoClient("localhost");
		sharedMongoTemplate = new MongoTemplate(mongoClient, "mongo-sample-shared");
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContractResource> findById(@PathVariable String id) {
		Contract contract = sharedMongoTemplate.findById(id, Contract.class);
		return ResponseEntity.ok(new ContractResource(contract));
	}
}
