package org.lab.samples.mongo.api.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.lab.samples.mongo.api.controller.AgreementController;
import org.lab.samples.mongo.api.controller.ContractController;
import org.lab.samples.mongo.api.model.Contract;
import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;

public class ContractResource extends ResourceSupport {

	@Getter
	private Contract contract;

	public ContractResource(Contract contract) {
		this.contract = contract;
		add(linkTo(methodOn(ContractController.class).findById(contract.getId())).withSelfRel());
		if (contract.getAgreement() != null) {
			add(linkTo(methodOn(AgreementController.class).findById(contract.getAgreement().getId())).withRel("agreement"));
		}
	}

}
