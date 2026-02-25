# Automated Tests for XYZ Bank
This is an automated UI test suite for the XYZ Bank Angular Banking demo application.

It validates key customer and bank manager flows using Selenium WebDriver, JUnit 5 tests, JSON-driven test data, and Allure reporting.

Purpose and scope:
- Verify core banking workflows end-to-end in a browser.
- Cover both positive and negative test paths.
- Provide reproducible CI execution with test artifacts and notifications.

## Prerequisites

Required software:
- Java Development Kit (JDK)
- Apache Maven
- Google Chrome

Required versions:
- Java: 11 (project compiler target)
- Maven: 3.8+ (recommended)
- Chrome: current stable version compatible with Selenium

Dependencies/tools needed before setup:
- Internet access to resolve Maven dependencies.
- Optional Allure CLI for viewing reports locally.
- Git for cloning the repository.

## Quick Start

### Clone the Repository

```bash
git clone https://github.com/nksarps/xyz-bank-test
cd xyz-bank-test
```

### Run Locally

1. Create a local environment file in the project root:

```env
APPLICATION_URL=TheApplicationURL
```

2. Execute tests (headed mode):

```bash
mvn clean test
```

3. Execute tests (headless mode):

```bash
mvn clean test -Dheadless=true
```

4. Generate and open Allure report:

```bash
mvn allure:report
mvn allure:serve
```

## Project Structure

```text
xyz-bank-test/
├── .github/
│   └── workflows/
│       └── ci.yml
├── .mvn/
├── src/
│   ├── main/
│   │   └── java/com/automation/
│   │       ├── helpers/
│   │       │   └── PageHelper.java
│   │       └── pages/
│   │           ├── customer/
│   │           │   ├── CustomerDashboardPage.java
│   │           │   ├── DepositPage.java
│   │           │   ├── LoginPage.java
│   │           │   ├── TransactionsPage.java
│   │           │   └── WithdrawalPage.java
│   │           ├── home/
│   │           │   ├── BankManagerLoginPage.java
│   │           │   └── CustomerLoginPage.java
│   │           └── manager/
│   │               ├── AddCustomerPage.java
│   │               ├── CustomersPage.java
│   │               └── OpenAccountPage.java
│   └── test/
│       ├── java/com/automation/
│       │   ├── base/
│       │   │   └── SetUp.java
│       │   ├── tests/
│       │   │   ├── customer/
│       │   │   │   ├── CustomerAccessTest.java
│       │   │   │   ├── DepositTest.java
│       │   │   │   ├── TransactionSecurityTest.java
│       │   │   │   ├── TransactionViewTest.java
│       │   │   │   └── WithdrawalTest.java
│       │   │   └── manager/
│       │   │       ├── AddCustomerTest.java
│       │   │       ├── DeleteAccountTest.java
│       │   │       └── OpenAccountTest.java
│       │   └── utils/
│       │       ├── AccountData.java
│       │       ├── AlertMessageParser.java
│       │       ├── CustomerAccessData.java
│       │       ├── CustomerData.java
│       │       ├── DeleteAccountData.java
│       │       ├── DepositData.java
│       │       ├── ExistingCustomer.java
│       │       ├── TestDataReader.java
│       │       └── WithdrawData.java
│       └── resources/
│           ├── logging.properties
│           └── testdata/
│               ├── addCustomer.json
│               ├── createAccount.json
│               ├── customerAccess.json
│               ├── customers.json
│               ├── deleteAccount.json
│               ├── deposit.json
│               └── withdraw.json
├── allure-results/
├── target/
├── .env
└── pom.xml
```

