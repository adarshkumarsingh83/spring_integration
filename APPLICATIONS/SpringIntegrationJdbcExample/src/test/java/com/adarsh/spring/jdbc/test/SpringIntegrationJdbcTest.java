/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adarsh.spring.jdbc.test;

import com.adarsh.spring.jdbc.entities.Gender;
import com.adarsh.spring.jdbc.entities.Person;
import com.adarsh.spring.jdbc.service.PersonService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/configuration/applicationContext.xml"})
public final class SpringIntegrationJdbcTest {

	private static final Logger LOGGER = Logger.getLogger(SpringIntegrationJdbcTest.class);

    @Autowired
    private PersonService personService;

    @Test
	public void testPersonService() {

		LOGGER.info("\n========================================================="
				  + "\n                                                         "
				  + "\n          Welcome to Spring Integration JDBC             "
				  + "\n                                                         "
				  + "\n    For more information please visit:                   "
				  + "\n    http://www.springsource.org/spring-integration       "
				  + "\n    http://adarshkumarsingh83.blogspot.in/               "
				  + "\n                                                         "
				  + "\n=========================================================" );

        System.setIn(System.in);
		final Scanner scanner = new Scanner(System.in);


		LOGGER.info("\n========================================================="
				  + "\n                                                         "
				  + "\n    Please press 'q + Enter' to quit the application.    "
				  + "\n                                                         "
				  + "\n=========================================================" );

		System.out.println("Please enter a choice and press <enter>: ");
		System.out.println("\t1. Find person details");
		System.out.println("\t2. Create a new person detail");
		System.out.println("\tq. Quit the application");
		System.out.print("Enter you choice: ");
		while (true) {
			final String input = scanner.nextLine();
			if("1".equals(input.trim())) {
				this.getPersonDetails(scanner);
			} else if("2".equals(input.trim())) {
				this.createPersonDetails(scanner);
			} else if("q".equals(input.trim())) {
				break;
			} else {
				System.out.println("Invalid choice\n\n");
			}

			System.out.println("Please enter a choice and press <enter>: ");
			System.out.println("\t1. Find person details");
			System.out.println("\t2. Create a new person detail");
			System.out.println("\tq. Quit the application");
			System.out.print("Enter you choice: ");
		}

		LOGGER.info("Exiting application...bye.");

		System.exit(0);

	}

	private void createPersonDetails(final Scanner scanner) {
		while(true) {

            System.out.print("\nEnter the Person's Id:");
            Integer personId = Integer.parseInt(scanner.nextLine());

			System.out.print("\nEnter the Person's name:");
			String name = scanner.nextLine();
			Gender gender;
			while(true) {
				System.out.print("Enter the Person's gender(M/F):");
				String genderStr = scanner.nextLine();
				if("m".equalsIgnoreCase(genderStr) || "f".equalsIgnoreCase(genderStr)) {
					gender = Gender.getGenderByIdentifier(genderStr.toUpperCase());
					break;
				}
			}
			Date dateOfBirth;
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			while(true) {
				System.out.print("Enter the Person's Date of birth in DD/MM/YYYY format:");
				String dobStr = scanner.nextLine();
				try {
					dateOfBirth = format.parse(dobStr);
					break;
				} catch (ParseException e) {
					//Silently suppress and ask to enter details again
				}
			}

			Person person = new Person();
            person.setPersonId(personId);
			person.setDateOfBirth(dateOfBirth);
			person.setGender(gender);
			person.setName(name);
			person = this.personService.createPerson(person);
			System.out.println("Created person record with id: " + person.getPersonId());
			System.out.print("Do you want to create another person? (y/n)");
			String choice  = scanner.nextLine();
			if(!"y".equalsIgnoreCase(choice))
				break;
		}
	}

	private void getPersonDetails(final Scanner scanner) {
		while(true) {
			System.out.print("Please enter the name of the person and press<enter>: ");
			String input = scanner.nextLine();
			final List<Person> personList = this.personService.findPersonByName(input);
			if(personList != null && !personList.isEmpty()) {
				for(Person person:personList) {
					System.out.print(
							String.format("Person found - Person Id: '%d', Person Name is: '%s',  Gender: '%s'",
                                    person.getPersonId(), person.getName(), person.getGender()));
					System.out.println(String.format(", Date of birth: '%1$td/%1$tm/%1$tC%1$ty'", person.getDateOfBirth()));
				}
			} else {
				System.out.println(
						String.format("No Person record found for name: '%s'.", input));
			}
			System.out.print("Do you want to find another person? (y/n)");
			String choice  = scanner.nextLine();
			if(!"y".equalsIgnoreCase(choice))
				break;
		}

	}
}
