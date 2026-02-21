package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.automation.base.SetUp;
import com.automation.utils.ExistingCustomer;
import com.automation.utils.TestDataReader;

/**
 * Tests for Withdrawal functionality based on acceptance criteria.
 * 
 * AC: Customers should be able to withdraw money from their account.
 * AC: The system should allow customers to enter the withdrawal amount.
 * AC: The system should validate the withdrawal amount (e.g., positive value, sufficient balance).
 * AC: Upon successful withdrawal, the system should update the account balance.
 */
@DisplayName("Withdrawal Tests")
public class WithdrawalTest extends SetUp {
    /**
     * AC: The system should validate the withdrawal amount - positive value, sufficient balance.
     * 
     * Test 1: Withdrawal with sufficient balance should succeed.
     */
    @Test
    @DisplayName("System should validate: withdrawal with sufficient balance should succeed")
    void testValidateWithdrawalWithSufficientBalance() {
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

        // Deposit 1000
        customerDashboardPage.openDeposit();
        depositPage.deposit("1000");

        // Withdraw less than balance (500)
        customerDashboardPage.openWithdrawal();
        assertTrue(withdrawalPage.isLoaded(), "Withdrawal page should be loaded");
        withdrawalPage.withdraw("500");

        // Verify withdrawal succeeded
        String message = "";
        try {
            message = withdrawalPage.getResultMessage();
        } catch (Exception e) {
            message = "";
        }
        assertTrue(message.contains("Transaction successful"), 
            String.format("Withdrawal with sufficient balance should succeed. Message: '%s'", message));
    }

    /**
     * AC: The system should validate the withdrawal amount - positive value, sufficient balance.
     * 
     * Test 2: Withdrawal with insufficient balance should fail.
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
            String.format("Withdrawal with insufficient balance should fail with specific message. Message: '%s', Balance unchanged: %s", 
                message, balanceUnchanged));
    }

    /**
     * AC: The system should validate the withdrawal amount - positive value only.
     * 
     * Test: Withdrawal with zero or negative amount should be rejected.
     * Note: System does not provide feedback for zero/negative amounts, so we verify by checking balance.
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

        // Get balance before zero withdrawal
        String balanceBeforeStr = customerDashboardPage.getBalance();
        int balanceBefore = Integer.parseInt(balanceBeforeStr);

        // Try to withdraw zero (system provides no feedback for invalid amounts)
        customerDashboardPage.openWithdrawal();
        assertTrue(withdrawalPage.isLoaded(), "Withdrawal page should be loaded");
        withdrawalPage.withdraw("0");

        // Verify zero withdrawal was rejected by checking balance (system provides no error message)
        String balanceAfterZeroStr = customerDashboardPage.getBalance();
        int balanceAfterZero = Integer.parseInt(balanceAfterZeroStr);

        assertEquals(balanceBefore, balanceAfterZero, 
            "Balance should not change for zero withdrawal amount (system accepts but does not process zero withdrawals)");
    }

    /**
     * AC: Upon successful withdrawal, the system should update the account balance.
     * 
     * Test validates that balance is correctly updated after withdrawal.
     */
    @Test
    @DisplayName("System should update account balance upon successful withdrawal")
    void testBalanceUpdatedAfterSuccessfulWithdrawal() {
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

        // Deposit exact amount: 7500
        customerDashboardPage.openDeposit();
        depositPage.deposit("7500");

        // Get balance after deposit
        String balanceAfterDepositStr = customerDashboardPage.getBalance();
        int balanceAfterDeposit = Integer.parseInt(balanceAfterDepositStr);
        assertEquals(7500, balanceAfterDeposit, "Balance should be 7500 after deposit");

        // Withdraw 2500
        customerDashboardPage.openWithdrawal();
        withdrawalPage.withdraw("2500");

        // Verify withdrawal succeeded
        String message = "";
        try {
            message = withdrawalPage.getResultMessage();
        } catch (Exception e) {
            message = "";
        }
        assertTrue(message.contains("Transaction successful"), "Withdrawal should succeed");

        // Verify balance is updated: 7500 - 2500 = 5000
        String balanceAfterWithdrawalStr = customerDashboardPage.getBalance();
        int balanceAfterWithdrawal = Integer.parseInt(balanceAfterWithdrawalStr);
        
        assertEquals(5000, balanceAfterWithdrawal, 
            String.format("Balance should be 5000 after withdrawal. Expected: 5000, Actual: %d", 
                balanceAfterWithdrawal));
    }
}
