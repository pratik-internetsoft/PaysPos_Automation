package Pays.posfinaltesting;


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pays.posfinaltesting.actions.LoginClass;
import Pays.posfinaltesting.actions.PrinterClass;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("Printer ") // High-level feature or module
@Feature("Printing Management") // Feature under test
@Story("Create and Manage Print Orders") 
public class PrinterTest extends BaseTest {
	
    private PrinterClass printer;
    @BeforeMethod
	public void Login(ITestResult result) throws MalformedURLException, URISyntaxException, InterruptedException {
		String testName = result.getMethod().getMethodName();
	
	    // Execute login logic only for specific test cases
	    if (testName.matches("Printer_setup")) {
	        System.out.println("Executing login for test: " + testName);

	        LoginClass lg = new LoginClass(driver);
	        
	        boolean loginSuccess = lg.LogintoApp();
	        if (!loginSuccess) {
	            System.out.println("Login failed for test: " + testName);
	          
	            return; // Exit if login fails
	        }

	        boolean passcodeSuccess = lg.Passcode();
	        if (!passcodeSuccess) {
	            System.out.println("Passcode entry failed for test: " + testName);
	            return; // Exit if passcode entry fails
	        }

	        System.out.println("Login and Passcode successful for test: " + testName);
			}
	}
    @Test
    @Description("Initialize Printer Setup")
    @Step("Step 1: Printer setup initialization")
    public void Printer_setup() {
        printer = new PrinterClass(driver);
    }

    @Test(dependsOnMethods = "Printer_setup")
    @Description("Navigate through menu to access printer settings")
    @Step("Step 2: Access printer settings from menu")
    public void InnerPrinter() throws MalformedURLException, URISyntaxException, InterruptedException {
        printer.MenuBurgerClick();
        printer.HardwareButtonClick();
        printer.PrinterButtonClick();
    }

    @Test(dependsOnMethods = {"InnerPrinter"})
    @Description("Check if Inner Printer is available")
    @Step("Step 3: Verify if inner printer is detected")
    public void Check_InnerPrinter() throws InterruptedException {
        boolean innerPrinter = printer.isInnerPrinter();
        Assert.assertTrue(innerPrinter, "Inner printer is not detected");
    }

    @Test(dependsOnMethods = {"Check_InnerPrinter"})
    @Description("Click on the edit button for the printer")
    @Step("Step 4: Edit printer settings")
    public void ClickonEdit() {
        printer.ClickEditPrinter();
    }

    @Test(dependsOnMethods = {"ClickonEdit"})
    @Description("Change the printer usage settings")
    @Step("Step 5: Modify printer usage settings")
    public void ChangePrinterUse() {
        printer.ChangePrinterUsage();
        printer.chooseKitchenandCustomerPrinter();
        printer.Saveprinterchanges();
    }

    @Test(dependsOnMethods = {"ChangePrinterUse"})
    @Description("Verify that the inner printer is connected to kitchen printers")
    @Step("Step 6: Validate kitchen printer connection")
    @Severity(SeverityLevel.CRITICAL)
    public void CheckKitchenPrinter() {
        boolean KitchenInnerPrinter = printer.isKitchenInnerPrinter();
        Assert.assertTrue(KitchenInnerPrinter, "Inner Printer is not properly connected to Kitchen printers");
    }

    @AfterMethod
    @Step("Capture screenshot on test failure and close driver")
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test failed, ensuring screenshot is captured before closing driver.");
            try {
                Thread.sleep(2000); // Ensures the failed screen is captured before quitting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}



