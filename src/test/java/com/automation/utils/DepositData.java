package com.automation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for deposit test data.
 */
public class DepositData {
    @JsonProperty("amount")
    private String amount;

    public String getAmount() {
        return amount;
    }

    /**
     * Returns a readable string representation for parameterized test display names.
     *
     * @return formatted string with amount
     */
    @Override
    public String toString() {
        return amount;
    }
}
