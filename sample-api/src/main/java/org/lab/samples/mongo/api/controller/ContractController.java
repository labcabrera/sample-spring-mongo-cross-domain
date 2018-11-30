package org.lab.samples.mongo.api.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromController;

import java.util.Optional;

import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.repositories.ContractRepository;
import org.lab.samples.mongo.api.resources.ContractResource;
import org.lab.samples.mongo.api.rsql.PredicateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/contracts", produces = "application/hal+json")
@Api(tags = "Contracts")
public class ContractController {

	@Autowired
	private ContractRepository repository;

	@Autowired
	private PagedResourcesAssembler<Contract> pagedAssembler;

	@Autowired
	private PredicateParser predicateParser;

	@GetMapping("/{id}")
	public ResponseEntity<ContractResource> findById(@PathVariable String id) {
		Optional<Contract> contract = repository.findById(id);
		Optional<ContractResource> resource = contract.isPresent() ? Optional.of(new ContractResource(contract.get())) : Optional.empty();
		return ResponseEntity.of(resource);
	}

	@GetMapping
	public ResponseEntity<PagedResources<ContractResource>> find(String search, @PageableDefault(sort = "code") Pageable pageable) {
		pageable = pageable != null ? pageable : PageRequest.of(0, 10);
		Optional<Predicate> predicate = predicateParser.buildPredicate(search, ContractRepository.PATH_MAP);
		Page<Contract> page = predicate.isPresent() ? repository.findAll(predicate.get(), pageable) : repository.findAll(pageable);
		PagedResources<ContractResource> pr = pagedAssembler.toResource(page, (e) -> new ContractResource(e));
		pr.add(new Link(fromController(ProductController.class).build().toString(), "contracts"));
		return ResponseEntity.ok(pr);
	}
}
