
package com.adarsh.spring.jdbc.entities;

import java.util.Date;

/**
 * A simple POJO representing a Person
 */
public class Person {

    private Integer personId;
    private String name;
    private Gender gender;
    private Date dateOfBirth;
	

    /**
     * Sets the person id
     *
     * @return
     */
    public Integer getPersonId() {
        return personId;
    }

    /**
     * Get the person Id
     *
     * @param personId
     */
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    /**
     * Gets the name of the person
     *
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the gender of the person
     *
     * @return
     */
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the date of birth of the person
     *
     * @return
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
