package org.lab.samples.mongo.api;

import org.junit.Assume;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.lab.samples.mongo.api.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchPersonByBirthDatePredicate {

	@Autowired
	private PersonRepository repository;

	@Before
	public void check() {
		Assume.assumeTrue(repository.count() == 3L);
	}

	// @Test
	// public void testAfter() {
	// Predicate predicate = QPerson.person.birthDate.after(LocalDate.parse("1975-01-01"));
	// long count = repository.count(predicate);
	// Assert.assertEquals(1, count);
	// }
	//
	// @Test
	// public void testBefore() {
	// Predicate predicate = QPerson.person.birthDate.before(LocalDate.parse("1975-01-01"));
	// long count = repository.count(predicate);
	// Assert.assertEquals(2, count);
	// }
	//
	// @Test
	// public void testBetween() {
	// Predicate predicate = QPerson.person.birthDate.between(LocalDate.parse("1980-01-01"),
	// LocalDate.parse("1980-12-31"));
	// long count = repository.count(predicate);
	// Assert.assertEquals(1, count);
	// }

}
