package com.automation.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.helpers.PageHelper;

/**
 * Page object for the Customers list view.
 */
public class CustomersPage {
    private final PageHelper helper;

    @FindBy(xpath = "//input[@ng-model='searchCustomer']")
    private WebElement searchInput;

    @FindBy(xpath = "//table//tbody//tr")
    private List<WebElement> customerRows;

    // Using the xpath locator to find delete buttons because they do not have unique IDs or class names
    @FindBy(xpath = "//button[contains(.,'Delete')]")
    private List<WebElement> deleteButtons;

    @FindBy(css = "button.btn.home")
    private WebElement homeButton;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public CustomersPage(WebDriver driver) {
        this.helper = new PageHelper(driver);

        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the customers page is displayed.
     *
     * @return true when the search input is visible
     */
    public boolean isLoaded() {
        return helper.isVisible(searchInput);
    }

    /**
     * Searches for a customer by name or other criteria.
     *
     * @param keyword search term
     */
    public void searchCustomer(String keyword) {
        helper.type(searchInput, keyword);
        // Give Angular time to filter the results
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Gets the count of visible customer rows.
     *
     * @return number of customer rows displayed
     */
    public int getCustomerCount() {
        return customerRows.size();
    }

    /**
     * Deletes a customer by clicking the delete button at the specified index.
     *
     * @param index zero-based index of the customer to delete
     */
    public void deleteCustomerByIndex(int index) {
        if (index >= 0 && index < deleteButtons.size()) {
            helper.click(deleteButtons.get(index));
        }
    }

    /**
     * Clicks the Home button to return to the main landing page.
     */
    public void goToHome() {
        helper.click(homeButton);
    }
}
