package com.automation.helpers;

import java.time.Duration;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base helper for page objects with common WebDriver utilities.
 */
public class PageHelper {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    /**
     * Constructor that creates a helper with a default 10-second timeout.
     *
     * @param driver active WebDriver instance
     */
    public PageHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Finds a visible element.
     *
     * @param locator element locator
     * @return visible element
     */
    public WebElement find(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Clicks an element when it becomes clickable.
     *
     * @param element page element
     */
    public void click(WebElement element) {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                return;
            } catch (StaleElementReferenceException ex) {
                if (attempt == 1) {
                    throw ex;
                }
            }
        }
    }

    /**
     * Clears and types into a field.
     *
     * @param element page element
     * @param value text to enter
     */
    public void type(WebElement element, String value) {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                WebElement target = wait.until(ExpectedConditions.elementToBeClickable(element));
                try {
                    target.clear();
                } catch (Exception e) {
                    // If clear fails, element might not support clearing (like search fields)
                    // Continue to send keys anyway
                }
                target.sendKeys(value);
                return;
            } catch (StaleElementReferenceException ex) {
                if (attempt == 1) {
                    throw ex;
                }
            }
        }
    }

    /**
     * Types into a field without clearing existing content.
     *
     * @param element page element
     * @param value text to enter
     */
    public void typeWithoutClear(WebElement element, String value) {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                WebElement target = wait.until(ExpectedConditions.elementToBeClickable(element));
                target.sendKeys(value);
                return;
            } catch (StaleElementReferenceException ex) {
                if (attempt == 1) {
                    throw ex;
                }
            }
        }
    }

    /**
     * Gets the visible text of an element.
     *
     * @param element page element
     * @return element text
     */
    public String getText(WebElement element) {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                return find(element).getText();
            } catch (StaleElementReferenceException ex) {
                if (attempt == 1) {
                    throw ex;
                }
            }
        }
        return "";
    }

    /**
     * Checks whether an element is visible within the wait timeout.
     *
     * @param element page element
     * @return true if visible, false otherwise
     */
    public boolean isVisible(WebElement element) {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
                return true;
            } catch (StaleElementReferenceException ex) {
                if (attempt == 1) {
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Waits until a custom expected condition is satisfied.
     *
     * @param condition expected condition to wait for
     */
    public void waitUntil(ExpectedCondition<?> condition) {
        wait.until(condition);
    }

    /**
     * Selects an option by visible text from a dropdown.
     *
     * @param element page element
     * @param visibleText option text to select
     */
    public void selectByVisibleText(WebElement element, String visibleText) {
        WebElement target = find(element);
        new Select(target).selectByVisibleText(visibleText);
    }
}
