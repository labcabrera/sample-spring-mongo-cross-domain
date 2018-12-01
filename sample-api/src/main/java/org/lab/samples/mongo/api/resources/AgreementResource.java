package org.lab.samples.mongo.api.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.lab.samples.mongo.api.controller.AgreementController;
import org.lab.samples.mongo.api.controller.ProductController;
import org.lab.samples.mongo.shared.model.Agreement;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;

@Relation(collectionRelation = "agreements")
public class AgreementResource extends ResourceSupport {

	@Getter
	private Agreement agreement;

	public AgreementResource(Agreement agreement) {
		this.agreement = agreement;
		add(linkTo(methodOn(AgreementController.class).findById(agreement.getId())).withSelfRel());
		if (agreement.getProduct() != null) {
			add(linkTo(methodOn(ProductController.class).findById(agreement.getProduct().getId())).withRel("product"));
		}
	}

}
