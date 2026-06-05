package com.website.testing;

import com.website.base.BaseTest;
import com.website.pages.HomePage;
import com.website.utilities.ExtentReportManager;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * LoginTest - Sample test class demonstrating framework usage.
 * 
 * NOTE: This class extends BaseTest, so it automatically gets:
 *   - Browser launch (before each test)
 *   - Navigation to baseUrl (before each test)
 *   - Screenshot on failure (after each test)
 *   - Browser close (after each test)
 *   - HTML report generation (end of suite)
 * 
 * LOCATION: src/test/java/.../testing/
 */
public class Website_HomepageTest extends BaseTest {

    @Test(priority = 1, description = "Verify website page loads successfully")
    public void verifyHomePageLoads() {
        ExtentReportManager.createTest("Verify home Page Loads");

        HomePage homePage = new HomePage(getDriver());

        // Verify page title
        String title = homePage.getPageTitle();
        Assert.assertTrue(title.contains("QA Automation Practice Playground"), "Page title should contain 'QA Automation Practice Playground'");
        logger.info("Page title verified: " + title);

        ExtentReportManager.getTest().pass("Website home page loaded successfully");
    }
}
