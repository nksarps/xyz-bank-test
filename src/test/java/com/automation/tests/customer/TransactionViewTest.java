package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.automation.base.SetUp;
import com.automation.utils.ExistingCustomer;
import com.automation.utils.TestDataReader;

/**
 * Tests for Transaction Viewing functionality based on acceptance criteria.
 * 
 * AC1: Viewing Transactions
 * - Customers should be able to view a list of their recent transactions.
 */
@DisplayName("Transaction View Tests (AC1)")
public class TransactionViewTest extends SetUp {

    /**
     * AC1: Customers should be able to view a list of their recent transactions.
     * 
     * Test 1: Verify customers can navigate to and view the Transactions page.
     */
    @Test
    @DisplayName("Customer can access Transactions page")
    void testCustomerCanAccessTransactionsPage() {
        // Get an existing customer to test with
        ExistingCustomer customer = TestDataReader.getExistingCustomers().get(0);

        // Login as customer
        loginPage.goToCustomerLogin();
        assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");
        customerLoginPage.loginAs(customer.getName());
        assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

        // Navigate to Transactions page
        customerDashboardPage.openTransactions();
        
        // Verify Transactions page loads successfully
        assertTrue(transactionsPage.isLoaded(), 
            "Transactions page should be loaded and accessible to customers");
        
        // Verify the page displays the transaction table (even if empty)
        assertNotNull(transactionsPage.getTransactionRows(), 
            "Transaction table should be present on the page");
    }

}
