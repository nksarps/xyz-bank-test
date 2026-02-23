package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.automation.base.SetUp;
import com.automation.utils.ExistingCustomer;
import com.automation.utils.TestDataReader;
import com.automation.utils.WithdrawData;

/**
 * Parameterized tests for Withdrawal functionality.
 */
@DisplayName("Withdrawal Tests")
public class WithdrawalTest extends SetUp {

	/**
	 * Positive test cases for valid withdrawals.
	 */
	@Nested
	@DisplayName("Valid Withdrawal Scenarios")
	class ValidWithdrawalTests {

		/**
		 * Tests making valid withdrawals with sufficient balance.
		 *
		 * @param withdrawData valid withdrawal test data
		 */
		@ParameterizedTest(name = "Withdraw valid amount: {0}")
		@MethodSource("com.automation.tests.customer.WithdrawalTest#provideValidWithdrawals")
		@DisplayName("Should successfully withdraw with valid amount")
		void testValidWithdrawal(WithdrawData withdrawData) {
			ExistingCustomer customer = TestDataReader.getExistingCustomers().get(0);

			// Log in as a known customer to reach the dashboard.
			loginPage.goToCustomerLogin();
			assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");

			customerLoginPage.loginAs(customer.getName());
			assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

			String initialBalanceStr = customerDashboardPage.getBalance();
			int initialBalance = Integer.parseInt(initialBalanceStr);

			// Seed the account with a known deposit before withdrawing.
			customerDashboardPage.openDeposit();
			assertTrue(depositPage.isLoaded(), "Deposit page should be loaded");

			depositPage.deposit(withdrawData.getDepositAmount());

			String depositMessage = depositPage.getSuccessMessage();
			assertNotNull(depositMessage, "Deposit success message should appear");
			assertTrue(depositMessage.contains("Deposit Successful"),
				String.format("Expected deposit success message but got: '%s'", depositMessage));

			
			// Withdraw the specified amount and verify balance updates correctly.
			customerDashboardPage.openWithdrawal();
			assertTrue(withdrawalPage.isLoaded(), "Withdrawal page should be loaded");

			withdrawalPage.withdraw(withdrawData.getWithdrawAmount());

			// Verify final balance 
			String resultMessage = withdrawalPage.getResultMessage();
			if (!resultMessage.isEmpty()) {
				assertFalse(resultMessage.contains("Transaction Failed"),
					String.format("Withdrawal should not fail. Message: '%s'", resultMessage));
			}

			String newBalanceStr = customerDashboardPage.getBalance();
			int newBalance = Integer.parseInt(newBalanceStr);
			int expectedBalance = initialBalance + Integer.parseInt(withdrawData.getDepositAmount())
				- Integer.parseInt(withdrawData.getWithdrawAmount());

			assertEquals(expectedBalance, newBalance,
				String.format("Balance should be updated. Initial: %d, Deposited: %s, Withdrawn: %s, Expected: %d, Actual: %d",
					initialBalance, withdrawData.getDepositAmount(), withdrawData.getWithdrawAmount(), expectedBalance, newBalance));
		}
	}

	/**
	 * Negative test cases for insufficient balance withdrawals.
	 */
	@Nested
	@DisplayName("Insufficient Balance Withdrawal Scenarios")
	class InsufficientBalanceWithdrawalTests {

		/**
		 * Tests that withdrawals greater than current balance are rejected.
		 * The account is reset to zero balance before each data set is executed.
		 *
		 * @param withdrawData insufficient balance withdrawal test data
		 */
		@ParameterizedTest(name = "Should reject insufficient-balance withdrawal: {0}")
		@MethodSource("com.automation.tests.customer.WithdrawalTest#provideInsufficientBalanceWithdrawals")
		@DisplayName("Should reject amount greater than balance")
		void testInsufficientBalanceWithdrawal(WithdrawData withdrawData) {
			ExistingCustomer customer = TestDataReader.getExistingCustomers().get(0);

			loginPage.goToCustomerLogin();
			assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");

			customerLoginPage.loginAs(customer.getName());
			assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

			emptyAccountBalance();

			customerDashboardPage.openDeposit();
			assertTrue(depositPage.isLoaded(), "Deposit page should be loaded");

			depositPage.deposit(withdrawData.getDepositAmount());

			String seededBalanceStr = customerDashboardPage.getBalance();
			int seededBalance = Integer.parseInt(seededBalanceStr);

			customerDashboardPage.openWithdrawal();
			assertTrue(withdrawalPage.isLoaded(), "Withdrawal page should be loaded");

			withdrawalPage.withdraw(withdrawData.getWithdrawAmount());

			String resultMessage = withdrawalPage.getResultMessage();
			assertTrue(resultMessage.contains("Transaction Failed. You can not withdraw amount more than the balance."),
				String.format("Expected insufficient-balance error message. Withdraw: %s, Actual message: '%s'",
					withdrawData.getWithdrawAmount(), resultMessage));

			String newBalanceStr = customerDashboardPage.getBalance();
			int newBalance = Integer.parseInt(newBalanceStr);

			assertEquals(seededBalance, newBalance,
				String.format("Insufficient-balance withdrawal should not change balance. Deposit: %s, Withdraw: %s, Expected: %d, Actual: %d",
					withdrawData.getDepositAmount(), withdrawData.getWithdrawAmount(), seededBalance, newBalance));
		}

