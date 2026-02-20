package com.automation.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.helpers.PageHelper;

/**
 * Page object for the Transactions view.
 */
public class TransactionsPage {
    private final PageHelper helper;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped']//tbody/tr")
    private List<WebElement> transactionRows;

    // Using the xpath locator to find reset and back buttons because they do not have unique IDs 
    // and both buttons have the same class name "btn"
    @FindBy(xpath = "//button[contains(.,'Reset')]")
    private WebElement resetButton;

    @FindBy(xpath = "//button[contains(.,'Back')]")
    private WebElement backButton;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public TransactionsPage(WebDriver driver) {
        this.helper = new PageHelper(driver);

        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the transactions page is displayed.
     *
     * @return true when the back button is visible
     */
    public boolean isLoaded() {
        return helper.isVisible(backButton);
    }

    /**
     * Gets the count of transactions displayed in the table.
     *
     * @return number of transaction rows
     */
    public int getTransactionCount() {
        return transactionRows.size();
    }

    /**
     * Gets all transaction rows.
     *
     * @return list of transaction row elements
     */
    public List<WebElement> getTransactionRows() {
        return transactionRows;
    }

    /**
     * Resets the transactions table/filters.
     */
    public void reset() {
        helper.click(resetButton);
    }

    /**
     * Goes back to the customer dashboard.
     */
    public void back() {
        helper.click(backButton);
    }
}
