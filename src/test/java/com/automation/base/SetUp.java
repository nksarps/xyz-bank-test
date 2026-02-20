package com.automation.base;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.automation.pages.AddCustomerPage;
import com.automation.pages.BankManagerLoginPage;
import com.automation.pages.CustomerDashboardPage;
import com.automation.pages.CustomerLoginPage;
import com.automation.pages.CustomersPage;
import com.automation.pages.DepositPage;
import com.automation.pages.LoginPage;
import com.automation.pages.OpenAccountPage;
import com.automation.pages.TransactionsPage;
import com.automation.pages.WithdrawalPage;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Manages WebDriver lifecycle for test execution.
 */
public class SetUp {
    protected WebDriver driver;

    // Creating instances of the page objects to be used across tests
    protected LoginPage loginPage;
    protected CustomerLoginPage customerLoginPage; 
    protected WithdrawalPage withdrawalPage;
    protected CustomerDashboardPage customerDashboardPage;
    protected CustomersPage customersPage;
    protected TransactionsPage transactionsPage;
    protected AddCustomerPage addCustomerPage;
    protected OpenAccountPage openAccountPage;
    protected BankManagerLoginPage bankManagerLoginPage;
    protected DepositPage depositPage;

    /**
     * Initializes the WebDriver and navigates to the application URL.
     */
    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        Dotenv dotenv = Dotenv.load();
        String applicationUrl = dotenv.get("APPLICATION_URL");

        String headless = System.getProperty("headless");
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        driver = new ChromeDriver(options);
        driver.get(applicationUrl);

        // Initialize all page objects after driver is set up
        loginPage = new LoginPage(driver);
        customerLoginPage = new CustomerLoginPage(driver);
        withdrawalPage = new WithdrawalPage(driver);
        customerDashboardPage = new CustomerDashboardPage(driver);
        customersPage = new CustomersPage(driver);
        transactionsPage = new TransactionsPage(driver);
        addCustomerPage = new AddCustomerPage(driver);
        openAccountPage = new OpenAccountPage(driver);
        bankManagerLoginPage = new BankManagerLoginPage(driver);
        depositPage = new DepositPage(driver);
    }



    /**
     * Quits the WebDriver instance if it is active.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
