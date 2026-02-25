package com.automation.pages.home;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.automation.helpers.PageHelper;

/**
 * Page object for the Customer Login page.
 */
public class CustomerLoginPage {
    private final PageHelper helper;
    public final WebDriver driver;

    @FindBy(id = "userSelect")
    private WebElement customerDropdown;

     @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public CustomerLoginPage(WebDriver driver) {
        this.driver = driver;
        this.helper = new PageHelper(driver);
        
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the customer login page is displayed.
     *
     * @return true when the customer dropdown is visible
     */
    public boolean isLoaded() {
        return helper.isVisible(customerDropdown);
    }

    /**
     * Logs in as the specified customer.
     *
     * @param customerName name of the customer to login as
     */
    public void loginAs(String customerName) {
        helper.selectByVisibleText(customerDropdown, customerName);
        helper.click(loginButton);
    }

    /**
     * Checks if a customer exists in the login dropdown.
     *
     * @param customerName name of the customer to check
     * @return true if customer is in the dropdown
     */
    public boolean isCustomerInDropdown(String customerName) {
        Select select = new Select(customerDropdown);
        List<WebElement> options = select.getOptions();
        
        for (WebElement option : options) {
            String optionText = option.getText().trim();
            if (optionText.equals(customerName)) {
                return true;
            }
        }
        return false;
    }
}
