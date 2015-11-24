package com.adarsh.spring.jdbc.service;
import com.adarsh.spring.jdbc.entities.Person;

import java.util.List;

/**
 * The Service used to create Person instance in database
 *
 */
public interface PersonService {

	/**
	 * Creates a {@link Person} instance from the {@link Person} instance passed
	 *
	 * @param person created person instance, it will contain the generated primary key and the formatted name
	 * @return the retrieved {@link Person}
	 */
	public Person createPerson(Person person);

	/**
	 * Find the person by the person name, the name search is case insensitive, however the
	 * spaces are not ignored
	 *
	 * @param name
	 * @return the matching {@link Person} record
	 */
	public List<Person> findPersonByName(String name);

}
