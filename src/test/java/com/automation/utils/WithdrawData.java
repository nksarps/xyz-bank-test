package com.automation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for withdrawal test data.
 */
public class WithdrawData {
    @JsonProperty("depositAmount")
    private String depositAmount;

    @JsonProperty("withdrawAmount")
    private String withdrawAmount;

    public WithdrawData() {
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }
}
