package com.automation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for account creation test data.
 */
public class AccountData {
    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("currency")
    private String currency;

    public AccountData() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Returns a readable string representation for parameterized test display names.
     *
     * @return formatted string with customer name and currency
     */
    @Override
    public String toString() {
        return customerName + " - " + currency;
    }
}
