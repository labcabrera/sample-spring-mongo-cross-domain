package org.lab.samples.mongo.api.config;

import org.lab.samples.mongo.api.model.QContract;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

	// @Bean(name = "sharedMongoTemplate")
	// MongoTemplate sharedMongoTemplate() {
	// MongoClient mongoClient = new MongoClient("localhost");
	// return new MongoTemplate(mongoClient, "mongo-sample-shared");
	// }
	
	QContract x;

}
