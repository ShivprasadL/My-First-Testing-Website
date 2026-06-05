package com.website.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.website.utilities.ConfigReader;
import com.website.utilities.DriverFactory;
import com.website.utilities.ExtentReportManager;
import com.website.utilities.ScreenshotUtils;

import java.util.Properties;

/**
 * BaseTest - Parent class that ALL test classes must extend.
 * 
 * WHY: Eliminates repetitive setup/teardown code in every test class.
 * HOW: class LoginTest extends BaseTest { ... }
 *      Then driver, prop, logger are automatically available.
 * REUSABLE: Yes - works with any Selenium project.
 * LOCATION: src/main/java/.../basic/
 * 
 * EXECUTION FLOW:
 * @BeforeSuite  -> Initialize ExtentReports (once for entire suite)
 * @BeforeMethod -> Load config, launch browser, open URL (before EACH test)
 * @Test         -> Your actual test runs here
 * @AfterMethod  -> Screenshot on failure, close browser (after EACH test)
 * @AfterSuite   -> Generate HTML report (once at the end)
 */
@Listeners(com.website.utilities.TestListener.class)
public class BaseTest {

	// 1. Changed all variables to PRIVATE
    private WebDriver driver;
    private Properties prop;
    private DriverFactory driverFactory;
    private ConfigReader configReader;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    public void setUpSuite() {
        ExtentReportManager.initReporter();
        logger.info("========== TEST SUITE STARTED ==========");
    }

    @BeforeMethod
    public void setUp() {
        // 1. Load config.properties
        configReader = new ConfigReader();
        prop = configReader.init_prop();
        logger.info("Config properties loaded");

        // 2. Launch browser (reads browser name from config)
        driverFactory = new DriverFactory();
        driver = driverFactory.initDriver(prop);
        logger.info("Browser launched: " + prop.getProperty("browser"));

        // 3. Navigate to base URL
        String baseUrl = prop.getProperty("baseUrl");
        driver.get(baseUrl);
        logger.info("Navigated to: " + baseUrl);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("TEST FAILED: " + result.getName());
            String path = ScreenshotUtils.takeScreenshot(driver, result.getName());
            ExtentReportManager.attachScreenshot(path, "Failure Screenshot");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("TEST PASSED: " + result.getName());
        } else {
            logger.warn("TEST SKIPPED: " + result.getName());
        }

        DriverFactory.quitDriver();
        logger.info("Browser closed");
    }

    @AfterSuite
    public void tearDownSuite() {
        ExtentReportManager.flushReporter();
        logger.info("========== TEST SUITE FINISHED ==========");
    }
    
 // ==========================================
    // 2. ADDED GETTER METHODS
    // ==========================================
    
    /**
     * Call this method from your Test classes to get the WebDriver instance.
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Call this method from your Test classes to access config properties.
     */
    public Properties getProp() {
        return prop;
    }
}
