package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.helpers.PageHelper;

/**
 * Page object for the Withdrawl transaction view.
 */
public class WithdrawalPage {
    private final PageHelper helper;

    // @FindBy(xpath = "//input[@ng-model='amount']")
    @FindBy(className = "form-control")
    private WebElement amountInput;

    // @FindBy(css = "button[type='submit']")
    @FindBy(className = "btn-default")
    private WebElement withdrawButton;

    // @FindBy(xpath = "//span[@ng-show='message' or @ng-show='notEnough']/strong")
    @FindBy(className = "error")
    private WebElement resultMessage;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public WithdrawalPage(WebDriver driver) {
        this.helper = new PageHelper(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the withdrawl page is displayed.
     *
     * @return true when the amount input is visible
     */
    public boolean isLoaded() {
        return helper.isVisible(amountInput);
    }

    /**
     * Withdraws the specified amount.
     *
     * @param amount amount to withdraw
     */
    public void withdraw(String amount) {
        helper.type(amountInput, amount);
        helper.click(withdrawButton);
    }

    /**
     * Gets the result message displayed after withdrawal attempt.
     *
     * @return result message text (success or error)
     */
    public String getResultMessage() {
        return helper.getText(resultMessage);
    }
}
