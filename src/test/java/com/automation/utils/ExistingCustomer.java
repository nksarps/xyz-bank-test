package com.automation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for existing customer test data.
 */
public class ExistingCustomer {
    @JsonProperty("name")
    private String name;

    public ExistingCustomer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
