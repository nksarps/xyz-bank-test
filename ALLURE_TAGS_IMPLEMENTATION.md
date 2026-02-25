# Allure Tags Implementation Guide

## Summary of Changes

All test classes have been updated with Allure annotations to provide better test reporting and organization based on your user stories.

## Allure Annotations Added

### Class-Level Annotations

1. **@Epic** - High-level feature grouping
   - "Bank Account Management" - For manager-related tests
   - "Customer Banking Operations" - For customer transaction tests

2. **@Feature** - Specific functionality within an epic
   - "Customer Management" - Add/manage customers
   - "Account Management" - Create/delete accounts
   - "Transaction Management" - Deposits, withdrawals, viewing transactions
   - "Transaction Security" - Security-related tests
   - "Customer Access Control" - Access control tests

3. **@Story** - User story reference
   - "US-1: Bank Manager can add customers"
   - "US-1: Bank Manager can create accounts"
   - "US-1: Bank Manager can delete accounts"
   - "US-2: Customer can deposit funds"
   - "US-2: Customer can withdraw money"
   - "US-2: Customer can view transactions"
   - "US-2: Customers cannot reset or alter transaction history"

4. **@Owner** - Test owner (set to "QA Team")

### Method-Level Annotations

1. **@Description** - Detailed test description
2. **@Severity** - Test importance level
   - CRITICAL - Core functionality tests
   - NORMAL - Standard validation tests
3. **@Tag** - Custom tags for filtering
   - "positive" - Happy path tests
   - "negative" - Error/validation tests
   - "smoke" - Critical smoke tests
   - "validation" - Input validation tests
   - "security" - Security-related tests

## Test Organization in Allure Reports

### Epic: Bank Account Management
- **Feature: Customer Management**
  - Story: US-1 - Add customers (valid/invalid scenarios)
  
- **Feature: Account Management**
  - Story: US-1 - Create accounts
  - Story: US-1 - Delete accounts
  
- **Feature: Customer Access Control**
  - Story: US-1 - Access control without account

### Epic: Customer Banking Operations
- **Feature: Transaction Management**
  - Story: US-2 - Deposit funds (valid/invalid)
  - Story: US-2 - Withdraw money (valid/invalid/insufficient balance)
  - Story: US-2 - View transactions
  
- **Feature: Transaction Security**
  - Story: US-2 - Cannot reset/alter transaction history

## Files Modified

### Manager Tests
1. `src/test/java/com/automation/tests/manager/AddCustomerTest.java`
2. `src/test/java/com/automation/tests/manager/OpenAccountTest.java`
3. `src/test/java/com/automation/tests/manager/DeleteAccountTest.java`

### Customer Tests
1. `src/test/java/com/automation/tests/customer/DepositTest.java`
2. `src/test/java/com/automation/tests/customer/WithdrawalTest.java`
3. `src/test/java/com/automation/tests/customer/TransactionViewTest.java`
4. `src/test/java/com/automation/tests/customer/TransactionSecurityTest.java`
5. `src/test/java/com/automation/tests/customer/CustomerAccessTest.java`

## Running Tests with Allure

### Generate Allure Results
```bash
mvn clean test
```

### Generate and Open Allure Report
```bash
mvn allure:serve
```

Or using the Allure CLI:
```bash
.allure/allure-2.36.0/bin/allure serve target/allure-results
```

## Filtering Tests by Tags

You can filter tests using Maven Surefire with JUnit tags:

### Run only smoke tests:
```bash
mvn test -Dgroups="smoke"
```

### Run only positive tests:
```bash
mvn test -Dgroups="positive"
```

### Run only security tests:
```bash
mvn test -Dgroups="security"
```

### Run multiple tag combinations:
```bash
mvn test -Dgroups="smoke | security"
```

## Benefits of This Implementation

1. **Better Organization** - Tests are grouped by Epic, Feature, and Story
2. **Traceability** - Direct mapping to user stories (US-1, US-2)
3. **Filtering** - Easy to run specific test categories
4. **Reporting** - Rich Allure reports with severity levels and descriptions
5. **Maintenance** - Clear test purpose and ownership

## Allure Report Features

With these annotations, your Allure report will show:
- Test execution trends
- Test distribution by Epic/Feature/Story
- Severity-based test results
- Tag-based filtering
- Detailed test descriptions
- Test ownership information
