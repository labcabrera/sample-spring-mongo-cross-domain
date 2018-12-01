package org.lab.samples.mongo.shared.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Country {

	@Id
	private String id;

	private String name;

	public Country(String id) {
		this.id = id;
	}

}
