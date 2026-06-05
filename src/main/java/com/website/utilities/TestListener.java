package com.website.utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        // Runs once before the whole suite starts
        ExtentReportManager.initReporter();
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Automatically grabs your @Test method name and creates an entry in the report!
        ExtentReportManager.createTest(result.getMethod().getMethodName());
        ExtentReportManager.getTest().info("Test Execution Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Automatically logs a green PASS in the report
        ExtentReportManager.getTest().pass("Test Passed Successfully");
    }

    @Override
//    public void onTestFailure(ITestResult result) {
//        // Automatically logs a red FAIL and prints the error trace
//        ExtentReportManager.getTest().fail("Test Failed");
//        ExtentReportManager.getTest().fail(result.getThrowable());
//        
//        // (Optional) If you have a screenshot method in BaseTest, you would call it here
//    }
    public void onTestFailure(ITestResult result) {
        // 1. Get the test object first
        ExtentTest test = ExtentReportManager.getTest();
        
        // 2. Check if it actually exists before logging
        if (test != null) {
            // Automatically logs a red FAIL and prints the error trace
            test.fail("Test Failed");
            test.fail(result.getThrowable());
            
            // (Optional) If you have a screenshot method in BaseTest, you would call it here
            // String screenshotPath = BaseClass.takeScreenshot(result.getName());
            // test.addScreenCaptureFromPath(screenshotPath);
            
        } else {
            // 3. The fallback: What to do if the test crashed before it started
            System.err.println("CRITICAL: Test failed before ExtentTest was initialized!");
            System.err.println("Method Name: " + result.getMethod().getMethodName());
            System.err.println("Reason: " + result.getThrowable());
        }
    }
    
    
    

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().skip("Test Skipped");
        ExtentReportManager.getTest().skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Runs once after all tests finish to save the file
        ExtentReportManager.flushReporter();
    }
}