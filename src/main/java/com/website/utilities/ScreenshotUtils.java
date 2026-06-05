package com.website.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtils - Captures and saves screenshots.
 * 
 * WHY: Automatically captures proof of failure for debugging.
 * HOW: ScreenshotUtils.takeScreenshot(driver, "testName") -> saves PNG to screenshots/
 * REUSABLE: Yes - works with any Selenium project.
 * LOCATION: src/main/java/.../utilities/
 */
public class ScreenshotUtils {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);

    /**
     * Takes a screenshot and saves it to the screenshots/ folder.
     * Returns the absolute path to the saved screenshot file.
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + fileName;

        try {
            Path screenshotsDir = Paths.get(System.getProperty("user.dir"), "screenshots");
            Files.createDirectories(screenshotsDir);

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            Files.copy(srcFile.toPath(), destFile.toPath());
            //FileHandler.copy(srcFile, destFile);

            logger.info("Screenshot saved: " + filePath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getMessage());
            e.printStackTrace();
        }

        return filePath;
    }
}
