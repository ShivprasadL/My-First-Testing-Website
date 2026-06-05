package com.website.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import java.util.List;

import com.website.utilities.WaitUtils;

public class Sec1_BasicForm_Page {
    
    private WebDriver driver;
    private WaitUtils waitUtils; 
    
    public Sec1_BasicForm_Page(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver, 15);        
    }
    
    // Locators
    private By headingSec1 = By.cssSelector("#section-1-basics h2");
    private By firstName = By.id("firstName");
    private By password = By.id("password");
    private By email = By.id("email");
    private By phone = By.id("phone");
    private By comments = By.id("comments");
    private By SubmitBtn  = By.xpath("//button[text()='Submit Form']");
    private By resetBtn = By.id("reset-basic");
    
    private By globalSuccessMessage = By.xpath("//div[text()='Success: Form submitted successfully!']");
    private By globalErrorMessage = By.xpath("//div[text()='Error: Please fix the mandatory fields before submitting.']");
    private By resetSuccessMessage = By.xpath("//div[text()='Form has been reset.']");
    
    private By firstNameError = By.id("firstName-error");
    private By passwordError = By.id("password-error");
    private By emailError = By.id("email-error");
    
    //===================== Safe Helper Method ================================
    
    /**
     * Safely attempts to get the text of an element if it appears. 
     * If the element is not displayed or times out, it returns an empty string.
     */
    private String getSafeElementText(By locator) {
        try {
            // Check if element is even in the DOM before waiting long
            List<WebElement> elements = driver.findElements(locator);
            if (elements.isEmpty() || !elements.get(0).isDisplayed()) {
                return "";
            }
            return elements.get(0).getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    //===================== Actions From Users ================================
    
    public void enterFirstName(String name) {
        WebElement firstNameField = waitUtils.waitForClickable(firstName);
        firstNameField.clear();
        firstNameField.sendKeys(name);
    }

    public void enterPassword(String passValue) {
        WebElement passwordField = waitUtils.waitForClickable(password);
        passwordField.clear();
        passwordField.sendKeys(passValue);
    }

    public void enterEmail(String emailIdValue) {
        WebElement emailField = waitUtils.waitForClickable(email);
        emailField.clear();
        emailField.sendKeys(emailIdValue);
    }

    public void enterPhone(String phoneValue) {
        WebElement phoneField = waitUtils.waitForClickable(phone);
        phoneField.clear();
        phoneField.sendKeys(phoneValue);
    }  
  
    public void enterComment(String commentValue) {
        WebElement commentField = waitUtils.waitForClickable(comments);
        commentField.clear();
        commentField.sendKeys(commentValue);
    }
    
    public void clickOnSubmitFormButton() {
        waitUtils.waitForClickable(SubmitBtn).click();
    }
        
    public void clickOnResetFormButton() {
        waitUtils.waitForClickable(resetBtn).click();
    }
    
    //============================ Error/Success/Text messages ============================
    
    public String getSec1HeadingText() {
        WebElement heading = driver.findElement(headingSec1);
        return heading.getText();
    }
    
    public String isSuccessMessageDisplayed() {
        return getSafeElementText(globalSuccessMessage);
    }
    
    public String isSGlobalErrorMessageDisplayed() {
        return getSafeElementText(globalErrorMessage);
    }   
    
    public String isResetSuccessMessageDisplayed() {
        return getSafeElementText(resetSuccessMessage);
    }
    
    public String firstNameErrorMessage() {
        return getSafeElementText(firstNameError);
    }
    
    public String passwordErrorMessage() {
        return getSafeElementText(passwordError);
    }        
    
    public String emailErrorMessage() {
        return getSafeElementText(emailError);
    }      
}