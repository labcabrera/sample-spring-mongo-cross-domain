package org.lab.samples.mongo.shared.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "agreements")
@Data
public class Agreement {

	@Id
	private String id;

	@NotBlank
	private String name;

	@DBRef
	@NotNull
	private Product product;

}
