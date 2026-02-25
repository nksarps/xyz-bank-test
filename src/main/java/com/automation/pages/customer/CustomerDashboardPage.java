package com.automation.pages.customer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.helpers.PageHelper;

/**
 * Page object for the Customer Dashboard page.
 */
public class CustomerDashboardPage {
    private final PageHelper helper;
    public final WebDriver driver;

    // Using xpath locators to find elements because they do not have unique IDs or class names.
    @FindBy(xpath = "//strong[contains(.,'Welcome')]")
    private WebElement welcomeMessage;

    @FindBy(xpath = "//div[@ng-hide='noAccount']//strong[2]")
    private WebElement accountBalance;

    // Using xpath locators to find action buttons because they do not have unique IDs 
    // and all buttons have the same class name "btn btn-lg tab"
    @FindBy(xpath = "//button[contains(.,'Transactions')]")
    private WebElement transactionsButton;

    @FindBy(xpath = "//button[contains(.,'Deposit')]")
    private WebElement depositButton;

    @FindBy(xpath = "//button[contains(.,'Withdrawl')]")
    private WebElement withdrawalButton;

    // Message displayed when customer has no account
    @FindBy(xpath = "//span[@ng-show='noAccount']")
    private WebElement noAccountMessage;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public CustomerDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.helper = new PageHelper(driver);
        
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the customer dashboard is displayed.
     *
     * @return true when the welcome message is visible
     */
    public boolean isLoaded() {
        return helper.isVisible(welcomeMessage);
    }

    /**
     * Gets the balance displayed on the dashboard.
     *
     * @return balance as string
     */
    public String getBalance() {
        return helper.getText(accountBalance);
    }

    /**
     * Opens the Transactions view.
     */
    public void openTransactions() {
        helper.click(transactionsButton);
    }

    /**
     * Opens the Deposit view.
     */
    public void openDeposit() {
        helper.click(depositButton);
    }

    /**
     * Opens the Withdrawal view.
     */
    public void openWithdrawal() {
        helper.click(withdrawalButton);
    }

    /**
     * Checks if the "no account" message is visible.
     * This message appears when a customer logs in but has no account created.
     *
     * @return true if no account message is visible
     */
    public boolean isNoAccountMessageVisible() {
        return helper.isVisible(noAccountMessage);
    }

    /**
     * Gets the no account message text.
     *
     * @return the no account message text
     */
    public String getNoAccountMessage() {
        return helper.getText(noAccountMessage);
    }
}