Major directories/files:
- `src/main/java/com/automation/pages`: Page Object Model classes for app screens.
- `src/main/java/com/automation/pages/customer`: Page Object Model classes for customer-related screens (e.g., dashboard, transactions).
- `src/main/java/com/automation/pages/home`: Page Object Model classes for home-related screens (e.g., login pages).
- `src/main/java/com/automation/pages/manager`: Page Object Model classes for manager-related screens (e.g., add customer, open account).
- `src/main/java/com/automation/helpers/PageHelper.java`: common Selenium wait/type/click helpers.
- `src/test/java/com/automation/base/SetUp.java`: WebDriver lifecycle and shared page initialization.
- `src/test/java/com/automation/tests`: customer and manager test suites.
- `src/test/resources/testdata`: JSON-driven input data for parameterized tests.
- `.github/workflows/ci.yml`: CI pipeline and notifications.
- `pom.xml`: build, test plugins, and dependencies.

## Features

This project validates:
- Add Customer flow (valid and invalid manager inputs).
- Open Account flow (valid and invalid account creation paths).
- Delete Account behavior and post-deletion customer access expectations.
- Customer access control for users without accounts.
- Deposit functionality with valid and invalid inputs.
- Withdrawal functionality with valid, invalid, and insufficient-balance scenarios.
- Transaction page visibility and transaction-security constraints (no reset access).
- Data-driven testing using JSON fixtures.
- Allure test result generation and report publication in CI.

## CI/CD Pipeline

### Workflow

GitHub Actions workflow: `.github/workflows/ci.yml`

### When It Runs

- On `push` to `main`, `develop`, and `feature/*`
- On `pull_request` targeting `main`, `develop`, and `feature/*`
- Manual trigger via `workflow_dispatch`

### Pipeline Steps

1. Checkout repository.
2. Setup Temurin Java 11 with Maven cache.
3. Verify Chrome availability.
4. Run tests in headless mode:
   - `mvn -B clean test -DtrimStackTrace=false -Dsurefire.printSummary=true -Dheadless=true`
5. Generate Allure report.
6. Upload Allure and Surefire artifacts.
7. Extract summary metrics and failing test names.
8. Send Slack notification.
9. Send email notification.

### Build Status Badge

```md
![Build Status](https://github.com/nksarps/xyz-bank-test/actions/workflows/ci.yml/badge.svg)
```

## Configuration

### Local Setup

- Use `.env` in project root.
- Required key:
  - `APPLICATION_URL` (base URL for the banking app under test)

### Environment Variables

Local/runtime:
- `APPLICATION_URL`
- Java system property: `headless=true|false`

CI environment:
- `APPLICATION_URL` (from GitHub Secrets)
- `MAVEN_OPTS`

### Secrets

Configured in CI workflow:
- `APPLICATION_URL`
- `SLACK_WEBHOOK_URL`
- `SMTP_SERVER`
- `SMTP_PORT`
- `SMTP_USERNAME`
- `SMTP_PASSWORD`
- `NOTIFY_EMAIL_TO`
- `NOTIFY_EMAIL_FROM`

Do not commit real secret values to source control.

### Production Setup (optional)

For production-like execution:
- Run in headless mode on CI agents.
- Use environment-specific `APPLICATION_URL` values.
- Keep credentials and webhook settings in secure secret stores only.

## Contributing

Branch strategy:
- Active branches used by CI: `main`, `develop`, `feature/*`.
- Create feature branches from `develop` where applicable.

Commit guidelines:
- Use clear, descriptive commit messages.
- Recommended style: Conventional Commits (e.g., `feat:`, `fix:`, `test:`).

PR process:
1. Create/update tests and supporting data.
2. Run local checks (`mvn clean test`).
3. Open pull request against target branch.
4. Ensure CI passes and review feedback is resolved.

## Support

Where to get help:
- Open an issue in the repository.
- Contact the project maintainers/reviewers.

Debugging tips:
- Run a focused test class:
  - `mvn -Dtest=DepositTest test`
- Run in headed mode for visual debugging:
  - `mvn test`
- Run in headless mode for CI parity:
  - `mvn test -Dheadless=true`

Logs location:
- Surefire reports: `target/surefire-reports`
- Allure raw results (build output): `target/allure-results`
- Allure report site: `target/site/allure-maven-plugin`
- Existing checked-in local results: `allure-results/`
