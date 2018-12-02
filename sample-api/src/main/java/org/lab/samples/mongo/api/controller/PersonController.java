package org.lab.samples.mongo.api.controller;

import java.util.Optional;

import org.lab.samples.mongo.api.model.Person;
import org.lab.samples.mongo.api.resources.PersonResource;
import org.lab.samples.mongo.api.service.PersonService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/persons", produces = "application/hal+json")
@Api(tags = "Persons")
public class PersonController {

	@Autowired
	private PersonService service;

	@Autowired
	private PagedResourcesAssembler<Person> pagedAssembler;

	@GetMapping("/{id}")
	@ApiOperation(value = "Person search by id")
	public ResponseEntity<PersonResource> findById(@PathVariable String id) {
		Optional<Person> contract = service.findById(id);
		Optional<PersonResource> resource = contract.isPresent() ? Optional.of(new PersonResource(contract.get())) : Optional.empty();
		return ResponseEntity.of(resource);
	}

	@GetMapping
	@ApiOperation(value = "Person search")
	//@formatter:off
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "Page number", required = false, dataType = "string", paramType = "query", defaultValue = "0"),
		@ApiImplicitParam(name = "size", value = "Page size", required = false, dataType = "string", paramType = "query", defaultValue = "10"),
		@ApiImplicitParam(name = "sort", value = "Sort expression", required = false, dataType = "string", paramType = "query", example = "surname,asc") })
	public ResponseEntity<PagedResources<PersonResource>> find(
			@ApiParam(value = "Search expression", required = false) @RequestParam(name = "search", required = false, defaultValue = "") String search,
			@ApiIgnore @PageableDefault(sort = "surname,name") Pageable pageable) { //@formatter:on
		pageable = pageable != null ? pageable : PageRequest.of(0, 10);
		Page<Person> page = service.findAll(search, pageable);
		PagedResources<PersonResource> pr = pagedAssembler.toResource(page, (e) -> new PersonResource(e));
		return ResponseEntity.ok(pr);
	}
}
