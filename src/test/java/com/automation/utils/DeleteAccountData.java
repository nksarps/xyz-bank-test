package com.automation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for delete account test data.
 */
public class DeleteAccountData {
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("postCode")
    private String postCode;

    @JsonProperty("currency")
    private String currency;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the full name with a suffix appended to the last name.
     *
     * @param suffix suffix to append to last name
     * @return full name with suffix
     */
    public String getFullNameWithSuffix(String suffix) {
        return firstName + " " + lastName + suffix;
    }
}
