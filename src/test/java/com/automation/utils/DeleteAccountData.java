package com.automation.utils;

/**
 * Data model for delete account test data.
 */
public class DeleteAccountData {
    private String firstName;
    private String lastName;
    private String postCode;

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

    public String postCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Gets the full name by combining first and last names.
     *
     * @return full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
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
