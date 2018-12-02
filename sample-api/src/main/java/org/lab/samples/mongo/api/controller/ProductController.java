package org.lab.samples.mongo.api.controller;

import java.util.Optional;

import org.lab.samples.mongo.api.resources.ProductResource;
import org.lab.samples.mongo.shared.model.Product;
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
@RequestMapping(value = "/products", produces = "application/hal+json")
@Api(tags = "Products")
public class ProductController {

	@Autowired
	@Qualifier("sharedMongoTemplate")
	private MongoTemplate sharedMongoTemplate;

	@GetMapping("/{id}")
	public ResponseEntity<ProductResource> findById(@PathVariable String id) {
		Product product = sharedMongoTemplate.findById(id, Product.class);
		Optional<ProductResource> optional = product != null ? Optional.of(new ProductResource(product)) : Optional.empty();
		return ResponseEntity.of(optional);
	}
}
