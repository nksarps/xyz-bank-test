package com.automation.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.helpers.PageHelper;

/**
 * Page object for the Add Customer form.
 */
public class AddCustomerPage {
    private final WebDriver driver;
    private final PageHelper helper;

    // Using the xpath locator to find form fields and submit button because they do not have 
    // unique IDs and all input fields have the same class name 
    // "form-control ng-pristine ng-untouched ng-invalid ng-invalid-required"
    @FindBy(xpath = "//input[@ng-model='fName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@ng-model='lName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@ng-model='postCd']")
    private WebElement postCodeInput;

    // Since this is the only button on the page, and it has a unique class name "btn-default",
    // we can use that to locate the submit button
    @FindBy(className = "btn-default")
    private WebElement addCustomerSubmitButton;

    @FindBy(css = "button.btn.home")
    private WebElement homeButton;

    /**
     * Constructor that initializes PageFactory elements and helper utilities.
     *
     * @param driver active WebDriver instance
     */
    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        this.helper = new PageHelper(driver);
        
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies that the add customer form is displayed.
     *
     * @return true when the first name input is visible
     */
    public boolean isLoaded() {
        return helper.isVisible(firstNameInput);
    }

    /**
     * Adds a new customer and returns the alert text.
     *
     * @param firstName customer first name
     * @param lastName customer last name
     * @param postCode customer postal code
     * @return alert message text
     */
    public String addCustomer(String firstName, String lastName, String postCode) {
        helper.type(firstNameInput, firstName);
        helper.type(lastNameInput, lastName);
        helper.type(postCodeInput, postCode);
        helper.click(addCustomerSubmitButton);

        Alert alert = driver.switchTo().alert();
        String message = alert.getText();
        alert.accept();
        return message;
    }

    /**
     * Clicks the Home button to return to the main landing page.
     */
    public void goToHome() {
        helper.click(homeButton);
    }
}
