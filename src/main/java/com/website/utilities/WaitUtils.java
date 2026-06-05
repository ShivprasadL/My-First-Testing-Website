package com.website.utilities;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * WaitUtils - Explicit wait methods for Selenium.
 * 
 * WHY: Thread.sleep() is unreliable. Explicit waits wait only until condition is met.
 * HOW: new WaitUtils(driver, 15) then call waitForClickable(By.id("login"))
 * REUSABLE: Yes - works with any Selenium project.
 * LOCATION: src/main/java/.../utilities/
 */
public class WaitUtils {

    private WebDriverWait wait;

    public WaitUtils(WebDriver driver, int timeoutInSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    /** Waits until element is VISIBLE (present + displayed). */
    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Waits until element is CLICKABLE (visible + enabled). */
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** Waits until element is PRESENT in DOM (may not be visible). */
    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /** Waits until page title contains given text. */
    public boolean waitForTitleContains(String titlePart) {
        return wait.until(ExpectedConditions.titleContains(titlePart));
    }

    /** Waits until URL contains given text. */
    public boolean waitForUrlContains(String urlPart) {
        return wait.until(ExpectedConditions.urlContains(urlPart));
    }

    /** Waits until ALL matching elements are visible. */
    public List<WebElement> waitForAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /** Waits until element DISAPPEARS from page. */
    public boolean waitForInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /** Waits until a JavaScript ALERT appears. */
    public Alert waitForAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }
}
