package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.helpers.PageHelper;

/**
 * Page object for the Deposit transaction view.
 */
public class DepositPage {
    private final PageHelper helper;

    // @FindBy(xpath = "//input[@ng-model='amount']")
    @FindBy(className = "form-control")
    private WebElement amountInput;

    // @FindBy(css = "button[type='submit']")
    @FindBy(className = "btn-default")
    private WebElement depositButton;

    // @FindBy(xpath = "//span[@ng-show='message']/strong")
    @FindBy(className = "error")
    private WebElement successMessage;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public DepositPage(WebDriver driver) {
        this.helper = new PageHelper(driver);
        
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the deposit page is displayed.
     *
     * @return true when the amount input is visible
     */
    public boolean isLoaded() {
        return helper.isVisible(amountInput);
    }

    /**
     * Deposits the specified amount.
     *
     * @param amount amount to deposit
     */
    public void deposit(String amount) {
        helper.type(amountInput, amount);
        helper.click(depositButton);
    }

    /**
     * Gets the success message displayed after deposit.
     *
     * @return success message text
     */
    public String getSuccessMessage() {
        return helper.getText(successMessage);
    }
}
