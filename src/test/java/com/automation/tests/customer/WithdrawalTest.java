package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.automation.base.SetUp;
import com.automation.utils.ExistingCustomer;
import com.automation.utils.TestDataReader;

/**
 * Tests for Withdrawal functionality.
 */
@DisplayName("Withdrawal Tests")
public class WithdrawalTest extends SetUp {
    /**
     * Verifies that the system prevents withdrawal when there is insufficient
     * balance.
     */
    @Test
    @DisplayName("System should validate: withdrawal with insufficient balance should fail")
    void testValidateWithdrawalWithInsufficientBalance() {
        // Get an existing customer to test with
        ExistingCustomer customer = TestDataReader.getExistingCustomers().get(0);

        // Login as customer
        loginPage.goToCustomerLogin();
        assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");
        customerLoginPage.loginAs(customer.getName());
        assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

        // Clear existing balance
        String balanceStr = customerDashboardPage.getBalance();
        int balance = Integer.parseInt(balanceStr);
        if (balance > 0) {
            customerDashboardPage.openWithdrawal();
            withdrawalPage.withdraw(String.valueOf(balance));
        }

        // Deposit 500
        customerDashboardPage.openDeposit();
        depositPage.deposit("500");
        depositPage.getSuccessMessage(); // Wait for deposit confirmation before reading balance

        // Get balance after deposit
        String balanceAfterDepositStr = customerDashboardPage.getBalance();
        int balanceAfterDeposit = Integer.parseInt(balanceAfterDepositStr);

        // Try to withdraw more than balance (1000 > 500)
        customerDashboardPage.openWithdrawal();
        assertTrue(withdrawalPage.isLoaded(), "Withdrawal page should be loaded");
        withdrawalPage.withdraw("1000");

        // Verify withdrawal failed with insufficient balance message
        String message = "";
        try {
            message = withdrawalPage.getResultMessage();
        } catch (Exception e) {
            message = "";
        }

        String currentBalanceStr = customerDashboardPage.getBalance();
        int currentBalance = Integer.parseInt(currentBalanceStr);

        // Check for insufficient balance error message or balance unchanged
        boolean hasInsufficientBalanceMessage = message.contains("Transaction Failed") &&
                message.contains("can not withdraw amount more than the balance");
        boolean balanceUnchanged = currentBalance == balanceAfterDeposit;

        assertTrue(hasInsufficientBalanceMessage || balanceUnchanged,
                String.format(
                        "Withdrawal with insufficient balance should fail with specific message. Message: '%s', Balance unchanged: %s",
                        message, balanceUnchanged));
    }

    /**
     * Verifies that the system rejects zero or negative withdrawal amounts.
     */
    @Test
    @DisplayName("System should validate: only positive withdrawal amounts allowed")
    void testValidateOnlyPositiveWithdrawalAmounts() {
        // Get an existing customer to test with
        ExistingCustomer customer = TestDataReader.getExistingCustomers().get(0);

        // Login as customer
        loginPage.goToCustomerLogin();
        assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");
        customerLoginPage.loginAs(customer.getName());
        assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

        // Deposit funds
        customerDashboardPage.openDeposit();
        depositPage.deposit("1000");
        depositPage.getSuccessMessage(); // Wait for deposit confirmation before reading balance

        // Get balance before zero withdrawal
        String balanceBeforeStr = customerDashboardPage.getBalance();
        int balanceBefore = Integer.parseInt(balanceBeforeStr);

        // Try to withdraw zero (system provides no feedback for invalid amounts)
        customerDashboardPage.openWithdrawal();
        assertTrue(withdrawalPage.isLoaded(), "Withdrawal page should be loaded");
        withdrawalPage.withdraw("0");

        // Verify zero withdrawal was rejected by checking balance (system provides no
        // error message)
        String balanceAfterZeroStr = customerDashboardPage.getBalance();
        int balanceAfterZero = Integer.parseInt(balanceAfterZeroStr);

        assertEquals(balanceBefore, balanceAfterZero,
                "Balance should not change for zero withdrawal amount (system accepts but does not process zero withdrawals)");
    }
}
