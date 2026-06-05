package com.website.testing;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.website.base.BaseTest;
import com.website.pages.Sec1_BasicForm_Page;
import com.website.utilities.ExcelReader;

public class SectionOneTest extends BaseTest {

    private Sec1_BasicForm_Page sec1Page;

    @BeforeMethod
    public void setupPageObject() {
        sec1Page = new Sec1_BasicForm_Page(getDriver());
    }

    @Test(priority=1, description=" Test Case -------------> Verify Heading Of Section one")
    public void verifySectionOneHeading() {
        String actualHeading = sec1Page.getSec1HeadingText();
        Assert.assertEquals(actualHeading, "Section 1: Basic Form Elements", 
                "The Section 1 heading text did not match!");
    }

    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        String excelFilePath = "src/main/resources/testdata.xlsx";
        String sheetName = "LoginData"; 
        return ExcelReader.getTestData(excelFilePath, sheetName);
    }

    @Test(priority = 2, dataProvider = "userData", description = "Test Case -------------> Verify form submission scenarios : Positive | Negative")
    public void verifySubmissionMessages(String nameValue, String passwordValue, String emailIdValue, 
                                         String phoneValue, String commentValue, String scenarioType, 
                                         String expectedSuccessMsg, String expGlobalErr, 
                                         String expFNameErr, String expPwdErr, String expEmailErr) {
        
        // ---------------------------------------------------------------------------------
        // CONSOLE VISUAL BLOCK: Know exactly which iteration is running!
        // ---------------------------------------------------------------------------------
        System.out.println("\n=================================================================================");
        System.out.println("▶ RUNNING ITERATION: [" + scenarioType.toUpperCase() + " SCENARIO]");
        System.out.println("  Inputs -> Name: '" + nameValue + "', Pwd: '" + passwordValue + "', Email: '" + emailIdValue + "'");
        System.out.println("=================================================================================");

        SoftAssert softAssert = new SoftAssert();
        
        sec1Page.enterFirstName(nameValue);
        sec1Page.enterPassword(passwordValue);
        sec1Page.enterEmail(emailIdValue);
        sec1Page.enterPhone(phoneValue);
        sec1Page.enterComment(commentValue);
        sec1Page.clickOnSubmitFormButton();
        
        if (scenarioType.equalsIgnoreCase("Positive")) {
            String actualSuccessMsg = sec1Page.isSuccessMessageDisplayed();
            
            // Adding context messages inside assertions shows you what exactly failed in the log
            Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, 
                "[FAILED] Positive scenario failed. Success message banner text mismatch.");
            
        } else if (scenarioType.equalsIgnoreCase("Negative")) {
            
            expGlobalErr = (expGlobalErr != null) ? expGlobalErr : "";
            expFNameErr  = (expFNameErr != null) ? expFNameErr : "";
            expPwdErr    = (expPwdErr != null) ? expPwdErr : "";
            expEmailErr  = (expEmailErr != null) ? expEmailErr : "";

            // Custom assertion messages identify which field is culprit instantly
            softAssert.assertEquals(sec1Page.isSGlobalErrorMessageDisplayed(), expGlobalErr, 
                "[ERROR MISMATCH] Master Error Banner text failed.");
            softAssert.assertEquals(sec1Page.firstNameErrorMessage(), expFNameErr, 
                "[ERROR MISMATCH] First Name validation text failed.");
            softAssert.assertEquals(sec1Page.passwordErrorMessage(), expPwdErr, 
                "[ERROR MISMATCH] Password validation text failed.");
            softAssert.assertEquals(sec1Page.emailErrorMessage(), expEmailErr, 
                "[ERROR MISMATCH] Email validation text failed.");
        }

        System.out.println("✓ COMPLETED ITERATION: [" + scenarioType.toUpperCase() + "]");
        System.out.println("=================================================================================\n");

        softAssert.assertAll();
    }
    
    @Test(priority=3, description=" Test Case -------------> Verify Reset message in section one")
    public void verifyResetMessage() {
    	sec1Page.clickOnResetFormButton();
    	String resetMessage= sec1Page.isResetSuccessMessageDisplayed();
    	
    	Assert.assertEquals(resetMessage,"Form has been reset.");
    }
    
 
}









