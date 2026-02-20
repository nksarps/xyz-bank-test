package com.automation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for deposit test data.
 */
public class DepositData {
    @JsonProperty("amount")
    private String amount;

    public DepositData() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
