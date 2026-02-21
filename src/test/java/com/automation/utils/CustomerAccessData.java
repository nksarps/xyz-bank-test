package com.automation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for customer access test data.
 */
public class CustomerAccessData {
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("postCode")
    private String postCode;

    public CustomerAccessData() {
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

    /**
     * Gets the full name (first name + last name).
     *
     * @return full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Gets the full name with a unique suffix appended to last name.
     *
     * @param uniqueSuffix suffix to append (e.g., timestamp)
     * @return full name with unique suffix
     */
    public String getFullNameWithSuffix(String uniqueSuffix) {
        return firstName + " " + lastName + uniqueSuffix;
    }
}
