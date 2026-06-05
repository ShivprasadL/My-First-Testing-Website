package com.website.pages;

import org.openqa.selenium.WebDriver;

import com.website.utilities.WaitUtils;

/**
 * LoginPage - Page Object for Facebook Login Page.
 * 
 * WHY PAGE OBJECTS:
 * - Separates locators from test logic (easy maintenance)
 * - If Facebook changes a locator, you fix it HERE only, not in 50 tests
 * - Makes tests readable: loginPage.enterEmail("test@mail.com")
 * 
 * PATTERN: One class per page. Locators at top, actions as methods.
 * LOCATION: src/main/java/.../pages/
 */
public class HomePage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // ========== LOCATORS (kept private — only this class knows them) ==========
//    private By emailField = By.id("email");
//    private By passwordField = By.id("pass");
//    private By loginButton = By.name("login");

    // ========== CONSTRUCTOR ==========
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver, 15);
    }

//    // ========== PAGE ACTIONS (public — tests call these) ==========
//
//    public void enterEmail(String email) {
//        waitUtils.waitForVisible(emailField).clear();
//        driver.findElement(emailField).sendKeys(email);
//    }
//
//    public void enterPassword(String password) {
//        waitUtils.waitForVisible(passwordField).clear();
//        driver.findElement(passwordField).sendKeys(password);
//    }
//
//    public void clickLogin() {
//        waitUtils.waitForClickable(loginButton).click();
//    }

    public String getPageTitle() {
        return driver.getTitle();
    }

//    public boolean isLoginButtonDisplayed() {
//        return waitUtils.waitForVisible(loginButton).isDisplayed();
//    }
}
