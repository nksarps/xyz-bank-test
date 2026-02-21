package com.automation.tests.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.automation.base.SetUp;
import com.automation.utils.AccountData;
import com.automation.utils.AlertMessageParser;
import com.automation.utils.TestDataReader;

/**
 * Parameterized tests for Open Account functionality.
 */
@DisplayName("Open Account Tests")
public class OpenAccountTest extends SetUp {

    /**
     * Positive test cases for opening valid accounts.
     */
    @Nested
    @DisplayName("Valid Account Scenarios")
    class ValidAccountTests {

        /**
         * Tests opening valid accounts for existing customers with proper currency selection.
         *
         * @param accountData valid account test data
         */
        @ParameterizedTest(name = "Open valid account: {0}")
        @MethodSource("com.automation.tests.manager.OpenAccountTest#provideValidAccounts")
        @DisplayName("Should successfully open account with valid data")
        void testOpenValidAccount(AccountData accountData) {
            // Navigate to Bank Manager Login
            loginPage.goToBankManagerLogin();
            assertTrue(bankManagerLoginPage.isLoaded(), "Bank Manager page should be loaded");

            // Open Open Account form
            bankManagerLoginPage.openOpenAccount();
            assertTrue(openAccountPage.isLoaded(), "Open Account page should be loaded");

            // Open account
            String alertMessage = openAccountPage.openAccount(
                accountData.getCustomerName(),
                accountData.getCurrency()
            );

            // Checking to ensure alert message is returned and contains expected success text
            assertNotNull(alertMessage, "Alert message should not be null");
            assertTrue(alertMessage.contains("Account created successfully"), 
                String.format("Expected success message but got: '%s'", alertMessage));

            // Extract account number from alert message (e.g., "Account created successfully with account Number :1025")
            String accountNumber = AlertMessageParser.extractAccountNumber(alertMessage);
            assertNotNull(accountNumber, "Account number should be extracted from alert message");
            assertFalse(accountNumber.isEmpty(), "Account number should not be empty");

            // Navigate to Customers tab to verify account exists
            bankManagerLoginPage.openCustomers();
            assertTrue(customersPage.isLoaded(), "Customers page should be loaded");

            // Search for the account number
            customersPage.searchCustomer(accountNumber);

            // Verify that the account number appears in the search results
            int customerCount = customersPage.getCustomerCount();
            assertTrue(customerCount > 0, 
                String.format("Account number '%s' should be found in the Customers list", accountNumber));
        }
    }

    /**
     * Negative test cases for opening invalid accounts.
     * These tests verify that the system should reject invalid input.
     */
    @Nested
    @DisplayName("Invalid Account Scenarios")
    class InvalidAccountTests {

        /**
         * Tests that invalid account creation attempts should be rejected by the system.
         * Invalid data includes: empty customer selection, non-existent customers.
         *
         * @param accountData invalid account test data
         */
        @ParameterizedTest(name = "Should reject invalid account: {0}")
        @MethodSource("com.automation.tests.manager.OpenAccountTest#provideInvalidAccounts")
        @DisplayName("Should reject invalid data")
        void testOpenInvalidAccount(AccountData accountData) {
            // Skip tests with empty fields - HTML5 validation prevents submission
            if (accountData.getCustomerName().isEmpty() || accountData.getCurrency().isEmpty()) {
                return;
            }

            // Navigate to Bank Manager Login
            loginPage.goToBankManagerLogin();
            assertTrue(bankManagerLoginPage.isLoaded(), "Bank Manager page should be loaded");

            // Open Open Account form
            bankManagerLoginPage.openOpenAccount();
            assertTrue(openAccountPage.isLoaded(), "Open Account page should be loaded");

            // Attempt to open invalid account
            String alertMessage = openAccountPage.openAccount(
                accountData.getCustomerName(),
                accountData.getCurrency()
            );

            // Verify that invalid data is rejected
            assertNotNull(alertMessage, "Alert should appear");
            assertFalse(alertMessage.contains("Account created successfully"), 
                String.format("System should NOT accept invalid account data. Customer: '%s', Currency: '%s', Alert: '%s'",
                    accountData.getCustomerName(), accountData.getCurrency(), alertMessage));
        }
    }

    /**
     * Provides valid account data for parameterized tests.
     *
     * @return stream of valid account data
     */
    static Stream<AccountData> provideValidAccounts() {
        return TestDataReader.getValidAccounts().stream();
    }

    /**
     * Provides invalid account data for parameterized tests.
     *
     * @return stream of invalid account data
     */
    static Stream<AccountData> provideInvalidAccounts() {
        return TestDataReader.getInvalidAccounts().stream();
    }
}