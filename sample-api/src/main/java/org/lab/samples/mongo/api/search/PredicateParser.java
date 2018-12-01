package org.lab.samples.mongo.api.search;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.vineey.rql.filter.FilterContext;
import com.github.vineey.rql.filter.parser.DefaultFilterParser;
import com.github.vineey.rql.querydsl.filter.QuerydslFilterBuilder;
import com.github.vineey.rql.querydsl.filter.QuerydslFilterParam;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;

@Component
public class PredicateParser {

	@SuppressWarnings("rawtypes")
	public Optional<Predicate> buildPredicate(String searchExpression, Map<String, Path> pathMapping) {
		if (searchExpression == null || StringUtils.isEmpty(searchExpression)) {
			return Optional.empty();
		}
		try {
			searchExpression = URLDecoder.decode(searchExpression, "UTF-8");
		}
		catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		try {
			DefaultFilterParser parser = new DefaultFilterParser();
			Predicate predicate = parser.parse(searchExpression,
					FilterContext.withBuilderAndParam(new QuerydslFilterBuilder(), new QuerydslFilterParam().setMapping(pathMapping)));
			return Optional.of(predicate);
		}
		catch (Exception ex) {
			throw new RuntimeException(searchExpression, ex);
		}
	}

}