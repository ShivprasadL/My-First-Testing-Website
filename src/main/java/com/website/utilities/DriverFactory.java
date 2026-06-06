package com.website.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Properties;

/**
 * DriverFactory - Manages WebDriver creation and lifecycle.
 * 
 * WHY NEEDED:
 * - Centralizes all browser setup logic in ONE place (no copy-paste in every test)
 * - Supports multiple browsers (Chrome, Firefox, Edge) from config.properties
 * - Uses ThreadLocal for thread-safe parallel test execution
 * - Handles headless mode, implicit waits, and browser options
 * 
 * REUSABLE: Yes — copy to any Selenium project, works with any website.
 * LOCATION: src/main/java/.../utilities/
 */
public class DriverFactory {

    // ThreadLocal ensures each thread gets its own WebDriver instance
    // This is CRITICAL when running tests in parallel with TestNG
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * Initializes the WebDriver based on browser name from config.properties.
     * Reads: browser, headless, implicitWait from Properties.
     */
    public WebDriver initDriver(Properties prop) {
        String browserName = prop.getProperty("browser", "chrome").trim().toLowerCase();
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless", "false").trim());

        switch (browserName.toLowerCase()) {

        case "chrome":
            // Forces WebDriverManager to read the actual browser version on the host machine
            WebDriverManager.chromedriver().browserVersion("").setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            
            // Dynamic Headless checking for your local/Jenkins execution
            if (headless) {
                chromeOptions.addArguments("--headless=new");
            }
            // Essential flags to prevent crashes on GitHub cloud machines
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("--disable-notifications");
            tlDriver.set(new ChromeDriver(chromeOptions));
            break;

        case "firefox":
            // Fixes Firefox driver matching for GitHub Actions
            WebDriverManager.firefoxdriver().browserVersion("").setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            
            if (headless) {
                firefoxOptions.addArguments("-headless");
            }
            // Essential for server nodes
            firefoxOptions.addArguments("--disable-gpu");
            
            tlDriver.set(new FirefoxDriver(firefoxOptions));
            break;

        case "edge":
            // Fixes Edge driver matching for GitHub Actions
            WebDriverManager.edgedriver().browserVersion("").setup();
            EdgeOptions edgeOptions = new EdgeOptions();
            
            if (headless) {
                edgeOptions.addArguments("--headless=new");
            }
            // Essential for server nodes
            edgeOptions.addArguments("--disable-gpu");
            edgeOptions.addArguments("--no-sandbox");
            
            tlDriver.set(new EdgeDriver(edgeOptions));
            break;

        default:
            throw new IllegalArgumentException("Browser : " + browserName + " is not supported.");
    }

        // Apply implicit wait from config.properties
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait", "10").trim());
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();

        return getDriver();
    }

    /**
     * Returns the thread-safe WebDriver instance for the current thread.
     * Can be called from anywhere: DriverFactory.getDriver()
     */
    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    /**
     * Quits the driver and removes it from ThreadLocal to prevent memory leaks.
     * Always call this in @AfterMethod.
     */
    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove(); // Prevents memory leak
        }
    }
}
