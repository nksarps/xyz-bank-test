package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.automation.base.SetUp;
import com.automation.utils.ExistingCustomer;
import com.automation.utils.TestDataReader;

/**
 * Tests for Transaction Security functionality.
 */
@DisplayName("Transaction Security Tests (AC4)")
public class TransactionSecurityTest extends SetUp {

    /**
     * Verifies that customers cannot reset or clear their transaction history.
     */
    @Test
    @DisplayName("SECURITY: Customers should NOT have access to Reset button on Transactions page")
    void testResetButtonShouldNotBeAvailableToCustomers() {
        // Get an existing customer to test with
        ExistingCustomer customer = TestDataReader.getExistingCustomers().get(0);

        // Login as customer
        loginPage.goToCustomerLogin();
        assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");
        customerLoginPage.loginAs(customer.getName());
        assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

        // Perform some transactions to generate history
        customerDashboardPage.openDeposit();
        depositPage.deposit("1000");

        customerDashboardPage.openDeposit();
        depositPage.deposit("500");

        // Navigate to Transactions page
        customerDashboardPage.openTransactions();
        assertTrue(transactionsPage.isLoaded(), "Transactions page should be loaded");

        // SECURITY CHECK: Verify Reset button is NOT visible to customers
        boolean resetButtonVisible = transactionsPage.isResetButtonVisible();

        assertFalse(resetButtonVisible,
                "SECURITY VIOLATION: Reset button should NOT be available to customers. " +
                        "Customers must not be able to reset or alter their transaction history.");
    }
}
