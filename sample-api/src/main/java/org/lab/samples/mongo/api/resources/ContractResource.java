package org.lab.samples.mongo.api.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.lab.samples.mongo.api.controller.AgreementController;
import org.lab.samples.mongo.api.controller.ContractController;
import org.lab.samples.mongo.api.controller.PersonController;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.model.Person;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;

@Relation(collectionRelation = "contracts")
public class ContractResource extends ResourceSupport {

	@Getter
	private Contract contract;

	public ContractResource(Contract contract) {
		this.contract = contract;
		add(linkTo(methodOn(ContractController.class).findById(contract.getId())).withSelfRel());
		if (contract.getAgreement() != null) {
			add(linkTo(methodOn(AgreementController.class).findById(contract.getAgreement().getId())).withRel("agreement"));
		}
		if (contract.getHolder() != null) {
			add(linkTo(methodOn(PersonController.class).findById(contract.getHolder().getId())).withRel("holder"));
		}
		if (contract.getRecipients() != null) {
			for (int i = 0; i < contract.getRecipients().size(); i++) {
				Person recipient = contract.getRecipients().get(i);
				add(linkTo(methodOn(PersonController.class).findById(recipient.getId())).withRel("recipient_" + 1));
			}
		}
	}

}
