package org.lab.samples.mongo.api.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromController;

import java.util.Optional;

import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.resources.ContractResource;
import org.lab.samples.mongo.api.service.ContractSearchService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/contracts", produces = "application/hal+json")
@Api(tags = "Contracts")
public class ContractController {

	@Autowired
	private ContractSearchService service;

	@Autowired
	private PagedResourcesAssembler<Contract> pagedAssembler;

	@GetMapping("/{id}")
	@ApiOperation(value = "Contract search by id")
	public ResponseEntity<ContractResource> findById(@PathVariable String id) {
		Optional<Contract> contract = service.findById(id);
		Optional<ContractResource> resource = contract.isPresent() ? Optional.of(new ContractResource(contract.get())) : Optional.empty();
		return ResponseEntity.of(resource);
	}

	@GetMapping
	@ApiOperation(value = "Contract search")
	//@formatter:off
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "Page number", required = false, dataType = "string", paramType = "query", defaultValue = "0"),
		@ApiImplicitParam(name = "size", value = "Page size", required = false, dataType = "string", paramType = "query", defaultValue = "10"),
		@ApiImplicitParam(name = "sort", value = "Sort expression", required = false, dataType = "string", paramType = "query", example = "c") })
	public ResponseEntity<PagedResources<ContractResource>> find(
			@ApiParam(value = "Search expression", required = false) @RequestParam(name = "search", required = false, defaultValue = "") String search,
			@ApiIgnore @PageableDefault(sort = "code") Pageable pageable) { //@formatter:on
		pageable = pageable != null ? pageable : PageRequest.of(0, 10);
		Page<Contract> page = service.findAll(search, pageable);
		PagedResources<ContractResource> pr = pagedAssembler.toResource(page, (e) -> new ContractResource(e));
		pr.add(new Link(fromController(ProductController.class).build().toString(), "contracts"));
		return ResponseEntity.ok(pr);
	}
}
