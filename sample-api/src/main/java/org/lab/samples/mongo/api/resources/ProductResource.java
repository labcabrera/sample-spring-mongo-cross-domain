package org.lab.samples.mongo.api.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.lab.samples.mongo.api.controller.ProductController;
import org.lab.samples.mongo.shared.model.Product;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;

@Relation(collectionRelation = "products")
public class ProductResource extends ResourceSupport {

	@Getter
	private Product product;

	public ProductResource(Product product) {
		this.product = product;
		add(linkTo(methodOn(ProductController.class).findById(product.getId())).withSelfRel());

	}

}
