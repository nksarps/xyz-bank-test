package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.helpers.PageHelper;

/**
 * Page object for the XYZ Bank landing login page.
 */
public class LoginPage {
    private final PageHelper helper;
    public final WebDriver driver;

    @FindBy(className = "mainHeading")
    private WebElement pageHeading;

    // Using the xpath locator to find customer and bank manager login buttons because 
    // they do not have unique IDs and both buttons have the same class name "btn btn-primary btn-lg"
    @FindBy(xpath = "//button[contains(.,'Customer Login')]")
    private WebElement customerLoginButton;

    @FindBy(xpath = "//button[contains(.,'Bank Manager Login')]")
    private WebElement bankManagerLoginButton;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.helper = new PageHelper(driver);
        
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the login page is displayed.
     *
     * @return true when the heading is visible
     */
    public boolean isLoaded() {
        return helper.isVisible(pageHeading);
    }

    /**
     * Navigates to the customer login screen.
     */
    public void goToCustomerLogin() {
        helper.click(customerLoginButton);
    }

    /**
     * Navigates to the bank manager login screen.
     */
    public void goToBankManagerLogin() {
        helper.click(bankManagerLoginButton);
    }
}
