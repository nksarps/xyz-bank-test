package com.automation.pages;

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

    // Using xpath locators to find elements on the dashboard because they do not have unique IDs or class name.
    @FindBy(xpath = "//strong[contains(.,'Welcome')]")
    private WebElement welcomeMessage;

    @FindBy(id = "accountSelect")
    private WebElement accountDropdown;

    // Using xpath locators to find account details because they do not have unique IDs or class names 
    // and all details are in the same section.
    @FindBy(xpath = "//div[@ng-hide='noAccount']//strong[1]")
    private WebElement accountNumberLabel;

    @FindBy(xpath = "//div[@ng-hide='noAccount']//strong[2]")
    private WebElement balanceLabel;

    @FindBy(xpath = "//div[@ng-hide='noAccount']//strong[3]")
    private WebElement currencyLabel;

    // Using xpath locators to find action buttons because they do not have unique IDs 
    // and all buttons have the same class name "btn btn-lg tab"
    @FindBy(xpath = "//button[contains(.,'Transactions')]")
    private WebElement transactionsButton;

    @FindBy(xpath = "//button[contains(.,'Deposit')]")
    private WebElement depositButton;

    @FindBy(xpath = "//button[contains(.,'Withdrawl')]")
    private WebElement withdrawalButton;

    // @FindBy(xpath = "//button[contains(.,'Logout')]")
    @FindBy(className = "logout")
    private WebElement logoutButton;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public CustomerDashboardPage(WebDriver driver) {
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
     * Gets the account number displayed on the dashboard.
     *
     * @return account number as string
     */
    public String getAccountNumber() {
        return helper.getText(accountNumberLabel);
    }

    /**
     * Gets the balance displayed on the dashboard.
     *
     * @return balance as string
     */
    public String getBalance() {
        return helper.getText(balanceLabel);
    }

    /**
     * Gets the currency displayed on the dashboard.
     *
     * @return currency as string
     */
    public String getCurrency() {
        return helper.getText(currencyLabel);
    }

    /**
     * Selects an account from the dropdown if customer has multiple accounts.
     *
     * @param accountNumber account number to select
     */
    public void selectAccount(String accountNumber) {
        helper.selectByVisibleText(accountDropdown, accountNumber);
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
     * Logs out from the customer account.
     */
    public void logout() {
        helper.click(logoutButton);
    }
}
