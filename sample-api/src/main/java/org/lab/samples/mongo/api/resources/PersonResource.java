package org.lab.samples.mongo.api.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.lab.samples.mongo.api.controller.PersonController;
import org.lab.samples.mongo.api.model.Person;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;

@Relation(collectionRelation = "persons")
public class PersonResource extends ResourceSupport {

	@Getter
	private Person person;

	public PersonResource(Person person) {
		this.person = person;
		add(linkTo(methodOn(PersonController.class).findById(person.getId())).withSelfRel());
	}

}
