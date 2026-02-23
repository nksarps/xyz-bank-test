package com.automation.utils;

import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class to read test data from JSON files.
 */
public class TestDataReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private TestDataReader() {
    }

    /**
     * Reads valid customer data from addCustomer.json.
     *
     * @return list of valid customer data
     */
    public static List<CustomerData> getValidCustomers() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/addCustomer.json")) {
            CustomerTestData data = objectMapper.readValue(inputStream, CustomerTestData.class);
            return data.getValidCustomers();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read valid customers from JSON", e);
        }
    }

    /**
     * Reads invalid customer data from addCustomer.json.
     *
     * @return list of invalid customer data
     */
    public static List<CustomerData> getInvalidCustomers() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/addCustomer.json")) {
            CustomerTestData data = objectMapper.readValue(inputStream, CustomerTestData.class);
            return data.getInvalidCustomers();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read invalid customers from JSON", e);
        }
    }

    /**
     * Reads valid account data from createAccount.json.
     *
     * @return list of valid account data
     */
    public static List<AccountData> getValidAccounts() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/createAccount.json")) {
            AccountTestData data = objectMapper.readValue(inputStream, AccountTestData.class);
            return data.getValidAccounts();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read valid accounts from JSON", e);
        }
    }

    /**
     * Reads invalid account data from createAccount.json.
     *
     * @return list of invalid account data
     */
    public static List<AccountData> getInvalidAccounts() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/createAccount.json")) {
            AccountTestData data = objectMapper.readValue(inputStream, AccountTestData.class);
            return data.getInvalidAccounts();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read invalid accounts from JSON", e);
        }
    }

    /**
     * Reads valid deposit data from deposit.json.
     *
     * @return list of valid deposit data
     */
    public static List<DepositData> getValidDeposits() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/deposit.json")) {
            DepositTestData data = objectMapper.readValue(inputStream, DepositTestData.class);
            return data.getValidDeposits();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read valid deposits from JSON", e);
        }
    }

    /**
     * Reads invalid deposit data from deposit.json.
     *
     * @return list of invalid deposit data
     */
    public static List<DepositData> getInvalidDeposits() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/deposit.json")) {
            DepositTestData data = objectMapper.readValue(inputStream, DepositTestData.class);
            return data.getInvalidDeposits();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read invalid deposits from JSON", e);
        }
    }

    /**
     * Reads valid withdrawal data from withdraw.json.
     *
     * @return list of valid withdrawal data
     */
    public static List<WithdrawData> getValidWithdrawals() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/withdraw.json")) {
            WithdrawTestData data = objectMapper.readValue(inputStream, WithdrawTestData.class);
            return data.getValidWithdrawals();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read valid withdrawals from JSON", e);
        }
    }

    /**
     * Reads invalid withdrawal data from withdraw.json.
     *
     * @return list of invalid withdrawal data
     */
    public static List<WithdrawData> getInvalidWithdrawals() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/withdraw.json")) {
            WithdrawTestData data = objectMapper.readValue(inputStream, WithdrawTestData.class);
            return data.getInvalidWithdrawals();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read invalid withdrawals from JSON", e);
        }
    }

    /**
     * Reads insufficient balance withdrawal data from withdraw.json.
     *
     * @return list of insufficient balance withdrawal data
     */
    public static List<WithdrawData> getInsufficientBalanceWithdrawals() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/withdraw.json")) {
            WithdrawTestData data = objectMapper.readValue(inputStream, WithdrawTestData.class);
            return data.getInsufficientBalanceWithdrawals();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read insufficient balance withdrawals from JSON", e);
        }
    }

    /**
     * Reads existing customer data from customers.json.
     *
     * @return list of existing customers
     */
    public static List<ExistingCustomer> getExistingCustomers() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/customers.json")) {
            ExistingCustomerData data = objectMapper.readValue(inputStream, ExistingCustomerData.class);
            return data.getExistingCustomers();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read existing customers from JSON", e);
        }
    }

    /**
     * Reads customer access test data for customer without account scenario.
     *
     * @return customer data for testing access without account
     */
    public static CustomerAccessData getCustomerWithoutAccountData() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/customerAccess.json")) {
            CustomerAccessTestData data = objectMapper.readValue(inputStream, CustomerAccessTestData.class);
            return data.getCustomerWithoutAccount();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read customer without account data from JSON", e);
        }
    }

    /**
     * Reads customer access test data for customer with account scenario.
     *
     * @return customer data for testing access with account
     */
    public static CustomerAccessData getCustomerWithAccountData() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/customerAccess.json")) {
            CustomerAccessTestData data = objectMapper.readValue(inputStream, CustomerAccessTestData.class);
            return data.getCustomerWithAccount();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read customer with account data from JSON", e);
        }
    }

    /**
     * Reads delete account test data from deleteAccount.json.
     *
     * @return delete account test data
     */
    public static DeleteAccountData getDeleteAccountData() {
        try (InputStream inputStream = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/deleteAccount.json")) {
            DeleteAccountTestData data = objectMapper.readValue(inputStream, DeleteAccountTestData.class);
            return data.getDeleteAccountTest();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read delete account data from JSON", e);
        }
    }

    // Inner classes for JSON deserialization
    private static class CustomerTestData {
        @com.fasterxml.jackson.annotation.JsonProperty("validCustomers")
        private List<CustomerData> validCustomers;

        @com.fasterxml.jackson.annotation.JsonProperty("invalidCustomers")
        private List<CustomerData> invalidCustomers;

        public List<CustomerData> getValidCustomers() {
            return validCustomers;
        }

        public List<CustomerData> getInvalidCustomers() {
            return invalidCustomers;
        }
    }

    private static class AccountTestData {
        @com.fasterxml.jackson.annotation.JsonProperty("validAccounts")
        private List<AccountData> validAccounts;

        @com.fasterxml.jackson.annotation.JsonProperty("invalidAccounts")
        private List<AccountData> invalidAccounts;

        public List<AccountData> getValidAccounts() {
            return validAccounts;
        }

        public List<AccountData> getInvalidAccounts() {
            return invalidAccounts;
        }
    }

    private static class DepositTestData {
        @com.fasterxml.jackson.annotation.JsonProperty("validDeposits")
        private List<DepositData> validDeposits;

        @com.fasterxml.jackson.annotation.JsonProperty("invalidDeposits")
        private List<DepositData> invalidDeposits;

        public List<DepositData> getValidDeposits() {
            return validDeposits;
        }

        public List<DepositData> getInvalidDeposits() {
            return invalidDeposits;
        }
    }

    private static class WithdrawTestData {
        @com.fasterxml.jackson.annotation.JsonProperty("validWithdrawals")
        private List<WithdrawData> validWithdrawals;

        @com.fasterxml.jackson.annotation.JsonProperty("invalidWithdrawals")
        private List<WithdrawData> invalidWithdrawals;

        @com.fasterxml.jackson.annotation.JsonProperty("insufficientBalanceWithdrawals")
        private List<WithdrawData> insufficientBalanceWithdrawals;

        public List<WithdrawData> getValidWithdrawals() {
            return validWithdrawals;
        }

        public List<WithdrawData> getInvalidWithdrawals() {
            return invalidWithdrawals;
        }

        public List<WithdrawData> getInsufficientBalanceWithdrawals() {
            return insufficientBalanceWithdrawals;
        }
    }

    private static class ExistingCustomerData {
        @com.fasterxml.jackson.annotation.JsonProperty("existingCustomers")
        private List<ExistingCustomer> existingCustomers;

        public List<ExistingCustomer> getExistingCustomers() {
            return existingCustomers;
        }
    }

    private static class CustomerAccessTestData {
        @com.fasterxml.jackson.annotation.JsonProperty("customerWithoutAccount")
        private CustomerAccessData customerWithoutAccount;

        @com.fasterxml.jackson.annotation.JsonProperty("customerWithAccount")
        private CustomerAccessData customerWithAccount;

        public CustomerAccessData getCustomerWithoutAccount() {
            return customerWithoutAccount;
        }

        public CustomerAccessData getCustomerWithAccount() {
            return customerWithAccount;
        }
    }

    private static class DeleteAccountTestData {
        @com.fasterxml.jackson.annotation.JsonProperty("deleteAccountTest")
        private DeleteAccountData deleteAccountTest;

        public DeleteAccountData getDeleteAccountTest() {
            return deleteAccountTest;
        }
    }
}
