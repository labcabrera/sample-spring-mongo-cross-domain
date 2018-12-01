package org.lab.samples.mongo.api.service;

import org.lab.samples.mongo.api.model.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonSearchService extends RsqlSearchService<Person> {

	public PersonSearchService() {
		super(Person.class);
	}

}
