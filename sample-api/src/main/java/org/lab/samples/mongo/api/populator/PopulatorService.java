package org.lab.samples.mongo.api.populator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.validator.internal.util.Contracts;
import org.lab.samples.mongo.api.model.Contract;
import org.lab.samples.mongo.api.model.Person;
import org.lab.samples.mongo.shared.model.Agreement;
import org.lab.samples.mongo.shared.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PopulatorService {

	@Autowired
	private MongoTemplate mongoTemplate;

	private MongoTemplate sharedMongoTemplate;

	public PopulatorService() {
		MongoClient mongoClient = new MongoClient("localhost");
		sharedMongoTemplate = new MongoTemplate(mongoClient, "mongo-sample-shared");
	}

	public void check() {
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
		List<String> persons = new ArrayList<>();
		persons.add("John|Doe|70111222A");
		persons.add("Alice|Smith|70333000B");
		persons.add("Bob|Kundera|70555111C");
		persons.stream().map(e -> {
			String[] split = e.split("\\|");
			Person person = new Person();
			person.setName(split[0]);
			person.setSurname(split[1]);
			person.setIdCardNumber(split[2]);
			return person;
		}).forEach(p -> mongoTemplate.save(p));
	}

	private void populateContracts() {
		List<String> contracts = new ArrayList<>();
		contracts.add("10001|100000000001|70111222A|70111222A");
		contracts.add("10001|100000000002|70111222A|70111222A");
		contracts.add("10001|100000000003|70111222A|70111222A");
		contracts.add("10001|100000000004|70111222A|70111222A");
		contracts.add("10001|100000000005|70111222A|70111222A,70555111C");
		contracts.add("10002|100000000006|70333000B|70333000B,70333000B");
		contracts.add("10002|100000000007|70333000B|70333000B");
		contracts.add("10002|100000000008|70333000B|70333000B");
		contracts.add("20001|100000000009|70333000B|70333000B");
		contracts.add("20001|100000000010|70333000B|70333000B");
		contracts.add("20002|100000000011|70555111C|70555111C");
		contracts.add("30001|100000000012|70555111C|70555111C,70111222A,70333000B");
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
		query.addCriteria(Criteria.where("idCardNumber").is(idCard));
		Person person = mongoTemplate.findOne(query, Person.class);
		return person;
	}

}
