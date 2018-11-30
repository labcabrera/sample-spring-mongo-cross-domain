package org.lab.samples.mongo.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Document(collection = "persons")
@Data
@ToString
public class Person {

	@Id
	private String id;

	private String name;

	private String surname;

	private String idCardNumber;
}
