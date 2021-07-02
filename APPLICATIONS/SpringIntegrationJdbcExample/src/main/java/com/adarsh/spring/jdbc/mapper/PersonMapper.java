
package com.adarsh.spring.jdbc.mapper;

import com.adarsh.spring.jdbc.entities.Gender;
import com.adarsh.spring.jdbc.entities.Person;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The result set mapper that will map the {@link java.sql.ResultSet} to the {@link Person} instance
 */
@Component("personResultMapper")
public class PersonMapper implements RowMapper<Person> {

    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Person person = new Person();
        person.setPersonId(rs.getInt("personId"));
        person.setName(rs.getString("name"));
        person.setGender(Gender.getGenderByIdentifier(rs.getString("gender")));
        person.setDateOfBirth(rs.getDate("dateOfBirth"));
        return person;
    }
}
