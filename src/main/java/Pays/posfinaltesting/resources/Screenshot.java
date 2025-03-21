package Pays.posfinaltesting.resources;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;

public class Screenshot {

    /**
     * Captures a screenshot and saves it to the specified directory.
     *
     * @param testCaseName The name of the test case (used for the file name).
     * @param driver       The AppiumDriver instance.
     * @return The absolute path of the saved screenshot.
     * @throws IOException If an I/O error occurs.
     */
    public String getScreenshotPath(String testCaseName, AppiumDriver driver) throws IOException {
        if (driver == null) {
            throw new IllegalArgumentException("Driver instance cannot be null.");
        }

        // Capture screenshot
        File screenshotSource = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Define the destination directory and file name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testCaseName + "_" + timestamp + ".png";
        String destinationPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + fileName;

        // Create the destination directory if it doesn't exist
        File destinationFile = new File(destinationPath);
        File parentDirectory = destinationFile.getParentFile();
        if (!parentDirectory.exists()) {
            parentDirectory.mkdirs(); // Create all necessary directories
        }

        // Copy the screenshot to the destination
        FileUtils.copyFile(screenshotSource, destinationFile);

        return destinationPath;
    }
    
    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public static byte[] takeScreenshot(WebDriver driver) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            return FileUtils.readFileToByteArray(src);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
