package org.lab.samples.mongo.api.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "mongodb")
public class ApiMongoProperties {

	private MongoProperties app = new MongoProperties();
	private MongoProperties shared = new MongoProperties();

}