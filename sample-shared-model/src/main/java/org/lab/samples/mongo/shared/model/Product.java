package org.lab.samples.mongo.shared.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "products")
@Data
public class Product {

	@Id
	private String id;

	@NotBlank
	private String name;

}
