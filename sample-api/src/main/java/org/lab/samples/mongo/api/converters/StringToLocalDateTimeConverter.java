package org.lab.samples.mongo.api.converters;

import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

	@Override
	public LocalDateTime convert(String source) {
		return LocalDateTime.parse(source);
	}

}