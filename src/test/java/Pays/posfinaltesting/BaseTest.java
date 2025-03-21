package Pays.posfinaltesting;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.nio.file.Files;
import java.io.FileInputStream;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.qameta.allure.Allure;

public class BaseTest {
	
	public static AndroidDriver driver;
	public AppiumDriverLocalService service;
	@BeforeClass(alwaysRun = true)
	public void ConfigureAppium() throws MalformedURLException, URISyntaxException {
		service = new AppiumServiceBuilder()
	            .withAppiumJS(new File("C:\\Users\\prati\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
	            .withIPAddress("127.0.0.1")
	            .usingPort(4723)
	            .build();
	    service.start();

	    UiAutomator2Options options = new UiAutomator2Options();
	    options.setPlatformName("Android");
	    options.setDeviceName("LANDI C20pro");
	    options.setApp("E:\\EclipseWorkspace\\posfinaltesting\\src\\test\\java\\resources\\SANDBOX_Pays_17_Mar_2025_14_28_v1.36_PAYMENT_.apk");
	    options.setAutoGrantPermissions(true); 
	    options.setNewCommandTimeout(Duration.ofSeconds(10000));
	   
	    // Prevent resetting during normal execution
	

	    options.setAppPackage("com.pays.pos");
	    options.setAppActivity("com.pays.pos.ui.activities.MainActivity");

	    driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	    // Ensure the app starts
	 
		
		
			
 }

//	 @AfterClass
//	    public void teardown() {
//	        // Do not quit the driver here
//	        System.out.println("Test class completed.");
//	    }
//
//	    @AfterSuite
//	    public void suiteTearDown() {
//	        // Quit the driver after the entire suite is done
//	        if (driver != null) {
//	            driver.quit();
//	            System.out.println("Driver closed.");
//	        }
//	        if (service != null) {
//	            service.stop();
//	            System.out.println("Appium service stopped.");
//	        }
//	    }
	
	
	@AfterMethod
    public void tearDown() {
        // Capture logcat and attach to Allure report
        captureLogcatAndAttachToAllure();
    }

    protected void captureLogcatAndAttachToAllure() {
        String logcatFilePath = "logcat.txt"; // Path to save logcat logs

        try {
            // Capture logcat logs
            Process process = Runtime.getRuntime().exec("adb logcat -d > " + logcatFilePath);
            process.waitFor(); // Wait for the command to complete

            // Attach the logcat file to Allure report
            Path logcatPath = Paths.get(logcatFilePath);
            if (Files.exists(logcatPath)) {
                Allure.addAttachment("Logcat Logs", "text/plain", new FileInputStream(logcatPath.toFile()), "txt");
            } else {
                System.out.println("Logcat file not found: " + logcatFilePath);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to capture logcat logs: " + e.getMessage());
        }
    }
}
