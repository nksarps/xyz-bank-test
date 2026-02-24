package com.automation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for customer test data.
 */
public class CustomerData {
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("postCode")
    private String postCode;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostCode() {
        return postCode;
    }


    /**
     * Returns a readable string representation for parameterized test display names.
     *
     * @return formatted string with first name, last name, and post code
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + postCode + ")";
    }
}
