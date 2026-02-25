package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.automation.base.SetUp;
import com.automation.utils.AlertMessageParser;
import com.automation.utils.CustomerAccessData;
import com.automation.utils.TestDataReader;

/**
 * Tests for Customer Access Control functionality.
 */
@DisplayName("Customer Access Control Tests")
public class CustomerAccessTest extends SetUp {

    /**
     * Tests that a customer without an account sees the "Please open an account" message.
     * 
     * Test Flow:
     * 1. Bank manager adds a new customer
     * 2. No account is opened for the customer
     * 3. Customer attempts to login
     * 4. Customer should see "Please open an account with us." message
     */
    @Test
    @DisplayName("Verify customers without an account sees a message to open an account when they login")
    void testCustomerWithoutAccountSeesMessage() {
        // Step 1: Navigate to Bank Manager Login and add a customer
        loginPage.goToBankManagerLogin();
        assertTrue(bankManagerLoginPage.isLoaded(), "Bank Manager page should be loaded");

        // Open Add Customer form
        bankManagerLoginPage.openAddCustomer();
        assertTrue(addCustomerPage.isLoaded(), "Add Customer page should be loaded");

        // Load test data from JSON
        CustomerAccessData customerData = TestDataReader.getCustomerWithoutAccountData();
        
        // Add a new customer with unique name (append timestamp for uniqueness)
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        String firstName = customerData.getFirstName();
        String lastName = customerData.getLastName() + uniqueSuffix;
        String postCode = customerData.getPostCode();

        String alertMessage = addCustomerPage.addCustomer(firstName, lastName, postCode);
        
        // Verify customer was added successfully
        assertNotNull(alertMessage, "Alert message should not be null");
        assertTrue(alertMessage.contains("Customer added successfully"), 
            String.format("Customer should be added successfully. Alert: '%s'", alertMessage));

        // Extract customer ID (though we won't use it, confirms success)
        String customerId = AlertMessageParser.extractCustomerId(alertMessage);
        assertNotNull(customerId, "Customer ID should be extracted from alert");

        // Step 2: Do NOT open an account for this customer
        // (Intentionally skipped to test access control)

        // Step 3: Click Home button to return to main page
        addCustomerPage.goToHome();
        assertTrue(loginPage.isLoaded(), "Login page should be loaded after clicking Home");

        // Step 4: Navigate to Customer Login
        loginPage.goToCustomerLogin();
        assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");

        // Step 5: Login as the newly created customer
        String customerFullName = firstName + " " + lastName;
        customerLoginPage.loginAs(customerFullName);
        
        // Step 6: Verify dashboard loads
        assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

        // Step 7: Verify "no account" message is displayed
        assertTrue(customerDashboardPage.isNoAccountMessageVisible(), 
            "No account message should be visible for customer without account");

        String noAccountMessage = customerDashboardPage.getNoAccountMessage();
        assertTrue(noAccountMessage.contains("Please open an account with us."),
            String.format("Expected message to contain 'Please open an account with us.' but got: '%s'", noAccountMessage));
    }
}
