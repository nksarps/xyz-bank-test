package com.automation.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.automation.pages.manager.AddCustomerPage;
import com.automation.pages.home.BankManagerLoginPage;
import com.automation.pages.customer.CustomerDashboardPage;
import com.automation.pages.home.CustomerLoginPage;
import com.automation.pages.manager.CustomersPage;
import com.automation.pages.customer.DepositPage;
import com.automation.pages.customer.LoginPage;
import com.automation.pages.manager.OpenAccountPage;
import com.automation.pages.customer.TransactionsPage;
import com.automation.pages.customer.WithdrawalPage;
import com.automation.utils.ScreenshotUtil;

import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Manages WebDriver lifecycle for test execution.
 */
@ExtendWith(SetUp.JulTestWatcher.class)
public class SetUp {
    protected WebDriver driver;

    static {
        /**
         * Configures Java Util Logging (JUL) to display only the log message without
         * additional metadata.
         * Also suppresses Selenium DevTools CDP version warnings.
         */
        try (InputStream configFile = SetUp.class.getClassLoader().getResourceAsStream("logging.properties")) {
            if (configFile != null) {
                LogManager.getLogManager().readConfiguration(configFile);
            }
        } catch (Exception e) {
            System.err.println("Could not load logging.properties: " + e.getMessage());
        }

        // Fallback configuration in case properties file is not loaded
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.OFF);
    }

    /**
     * Declaring logger instance for logging test results in SetUp.
     */
    private static final Logger LOGGER = Logger.getLogger(SetUp.class.getName());

    /**
     * Custom JUnit 5 {@link TestWatcher} implementation to log test results and capture screenshots on failure.
     * Implements {@link TestExecutionExceptionHandler} to capture screenshots when exceptions occur during test execution.
     */
    static class JulTestWatcher implements TestWatcher, TestExecutionExceptionHandler {

        /**
         * Intercepts test execution exceptions to capture screenshots before the exception propagates.
         * This runs DURING test execution, BEFORE @AfterEach, ensuring the WebDriver is still active.
         *
         * @param context the extension context
         * @param throwable the exception thrown during test execution
         */
        @Override
        public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
            try {
                Object testInstance = context.getRequiredTestInstance();
                
                if (testInstance instanceof SetUp) {
                    SetUp setUp = (SetUp) testInstance;
                    WebDriver driver = setUp.driver;
                    
                    if (driver != null) {
                        byte[] screenshot = ScreenshotUtil.captureScreenshot(driver);
                        
                        if (screenshot.length > 0) {
                            String screenshotName = ScreenshotUtil.generateScreenshotName(context.getDisplayName());
                            Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshot), ".png");
                            LOGGER.info("Screenshot captured and attached for failed test: " + context.getDisplayName());
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to capture or attach screenshot for test: " + context.getDisplayName(), e);
            }
            
            // Re-throw the original exception to maintain test failure
            throw throwable;
        }

        /**
         * Logs a message when a test is successful.
         *
         * @param context the context of the test that was successful
         */
        @Override
        public void testSuccessful(ExtensionContext context) {
            LOGGER.info("[PASS] " + context.getDisplayName());
        }

        /**
         * Logs a message and the exception when a test fails.
         *
         * @param context the context of the test that failed
         * @param cause   the exception that caused the test to fail
         */
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            LOGGER.info("[FAIL] " + context.getDisplayName() + " - " + cause.getMessage());
        }

        /**
         * Logs a message when a test is skipped.
         *
         * @param context the context of the test that was skipped
         * @param reason  the reason the test was skipped
         */
        @Override
        public void testDisabled(ExtensionContext context, java.util.Optional<String> reason) {
            LOGGER.info("[SKIP] " + context.getDisplayName() + reason.map(r -> " - " + r).orElse(""));
        }
    }

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

        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
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