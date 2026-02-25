package com.automation.pages.customer;

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
    public final WebDriver driver;

    @FindBy(xpath = "//input[@type='number' and @ng-model='amount' and @placeholder='amount']")
    private WebElement amountInput;

    @FindBy(xpath = "//button[@type='submit' and contains(@class,'btn-default') and normalize-space()='Withdraw']")
    private WebElement withdrawButton;

    @FindBy(xpath = "//span[contains(@class,'error') and @ng-show='message']")
    private WebElement resultMessage;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public WithdrawalPage(WebDriver driver) {
        this.driver = driver;
        this.helper = new PageHelper(driver);

        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the withdrawal page is displayed.
     *
     * @return true when the amount input is visible
     */
    public boolean isLoaded() {
        return helper.isVisible(withdrawButton);
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
        if (!helper.isVisible(resultMessage)) {
            return "";
        }
        return helper.getText(resultMessage);
    }
}
