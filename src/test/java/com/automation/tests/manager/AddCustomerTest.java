package com.automation.tests.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.automation.base.SetUp;
import com.automation.utils.CustomerData;
import com.automation.utils.TestDataReader;

/**
 * Parameterized tests for Add Customer functionality.
 */
@DisplayName("Add Customer Tests")
public class AddCustomerTest extends SetUp {

    /**
     * Positive test cases for adding valid customers.
     */
    @Nested
    @DisplayName("Valid Add Customer Scenarios")
    class ValidCustomerTests {

        /**
         * Tests adding valid customers with proper alphabetic names and numeric postal
         * codes.
         *
         * @param customerData valid customer test data
         */
        @ParameterizedTest(name = "Add valid customer: {0}")
        @MethodSource("com.automation.tests.manager.AddCustomerTest#provideValidCustomers")
        @DisplayName("Verify bank manager can add valid customers successfully")
        void testAddValidCustomer(CustomerData customerData) {
            // Navigate to Bank Manager Login
            loginPage.goToBankManagerLogin();
            assertTrue(bankManagerLoginPage.isLoaded(), "Bank Manager page should be loaded");

            // Open Add Customer form
            bankManagerLoginPage.openAddCustomer();
            assertTrue(addCustomerPage.isLoaded(), "Add Customer page should be loaded");

            // Add customer and getting the alert message because the addCustomer method returns
            // the message in the alert.
            String alertMessage = addCustomerPage.addCustomer(
                    customerData.getFirstName(),
                    customerData.getLastName(),
                    customerData.getPostCode());

            // Checking to ensure alert message is returned and contains expected success text
            assertNotNull(alertMessage, "Alert message should not be empty");
            assertTrue(alertMessage.contains("Customer added successfully"),
                    String.format("Expected success message but got: '%s'", alertMessage));
        }
    }

    /**
     * Negative test cases for adding invalid customers.
     */
    @Nested
    @DisplayName("Invalid Add Customer Scenarios")
    class InvalidCustomerTests {

        /**
         * Tests that invalid customers are rejected by the system.
         *
         * @param customerData invalid customer test data
         */
        @ParameterizedTest(name = "Should reject invalid customer: {0}")
        @MethodSource("com.automation.tests.manager.AddCustomerTest#provideInvalidCustomers")
        @DisplayName("Verify system rejects invalid customer data")
        void testAddInvalidCustomer(CustomerData customerData) {
            // Navigate to Bank Manager Login
            loginPage.goToBankManagerLogin();
            assertTrue(bankManagerLoginPage.isLoaded(), "Bank Manager page should be loaded");

            // Open Add Customer form
            bankManagerLoginPage.openAddCustomer();
            assertTrue(addCustomerPage.isLoaded(), "Add Customer page should be loaded");

            // Attempt to add invalid customer
            String alertMessage = addCustomerPage.addCustomer(
                    customerData.getFirstName(),
                    customerData.getLastName(),
                    customerData.getPostCode());

            // Verify that invalid data is rejected
            assertNotNull(alertMessage, "An alert should appear explaining why the customer addition failed.");
            assertFalse(alertMessage.contains("Customer added successfully"),
                    String.format(
                            "System should NOT accept invalid customer data. First Name: '%s', Last Name: '%s', Post Code: '%s', Alert: '%s'",
                            customerData.getFirstName(), customerData.getLastName(), customerData.getPostCode(),
                            alertMessage));
        }
    }

    /**
     * Provides valid customer data for parameterized tests.
     *
     * @return stream of valid customer data
     */
    static Stream<CustomerData> provideValidCustomers() {
        return TestDataReader.getValidCustomers().stream();
    }

    /**
     * Provides invalid customer data for parameterized tests.
     *
     * @return stream of invalid customer data
     */
    static Stream<CustomerData> provideInvalidCustomers() {
        return TestDataReader.getInvalidCustomers().stream();
    }
}
