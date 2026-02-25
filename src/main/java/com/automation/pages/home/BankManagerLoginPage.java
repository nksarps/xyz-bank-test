package com.automation.pages.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.helpers.PageHelper;

/**
 * Page object for the bank manager dashboard.
 */
public class BankManagerLoginPage {
    private final PageHelper helper;
    public final WebDriver driver;

    // Using the xpath locator to find manager action buttons because they do not have unique IDs 
    // and all buttons have the same class name "btn btn-lg tab".
    // So I am locating the buttons using the texts visible on them.
    @FindBy(xpath = "//button[contains(.,'Add Customer')]")
    private WebElement addCustomerButton;

    @FindBy(xpath = "//button[contains(.,'Open Account')]")
    private WebElement openAccountButton;

    @FindBy(xpath = "//button[contains(.,'Customers')]")
    private WebElement customersButton;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public BankManagerLoginPage(WebDriver driver) {
        this.driver = driver;
        this.helper = new PageHelper(driver);

        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the manager dashboard is displayed.
     *
     * @return true when the manager actions are visible
     */
    public boolean isLoaded() {
        return helper.isVisible(addCustomerButton);
    }

    /**
     * Opens the Add Customer view.
     */
    public void openAddCustomer() {
        helper.click(addCustomerButton);
    }

    /**
     * Opens the Open Account view.
     */
    public void openOpenAccount() {
        helper.click(openAccountButton);
    }

    /**
     * Opens the Customers view.
     */
    public void openCustomers() {
        helper.click(customersButton);
    }
}
