package com.automation.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Manages WebDriver lifecycle for test execution.
 */
public class SetUp {
    protected static WebDriver driver;

    /**
     * Initializes the WebDriver and navigates to the application URL.
     */
    public static void setUp() {
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
    }

    /**
     * Placeholder for per-test navigation setup.
     */
    public void beforeEachTest() {
        // This method will open the application URL before each test case
    }

    /**
     * Quits the WebDriver instance if it is active.
     */
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
