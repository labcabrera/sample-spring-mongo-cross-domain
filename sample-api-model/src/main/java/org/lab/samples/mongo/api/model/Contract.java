package org.lab.samples.mongo.api.model;

import java.util.List;

import org.lab.samples.mongo.shared.model.Agreement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Document(collection = "contracts")
@Data
@ToString
public class Contract {

	@Id
	private String id;

	private String contractNumber;

	@DBRef(db = "mongo-sample-shared")
	private Agreement agreement;

	@DBRef
	private Person holder;

	@DBRef
	private List<Person> recipients;

}