		private void emptyAccountBalance() {
			String currentBalanceStr = customerDashboardPage.getBalance();
			int currentBalance = Integer.parseInt(currentBalanceStr);

			if (currentBalance <= 0) {
				return;
			}

			customerDashboardPage.openWithdrawal();
			assertTrue(withdrawalPage.isLoaded(), "Withdrawal page should be loaded");

			withdrawalPage.withdraw(String.valueOf(currentBalance));

			String balanceAfterResetStr = customerDashboardPage.getBalance();
			int balanceAfterReset = Integer.parseInt(balanceAfterResetStr);
			assertEquals(0, balanceAfterReset,
				String.format("Account should be emptied before insufficient-balance test. Before: %d, After: %d",
					currentBalance, balanceAfterReset));
		}
	}

	/**
	 * Negative test cases for invalid withdrawals.
	 */
	@Nested
	@DisplayName("Invalid Withdrawal Scenarios")
	class InvalidWithdrawalTests {

		/**
		 * Tests that invalid withdrawals should be rejected by the system.
		 * Invalid data includes: insufficient balance, zero, negative amounts, non-numeric values.
		 *
		 * @param withdrawData invalid withdrawal test data
		 */
		@ParameterizedTest(name = "Should reject invalid withdrawal: {0}")
		@MethodSource("com.automation.tests.customer.WithdrawalTest#provideInvalidWithdrawals")
		@DisplayName("Should reject invalid amounts")
		void testInvalidWithdrawal(WithdrawData withdrawData) {
			ExistingCustomer customer = TestDataReader.getExistingCustomers().get(0);

			// Log in as a known customer to reach the dashboard.
			loginPage.goToCustomerLogin();
			assertTrue(customerLoginPage.isLoaded(), "Customer Login page should be loaded");

			customerLoginPage.loginAs(customer.getName());
			assertTrue(customerDashboardPage.isLoaded(), "Customer Dashboard should be loaded");

			String initialBalanceStr = customerDashboardPage.getBalance();
			int initialBalance = Integer.parseInt(initialBalanceStr);

			// Seed the account so invalid withdrawals are deterministic.
			customerDashboardPage.openDeposit();
			assertTrue(depositPage.isLoaded(), "Deposit page should be loaded");

			depositPage.deposit(withdrawData.getDepositAmount());

			customerDashboardPage.openWithdrawal();
			assertTrue(withdrawalPage.isLoaded(), "Withdrawal page should be loaded");

			withdrawalPage.withdraw(withdrawData.getWithdrawAmount());

			String newBalanceStr = customerDashboardPage.getBalance();
			int newBalance = Integer.parseInt(newBalanceStr);
			int expectedBalance = initialBalance + Integer.parseInt(withdrawData.getDepositAmount());

			assertEquals(expectedBalance, newBalance,
				String.format("Invalid withdrawal should not change balance. Input: '%s', Expected: %d, Actual: %d",
					withdrawData.getWithdrawAmount(), expectedBalance, newBalance));
		}
	}

	/**
	 * Provides valid withdrawal data for parameterized tests.
	 *
	 * @return stream of valid withdrawal data
	 */
	static Stream<WithdrawData> provideValidWithdrawals() {
		return TestDataReader.getValidWithdrawals().stream();
	}

	/**
	 * Provides invalid withdrawal data for parameterized tests.
	 *
	 * @return stream of invalid withdrawal data
	 */
	static Stream<WithdrawData> provideInvalidWithdrawals() {
		return TestDataReader.getInvalidWithdrawals().stream();
	}

	/**
	 * Provides insufficient balance withdrawal data for parameterized tests.
	 *
	 * @return stream of insufficient balance withdrawal data
	 */
	static Stream<WithdrawData> provideInsufficientBalanceWithdrawals() {
		return TestDataReader.getInsufficientBalanceWithdrawals().stream();
	}
}
