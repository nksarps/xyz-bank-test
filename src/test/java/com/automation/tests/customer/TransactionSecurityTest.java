package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.automation.base.SetUp;
import com.automation.utils.ExistingCustomer;
import com.automation.utils.TestDataReader;

/**
 * Tests for Transaction Security functionality based on acceptance criteria.
 * 
 * AC4: Transaction Security
 * - Customers should not be able to reset or alter their transaction history.
 * 
 * SECURITY REQUIREMENT: The presence of a Reset button on the Transactions page
 * violates this acceptance criterion as it allows customers to clear their transaction history.
 */
@DisplayName("Transaction Security Tests (AC4)")
public class TransactionSecurityTest extends SetUp {

    /**
     * AC4: Customers should not be able to reset their transaction history.
     * 
     * CRITICAL SECURITY TEST: This test verifies that customers cannot reset/clear
     * their transaction history. The Reset button should NOT be accessible to customers.
     * 
     * Expected Result: FAIL (as Reset button currently exists)
     * Security Issue: Customers can currently reset their transaction history, which
     * violates audit trail requirements and financial regulations.
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
