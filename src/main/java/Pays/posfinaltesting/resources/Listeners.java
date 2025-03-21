package Pays.posfinaltesting.resources;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;



public class Listeners extends Screenshot implements ITestListener{
	ExtentReports extent = ExtentReporterNG.getReporterObject();
	  ExtentTest test;
	  AppiumDriver driver;
	    private static Map<String, Long> startTimeMap = new HashMap<>();
	    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	 @Override
	    public void onTestStart(ITestResult result) {
	   test = extent.createTest(result.getMethod().getMethodName());
	   startTimeMap.put(result.getMethod().getMethodName(), System.currentTimeMillis());
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	        long duration = calculateDuration(result);
	     test.log(Status.PASS, "Test Passed");
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	    	 test.log(Status.FAIL, "Test Failed: " + result.getThrowable());

	         try {
	             // Get driver instance from test class
	             WebDriver driver = (WebDriver) result.getTestClass().getRealClass()
	                     .getField("driver").get(result.getInstance());

	             if (driver != null) {
	                 // Capture screenshot
	                 byte[] screenshotBytes = captureScreenshot(driver);

	                 // Attach to Allure
	                 Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshotBytes));

	                 // Save to file and attach to Extent Reports
	                 String screenshotPath = saveScreenshotToFile(driver, result.getMethod().getMethodName());
	                 test.addScreenCaptureFromPath(screenshotPath);
	                 captureLogcatAndAttachToAllure();
	             }
	         } catch (Exception e) {
	             e.printStackTrace();
	             
	         }
	    }



	    @Override
	    public void onTestSkipped(ITestResult result) {
	       test.skip(result.getThrowable());
	    }

	    @Override
	    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	        
	    }

	    @Override
	    public void onStart(ITestContext context) {
	      
	    }

	    @Override
	    public void onFinish(ITestContext context) {
	    extent.flush();
	    } 
	    // Helper method to calculate test duration
	    private long calculateDuration(ITestResult result) {
	        long startTime = startTimeMap.getOrDefault(result.getMethod().getMethodName(), 0L);
	        long endTime = System.currentTimeMillis();
	        return (endTime - startTime) / 1000; // Convert ms to seconds
	    }
	    @Attachment(value = "Screenshot on Failure", type = "image/png")
	    public byte[] captureScreenshot(WebDriver driver) {
	        try {
	            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    private String saveScreenshotToFile(WebDriver driver, String methodName) {
	        String path = "screenshots/" + methodName + ".png";
	        try {
	            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	            FileUtils.copyFile(src, new File(path));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return path;
	    }
	    private void captureLogcatAndAttachToAllure() {
	        String logcatFilePath = "logcat.txt"; // Path to save logcat logs

	        try {
	            // Capture logcat logs
	            Process process = Runtime.getRuntime().exec("adb logcat -d > " + logcatFilePath);
	            process.waitFor(); // Wait for the command to complete

	            // Attach the logcat file to Allure report
	            Path logcatPath = Paths.get(logcatFilePath);
	            if (Files.exists(logcatPath)) {
	                Allure.addAttachment("Logcat Logs (Test Failed)", "text/plain", new FileInputStream(logcatPath.toFile()), "txt");
	            } else {
	                System.out.println("Logcat file not found: " + logcatFilePath);
	            }
	        } catch (IOException | InterruptedException e) {
	            System.out.println("Failed to capture logcat logs: " + e.getMessage());
	        }
	    }
}
