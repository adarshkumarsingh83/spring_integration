
package com.adarsh.spring.jdbc.entities;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the gender of the person
 */
public enum Gender {

    MALE("M"), FEMALE("F");
    private static Map<String, Gender> map;

    private String identifier;

    private Gender(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static Gender getGenderByIdentifier(String identifier) {
        return map.get(identifier);
    }

    static {
        map = new HashMap<String, Gender>();
        for (Gender gender : EnumSet.allOf(Gender.class)) {
            map.put(gender.getIdentifier(), gender);
        }
    }
}
