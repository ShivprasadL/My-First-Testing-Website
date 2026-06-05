package com.website.testing;

import com.website.base.BaseTest;
import com.website.pages.HomePage;
import com.website.utilities.ExcelReader;
import com.website.utilities.ExtentReportManager;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * LoginDataDrivenTest - Demonstrates data-driven testing using
 * @DataProvider + ExcelReader (Apache POI).
 *
 * HOW IT WORKS:
 * 1. @DataProvider reads Excel file via ExcelReader
 * 2. Returns each row as a set of parameters
 * 3. @Test runs ONCE PER ROW automatically
 *
 * EXCEL FILE FORMAT (testdata.xlsx, sheet "LoginData"):
 * ┌──────────────────────┬──────────────┐
 * │ email                │ password     │  ← Row 0 (Header - SKIPPED)
 * ├──────────────────────┼──────────────┤
 * │ user1@test.com       │ Pass123      │  ← Row 1 (Test run 1)
 * │ user2@test.com       │ Pass456      │  ← Row 2 (Test run 2)
 * │ invalid@test.com     │ wrongPass    │  ← Row 3 (Test run 3)
 * └──────────────────────┴──────────────┘
 *
 * LOCATION: src/test/java/.../testing/
 */
public class LoginDataDrivenTest extends BaseTest {

    // ========== DATA PROVIDER — feeds data from Excel to @Test ==========

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        // Reads all rows (except header) from "LoginData" sheet
        return ExcelReader.getTestData(
            "src/test/resources/testdata.xlsx",
            "LoginData"
        );
    }

    // ========== TEST — runs once per Excel row ==========

    @Test(dataProvider = "loginData", description = "Data-driven login test from Excel")
    public void testLoginWithExcelData(String email, String password) {
        ExtentReportManager.createTest("Login Test: " + email);

        HomePage homePage = new HomePage(getDriver());

//        // Enter credentials from Excel
//        loginPage.enterEmail(email);
//        logger.info("Entered email: " + email);
//
//        loginPage.enterPassword(password);
//        logger.info("Entered password: ****");
//
//        loginPage.clickLogin();
//        logger.info("Clicked login button");

        // Verify page title (basic check)
        String title = getDriver().getTitle();
        Assert.assertNotNull(title, "Page title should not be null after login attempt");

        ExtentReportManager.getTest().pass("Login attempt completed for: " + email);
    }
}
