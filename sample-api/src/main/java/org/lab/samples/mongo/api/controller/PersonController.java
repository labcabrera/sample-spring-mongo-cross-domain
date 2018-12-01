package org.lab.samples.mongo.api.controller;

import java.util.Optional;

import org.lab.samples.mongo.api.model.Person;
import org.lab.samples.mongo.api.repositories.PersonRepository;
import org.lab.samples.mongo.api.resources.PersonResource;
import org.lab.samples.mongo.api.rsql.PredicateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/persons", produces = "application/hal+json")
@Api(tags = "Persons")
public class PersonController {

	@Autowired
	private PersonRepository repository;

	@Autowired
	private PagedResourcesAssembler<Person> pagedAssembler;

	@Autowired
	private PredicateParser predicateParser;

	@GetMapping("/{id}")
	public ResponseEntity<PersonResource> findById(@PathVariable String id) {
		Optional<Person> contract = repository.findById(id);
		Optional<PersonResource> resource = contract.isPresent() ? Optional.of(new PersonResource(contract.get())) : Optional.empty();
		return ResponseEntity.of(resource);
	}

	@GetMapping
	public ResponseEntity<PagedResources<PersonResource>> find(String search, @PageableDefault(sort = "code") Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10);
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, PersonRepository.PATH_MAP);
		Page<Person> page = predicate.isPresent() ? repository.findAll(predicate.get(), pageable) : repository.findAll(pageable);
		PagedResources<PersonResource> pr = pagedAssembler.toResource(page, (e) -> new PersonResource(e));
		return ResponseEntity.ok(pr);
	}
}
