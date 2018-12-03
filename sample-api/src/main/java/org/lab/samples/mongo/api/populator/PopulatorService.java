package org.lab.samples.mongo.api.populator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.Contracts;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.model.IdCard;
import org.lab.samples.mongo.api.model.Person;
import org.lab.samples.mongo.shared.model.Agreement;
import org.lab.samples.mongo.shared.model.Country;
import org.lab.samples.mongo.shared.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PopulatorService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	@Qualifier("sharedMongoTemplate")
	private MongoTemplate sharedMongoTemplate;

	public void check() {
		if (sharedMongoTemplate.count(new Query(), Country.class) == 0) {
			populateCountries();
		}
		if (sharedMongoTemplate.count(new Query(), Product.class) == 0) {
			populateProducts();
		}
		if (sharedMongoTemplate.count(new Query(), Agreement.class) == 0) {
			populateAgreements();
		}
		if (mongoTemplate.count(new Query(), Person.class) == 0) {
			populatePersons();
		}
		if (mongoTemplate.count(new Query(), Contracts.class) == 0) {
			populateContracts();
		}
	}

	private void populateCountries() {
		log.info("Loading countries");
		List<String> countries = new ArrayList<>();
		countries.add("ESP|Spain");
		countries.add("ITA|Italy");
		countries.add("FRA|France");
		countries.stream().map(e -> {
			String[] split = e.split("\\|");
			return new Country(split[0], split[1]);
		}).forEach(p -> sharedMongoTemplate.save(p));
	}

	private void populateProducts() {
		log.info("Loading products");
		Arrays.asList("100", "200", "300").stream().map(e -> {
			Product product = new Product();
			product.setId(e);
			product.setName("Product " + e);
			return product;
		}).forEach(p -> sharedMongoTemplate.save(p));
	}

	private void populateAgreements() {
		log.info("Loading agreements");
		Arrays.asList("10001", "10002", "10003", "20001", "20002", "30001").stream().map(e -> {
			Agreement agreement = new Agreement();
			agreement.setId(e);
			agreement.setName("Agreement " + e);
			agreement.setProduct(new Product());
			agreement.getProduct().setId(e.substring(0, 3));
			return agreement;
		}).forEach(p -> sharedMongoTemplate.save(p));
	}

	private void populatePersons() {
		log.info("Loading persons");
		List<String> persons = new ArrayList<>();
		persons.add("John  |Doe    |70111222A|1971-05-25|ESP|ESP    |2018-03-03T12:50:16.204");
		persons.add("Alice |Smith  |70333000B|1980-08-30|FRA|ESP,FRA|2018-04-04T12:50:16.204");
		persons.add("Bob   |Kundera|70555111C|1963-04-11|ITA|ITA    |2018-05-05T12:50:16.204");
		persons.add("Jack  |Auster |70666888D|1971-03-11|ESP|ESP    |2018-06-06T12:50:16.204");
		persons.stream().map(e -> {
			String[] split = e.split("\\|");
			List<String> nationalities = Arrays.asList(split[5].split(","));
			Person person = new Person();
			person.setName(split[0].trim());
			person.setSurname(split[1].trim());
			person.setIdCard(new IdCard(split[2].trim()));
			person.setBirthDate(LocalDate.parse(split[3].trim()));
			person.setBirthCountry(new Country(split[4].trim()));
			person.setNationalities(nationalities.stream().map(c -> new Country(c)).collect(Collectors.toList()));
			person.setCreated(LocalDateTime.parse(split[6].trim()));
			return person;
		}).forEach(p -> mongoTemplate.save(p));
	}

	private void populateContracts() {
		log.info("Loading contracts");
		List<String> contracts = new ArrayList<>();
		contracts.add("10001|100000000001|70111222A|70111222A");
		contracts.add("10001|100000000002|70111222A|70111222A");
		contracts.add("10001|100000000003|70111222A|70111222A");
		contracts.add("10001|100000000004|70111222A|70111222A");
		contracts.add("10001|100000000005|70111222A|70111222A,70555111C");
		contracts.add("10002|100000000006|70333000B|70333000B,70111222A");
		contracts.add("10002|100000000007|70333000B|70333000B");
		contracts.add("10002|100000000008|70333000B|70333000B");
		contracts.add("20001|100000000009|70333000B|70333000B");
		contracts.add("20001|100000000010|70333000B|70333000B");
		contracts.add("20002|100000000011|70555111C|70555111C");
		contracts.add("30001|100000000012|70555111C|70666888D");
		contracts.stream().map(e -> {
			String[] split = e.split("\\|");
			List<String> recipients = Arrays.asList(split[3].split(","));
			Contract contract = new Contract();
			contract.setAgreement(new Agreement());
			contract.getAgreement().setId(split[0]);
			contract.setContractNumber(split[1]);
			contract.setHolder(findByIdCardNumber(split[2]));
			contract.setRecipients(new ArrayList<>());
			recipients.stream().forEach(r -> contract.getRecipients().add(findByIdCardNumber(r)));
			return contract;
		}).forEach(c -> mongoTemplate.save(c));
	}

	private Person findByIdCardNumber(String idCard) {
		Query query = new Query();
		query.addCriteria(Criteria.where("idCard.number").is(idCard));
		Person person = mongoTemplate.findOne(query, Person.class);
		Assert.notNull(person, "Unknow person with id card number " + idCard);
		return person;
	}

}
