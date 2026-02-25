package com.automation.tests.manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.automation.base.SetUp;
import com.automation.utils.AlertMessageParser;
import com.automation.utils.DeleteAccountData;
import com.automation.utils.TestDataReader;

import io.qameta.allure.*;

/**
 * Tests for Delete Account functionality.
 */
@Epic("Bank Account Management")
@Feature("Account Management")
@Story("US-1: Bank Manager can delete accounts")
@DisplayName("Delete Account Tests")
public class DeleteAccountTest extends SetUp {

    /**
     * Tests that deleting a customer record removes their account but keeps the
     * customer in system.
     */
    @Test
    @DisplayName("Verify deleting a customer record removes their account but keeps the customer in system")
    @Description("When an account is deleted, the associated customer should no longer be able to access the account")
    @Severity(SeverityLevel.CRITICAL)
    void testDeleteAccountKeepsCustomerInSystem() {
        // Step 1: Navigate to Bank Manager Login and add a customer
        loginPage.goToBankManagerLogin();
        assertTrue(bankManagerLoginPage.isLoaded(), "Bank Manager page should be loaded");

        // Open Add Customer form
        bankManagerLoginPage.openAddCustomer();
        assertTrue(addCustomerPage.isLoaded(), "Add Customer page should be loaded");

        // Load test data from JSON
        DeleteAccountData testData = TestDataReader.getDeleteAccountData();

        // Add a new customer with unique name (append timestamp for uniqueness)
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        String firstName = testData.getFirstName();
        String lastName = testData.getLastName() + uniqueSuffix;
        String postCode = testData.getPostCode();

        String addCustomerAlert = addCustomerPage.addCustomer(firstName, lastName, postCode);

        // Verify customer was added successfully
        assertNotNull(addCustomerAlert, "Alert message should not be null");
        assertTrue(addCustomerAlert.contains("Customer added successfully"),
                String.format("Customer should be added successfully. Alert: '%s'", addCustomerAlert));

        // Extract customer ID
        String customerId = AlertMessageParser.extractCustomerId(addCustomerAlert);
        assertNotNull(customerId, "Customer ID should be extracted from alert");

        // Step 2: Open an account for this customer
        bankManagerLoginPage.openOpenAccount();
        assertTrue(openAccountPage.isLoaded(), "Open Account page should be loaded");

        String customerFullName = firstName + " " + lastName;
        String openAccountAlert = openAccountPage.openAccount(customerFullName, testData.getCurrency());

        // Verify account was created successfully
        assertNotNull(openAccountAlert, "Account creation alert should not be null");
        assertTrue(openAccountAlert.contains("Account created successfully"),
                String.format("Account should be created successfully. Alert: '%s'", openAccountAlert));

        // Extract account number
        String accountNumber = AlertMessageParser.extractAccountNumber(openAccountAlert);
        assertNotNull(accountNumber, "Account number should be extracted");

        // Step 3: Navigate to Customers page
        bankManagerLoginPage.openCustomers();
        assertTrue(customersPage.isLoaded(), "Customers page should be loaded");

        // Step 4: Search for the account number
        customersPage.searchCustomer(accountNumber);

        // Verify customer appears in search results
        int customerCountBeforeDelete = customersPage.getCustomerCount();
        assertTrue(customerCountBeforeDelete > 0,
                String.format("Customer with account number '%s' should be found", accountNumber));

        // Step 5: Delete the customer record (this removes their account)
        customersPage.deleteCustomerByIndex(0);

        // Verify customer is removed from the list
        customersPage.searchCustomer(accountNumber);
        int customerCountAfterDelete = customersPage.getCustomerCount();
        assertEquals(0, customerCountAfterDelete,
                "Customer should be removed from Customers list after deletion");

        // Step 6: Click Home button to return to main page
        customersPage.goToHome();
        assertTrue(loginPage.isLoaded(), "Login page should be loaded after clicking Home");

        // Step 7: Navigate to Customer Login
        loginPage.goToCustomerLogin();
        assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");

        // Step 8: Verify customer still exists in login dropdown
        boolean customerExistsInDropdown = customerLoginPage.isCustomerInDropdown(customerFullName);
        assertTrue(customerExistsInDropdown,
                String.format("Customer '%s' should still exist in login dropdown after account deletion",
                        customerFullName));

        // Step 9: Login as customer and verify "no account" message
        customerLoginPage.loginAs(customerFullName);
        assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

        // Verify "no account" message is displayed
        assertTrue(customerDashboardPage.isNoAccountMessageVisible(),
                "No account message should be visible after account deletion");

        String noAccountMessage = customerDashboardPage.getNoAccountMessage();
        assertTrue(noAccountMessage.contains("Please open an account with us."),
                String.format("Expected message to contain 'Please open an account with us.' but got: '%s'",
                        noAccountMessage));
    }
}
