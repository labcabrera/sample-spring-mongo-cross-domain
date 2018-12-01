package org.lab.samples.mongo.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Autowired
	private Environment env;

	@Bean
	@ConditionalOnMissingBean
	public Docket api() {
		//@formatter:off
		return new Docket(DocumentationType.SWAGGER_2) 
			.select()
			.apis(RequestHandlerSelectors.basePackage(env.getProperty("app.api.swagger.base-package")))
			.paths(PathSelectors.any())
			.build()
			.apiInfo(apiInfo());
		//@formatter:on
	}

	private ApiInfo apiInfo() { //@formatter:off
		return new ApiInfoBuilder() 
			.title(env.getProperty("app.api.swagger.doc.title"))
			.description(env.getProperty("app.api.swagger.doc.description"))
			.contact(new Contact(
				env.getProperty("app.api.swagger.doc.contact.name"),
				env.getProperty("app.api.swagger.doc.contact.url"),
				env.getProperty("app.api.swagger.doc.contact.email")))
			.licenseUrl("")
			.license("")
			.termsOfServiceUrl("")
			.version(env.getProperty("app.api.swagger.doc.version"))
			.build();
	} //@formatter:on

}