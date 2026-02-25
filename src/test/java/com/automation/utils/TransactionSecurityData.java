package com.automation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for transaction security test data.
 */
public class TransactionSecurityData {
    @JsonProperty("depositAmount")
    private String depositAmount;

    @JsonProperty("withdrawalAmount")
    private String withdrawalAmount;

    public String getDepositAmount() {
        return depositAmount;
    }

    public String getWithdrawalAmount() {
        return withdrawalAmount;
    }
}
