package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.automation.base.SetUp;
import com.automation.utils.ExistingCustomer;
import com.automation.utils.TestDataReader;

import io.qameta.allure.*;

/**
 * Tests to see that customers can view a list of their transactions.
 */
@Epic("Customer Banking Operations")
@Feature("Transaction Management")
@Story("US-2: Customer can view transactions")
@DisplayName("Transaction View Tests")
public class TransactionViewTest extends SetUp {

    /**
     * Customers should be able to view a list of their recent transactions.
     */
    @Test
    @DisplayName("Verify customers can view a list of their transactions")
    @Description("Customers should be able to view a list of their recent transactions")
    @Severity(SeverityLevel.NORMAL)
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
