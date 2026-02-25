package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import com.automation.base.SetUp;
import com.automation.utils.ExistingCustomer;
import com.automation.utils.TestDataReader;
import com.automation.utils.TransactionSecurityData;

import io.qameta.allure.*;

/**
 * Tests for Transaction Security functionality.
 */
@Epic("Customer Banking Operations")
@Feature("Transaction Security")
@Story("US-2: Customers cannot reset or alter transaction history")
@DisplayName("Transaction Security Tests")
public class TransactionSecurityTest extends SetUp {

    /**
     * Verifies that customers cannot reset or alter their transactions.
     */
    @Test
    @DisplayName("Verify customers cannot reset or alter their transactions")
    @Description("Customers should not be able to reset or alter their transaction history")
    @Severity(SeverityLevel.CRITICAL)
    void testResetButtonShouldNotAlterAccountBalance() {
        // Load test data
        ExistingCustomer customer = TestDataReader.getExistingCustomers().get(0);
        TransactionSecurityData txData = TestDataReader.getTransactionSecurityData();

        // Login as customer
        loginPage.goToCustomerLogin();
        assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");
        customerLoginPage.loginAs(customer.getName());
        assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

        // Make a deposit
        customerDashboardPage.openDeposit();
        assertTrue(depositPage.isLoaded(), "Deposit page should be loaded");
        depositPage.deposit(txData.getDepositAmount());

        // Make a withdrawal
        customerDashboardPage.openWithdrawal();
        assertTrue(withdrawalPage.isLoaded(), "Withdrawal page should be loaded");
        withdrawalPage.withdraw(txData.getWithdrawalAmount());

        // Record balance after transactions
        assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded after withdrawal");
        String balanceBeforeReset = customerDashboardPage.getBalance();

        // Navigate to Transactions page
        customerDashboardPage.openTransactions();
        assertTrue(transactionsPage.isLoaded(), "Transactions page should be loaded");

        // Only continue if Reset button is present — the security concern only applies when it exists
        boolean resetButtonVisible = transactionsPage.isResetButtonVisible();
        assertFalse(resetButtonVisible,
                "Reset button should not be visible on the Transactions page");

        // Click Reset, then go Back to dashboard
        transactionsPage.clickReset();
        transactionsPage.clickBack();
        assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded after going back");

        // Get balance after reset
        String balanceAfterReset = customerDashboardPage.getBalance();

        // SECURITY CHECKS:
        // 1. Balance must not be zero after reset
        assertNotEquals("0", balanceAfterReset,
                "Balance must NOT be zero after reset — resetting transaction history should not wipe the account balance.");

        // 2. Balance must remain the same as before reset was clicked
        assertEquals(balanceBeforeReset, balanceAfterReset,
                "Balance after reset (" + balanceAfterReset + ") must match balance before reset (" +
                        balanceBeforeReset + "). Resetting transaction history must not alter the account balance.");
    }
}

