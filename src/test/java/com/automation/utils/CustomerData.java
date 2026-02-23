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

    public CustomerData() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getFullName() {
        return firstName + " " + lastName;
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
