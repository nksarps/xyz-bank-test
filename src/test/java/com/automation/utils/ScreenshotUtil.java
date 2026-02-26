package com.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for capturing screenshots from WebDriver instances.
 * Provides thread-safe screenshot capture with error handling for Allure reporting.
 */
public class ScreenshotUtil {

    // Logger instance for logging messages, warnings, and errors specific to this class
    private static final Logger LOGGER = Logger.getLogger(ScreenshotUtil.class.getName());

    // Formatter for generating timestamps in the format yyyyMMdd_HHmmss_SSS
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
    
    /**
     * Captures a screenshot from the provided WebDriver instance.
     * 
     * @param driver the WebDriver instance to capture from
     * @return byte array containing PNG screenshot data, or empty array if capture fails
     */
    public static byte[] captureScreenshot(WebDriver driver) {
        if (driver == null) {
            LOGGER.warning("WebDriver is null, cannot capture screenshot");
            return new byte[0];
        }
        
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            LOGGER.info("Screenshot captured successfully, size: " + screenshot.length + " bytes");
            return screenshot;
        } catch (ClassCastException e) {
            LOGGER.log(Level.SEVERE, "WebDriver does not support screenshot capture: " + e.getMessage(), e);
            return new byte[0];
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to capture screenshot: " + e.getClass().getName() + " - " + e.getMessage(), e);
            return new byte[0];
        }
    }
    
    /**
     * Generates a unique screenshot filename with timestamp.
     * Format: {testName}_{timestamp}.png
     * 
     * @param testName the name of the test
     * @return formatted filename string
     */
    public static String generateScreenshotName(String testName) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        return String.format("%s_%s.png", testName, timestamp);
    }
}
