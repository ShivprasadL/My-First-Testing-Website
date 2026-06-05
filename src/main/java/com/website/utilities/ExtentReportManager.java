package com.website.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ExtentReportManager - Manages HTML test report generation.
 * 
 * WHY: Generates beautiful HTML reports showing pass/fail with screenshots.
 * HOW: initReporter() in @BeforeSuite, createTest() per test, flushReporter() in @AfterSuite
 * REUSABLE: Yes - works with any Selenium project.
 * LOCATION: src/main/java/.../utilities/
 */
public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);

    /** Initialize ExtentReports. Call ONCE in @BeforeSuite. */
    public static void initReporter() {
        String reportPath = System.getProperty("user.dir") + "/reports/TestReport.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Results");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        logger.info("ExtentReports initialized. Report: " + reportPath);
    }

    /** Creates a new test entry in the report. Call at start of each @Test. */
    public static ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
        return test;
    }

    /** Returns current thread's ExtentTest instance. */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    /** Attaches a failure screenshot to the report. */
    public static void attachScreenshot(String screenshotPath, String title) {
        if (extentTest.get() != null) {
            extentTest.get().fail(title,
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
    }

    /** Writes report to disk. Call ONCE in @AfterSuite. */
    public static void flushReporter() {
        if (extent != null) {
            extent.flush();
            logger.info("ExtentReports flushed — HTML report generated");
        }
    }
}
