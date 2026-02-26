package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.automation.base.SetUp;
import com.automation.utils.DepositData;
import com.automation.utils.ExistingCustomer;
import com.automation.utils.TestDataReader;

import io.qameta.allure.*;

/**
 * Parameterized tests for Deposit functionality.
 */
@Epic("Customer Banking Operations")
@Feature("Transaction Management")
@Story("US-2: Customer can deposit funds")
@DisplayName("Deposit Tests")
public class DepositTest extends SetUp {

    /**
     * Positive test cases for valid deposits.
     */
    @Nested
    @DisplayName("Valid Deposit Scenarios")
    @Severity(SeverityLevel.CRITICAL)
    class ValidDepositTests {

        /**
         * Tests making valid deposits with positive amounts.
         *
         * @param depositData valid deposit test data
         */
        @ParameterizedTest(name = "Deposit valid amount: {0}")
        @MethodSource("com.automation.tests.customer.DepositTest#provideValidDeposits")
        @DisplayName("Verify successful deposit updates balance correctly")
        @Description("System should allow customers to deposit positive amounts and update account balance")
        void testValidDeposit(DepositData depositData) {
            // Get an existing customer to test with
            ExistingCustomer customer = TestDataReader.getExistingCustomers().get(0);

            // Navigate to Customer Login
            loginPage.goToCustomerLogin();
            assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");

            // Login as customer
            customerLoginPage.loginAs(customer.getName());
            assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

            // Get initial balance
            String initialBalanceStr = customerDashboardPage.getBalance();
            int initialBalance = Integer.parseInt(initialBalanceStr);

            // Open Deposit page
            customerDashboardPage.openDeposit();
            assertTrue(depositPage.isLoaded(), "Deposit page should be loaded");

            // Make deposit
            depositPage.deposit(depositData.getAmount());

            // Verify success message
            String successMessage = depositPage.getSuccessMessage();
            assertNotNull(successMessage, "Success message should appear");
            assertTrue(successMessage.contains("Deposit Successful"), 
                String.format("Expected success message but got: '%s'", successMessage));

            // Verify balance updated
            String newBalanceStr = customerDashboardPage.getBalance();
            int newBalance = Integer.parseInt(newBalanceStr);
            int expectedBalance = initialBalance + Integer.parseInt(depositData.getAmount());
            
            assertEquals(expectedBalance, newBalance, 
                String.format("Balance should be updated. Initial: %d, Deposited: %s, Expected: %d, Actual: %d",
                    initialBalance, depositData.getAmount(), expectedBalance, newBalance));
        }
    }

    /**
     * Negative test cases for invalid deposits.
     * These tests verify that the system should reject invalid input.
     */
    @Nested
    @DisplayName("Invalid Deposit Scenarios")
    @Severity(SeverityLevel.NORMAL)
    class InvalidDepositTests {

        /**
         * Tests that invalid deposits should be rejected by the system.
         * Invalid data includes: zero, negative amounts, non-numeric values.
         *
         * @param depositData invalid deposit test data
         */
        @ParameterizedTest(name = "Should reject invalid deposit: {0}")
        @MethodSource("com.automation.tests.customer.DepositTest#provideInvalidDeposits")
        @DisplayName("Verify invalid deposit amounts are rejected and balance remains unchanged")
        @Description("System should validate deposit amounts and reject zero, negative, or non-numeric values")
        void testInvalidDeposit(DepositData depositData) {
            // Get an existing customer to test with
            ExistingCustomer customer = TestDataReader.getExistingCustomers().get(0);

            // Navigate to Customer Login
            loginPage.goToCustomerLogin();
            assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");

            // Login as customer
            customerLoginPage.loginAs(customer.getName());
            assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

            // Get initial balance
            String initialBalanceStr = customerDashboardPage.getBalance();
            int initialBalance = Integer.parseInt(initialBalanceStr);

            // Open Deposit page
            customerDashboardPage.openDeposit();
            assertTrue(depositPage.isLoaded(), "Deposit page should be loaded");

            // Attempt invalid deposit
            depositPage.deposit(depositData.getAmount());

            // Get current balance to verify deposit was not accepted
            String newBalanceStr = customerDashboardPage.getBalance();
            int newBalance = Integer.parseInt(newBalanceStr);

            assertEquals(initialBalance, newBalance,
                String.format("Invalid deposit should not change balance. Input: '%s', Expected: %d, Actual: %d",
                    depositData.getAmount(), initialBalance, newBalance));
        }
    }

    /**
     * Provides valid deposit data for parameterized tests.
     *
     * @return list of valid deposit data
     */
    static List<DepositData> provideValidDeposits() {
        return TestDataReader.getValidDeposits();
    }

    /**
     * Provides invalid deposit data for parameterized tests.
     *
     * @return list of invalid deposit data
     */
    static List<DepositData> provideInvalidDeposits() {
        return TestDataReader.getInvalidDeposits();
    }
}
