package com.automation.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.helpers.PageHelper;

/**
 * Page object for the Open Account form.
 */
public class OpenAccountPage {
    private final WebDriver driver;
    private final PageHelper helper;

    @FindBy(id = "userSelect")
    private WebElement customerDropdown;

    @FindBy(id = "currency")
    private WebElement currencyDropdown;

    // Using the css selector to find the process button because it does not have a unique ID 
    // and it only has a "type" attribute of "submit" which is not shared by any other buttons on the page
    @FindBy(css = "button[type='submit']")
    private WebElement processButton;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
        this.helper = new PageHelper(driver);
        
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the open account form is displayed.
     *
     * @return true when the customer dropdown is visible
     */
    public boolean isLoaded() {
        return helper.isVisible(customerDropdown);
    }

    /**
     * Opens a new account for the specified customer and currency.
     *
     * @param customerName name of the customer
     * @param currency currency type (e.g., Dollar, Pound, Rupee)
     * @return alert message text containing account number
     */
    public String openAccount(String customerName, String currency) {
        helper.selectByVisibleText(customerDropdown, customerName);
        helper.selectByVisibleText(currencyDropdown, currency);
        helper.click(processButton);

        Alert alert = driver.switchTo().alert();
        String message = alert.getText();
        alert.accept();
        return message;
    }
}
