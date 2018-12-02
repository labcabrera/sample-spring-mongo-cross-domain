package org.lab.samples.mongo.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.lab.samples.mongo.shared.model.Country;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	private IdCard idCard;

	private LocalDate birthDate;

	@DBRef(db = "mongo-sample-shared")
	@JsonIgnoreProperties("name")
	private Country birthCountry;

	@DBRef(db = "mongo-sample-shared")
	@JsonIgnoreProperties("name")
	private List<Country> nationalities;

	private LocalDateTime created;
}
