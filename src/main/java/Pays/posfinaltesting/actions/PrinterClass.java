package Pays.posfinaltesting.actions;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Pays.posfinaltest.pageobjectlocators.PrinterConnectionPageobject;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

public class PrinterClass {
    private AppiumDriver driver;
    private WebDriverWait wait;
    private PrinterConnectionPageobject printer;
    // Constructor
    public PrinterClass(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
        this.printer = new PrinterConnectionPageobject(driver);// Explicit wait of 10 sec
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

  
    
    public void MenuBurgerClick() throws InterruptedException {
    	 Thread.sleep(10);  
    	 WebElement sellCard = wait.until(ExpectedConditions.presenceOfElementLocated(
	                AppiumBy.androidUIAutomator(
	                        "new UiScrollable(new UiSelector().className(\"androidx.recyclerview.widget.RecyclerView\"))" +
	                        ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtCategoryName\").text(\"Sell Card\"))"
	                )
	        ));
		  	wait.until(ExpectedConditions.visibilityOfElementLocated(
	   		     AppiumBy.id("com.pays.pos:id/imgDrawer")
	   		)).click();
    }
    


    public void HardwareButtonClick() {
        wait.until(ExpectedConditions.elementToBeClickable(printer.HardwareButton)).click();
    }



    public void PrinterButtonClick() {
        wait.until(ExpectedConditions.elementToBeClickable(printer.Printer_Button)).click();
    }

    public boolean isInnerPrinter() throws InterruptedException {
    	try {
            // Wait for RecyclerView
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.pays.pos:id/rvCustomerPrinter\")")
            ));

            // Wait for the first LinearLayoutCompat inside RecyclerView
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCustomerPrinter']/androidx.appcompat.widget.LinearLayoutCompat[1]")
            ));

            // Wait for and fetch `txtPrinterName` inside the first LinearLayoutCompat
            WebElement printerNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCustomerPrinter']/androidx.appcompat.widget.LinearLayoutCompat[1]//android.widget.TextView[@resource-id='com.pays.pos:id/txtPrinterName']")
            ));

            return printerNameElement.getText().equals("InnerPrinter");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
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
   
   
   public void ClickEditPrinter() {
	   wait.until(ExpectedConditions.elementToBeClickable(printer.EditPrinter)).click();
	}

   
   public void ChangePrinterUsage() {

	   WebElement changePrinterUsageButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
	           AppiumBy.id("com.pays.pos:id/spnPrinterCat"))); // Replace with the correct locator
	   changePrinterUsageButton.click();	   
   }
   
   public void chooseKitchenandCustomerPrinter() {
	   String[] printerType = {"Kitchen","Customer","KitchenAndCustomer"};
	   
	   WebElement KitchenandCustomerprinter = wait.until(ExpectedConditions.elementToBeClickable(
	           AppiumBy.xpath("//android.widget.CheckedTextView[@resource-id=\"android:id/text1\" and @text=\"KitchenAndCustomer\"]")));
	   KitchenandCustomerprinter.click();
	   
   }
  
   public void Saveprinterchanges() {
	   wait.until(ExpectedConditions.elementToBeClickable(printer.Save)).click();
	   wait.until(ExpectedConditions.visibilityOf(printer.customFrameLayout));  // Ensure the FrameLayout is visible
       wait.until(ExpectedConditions.elementToBeClickable(printer.saveButton)).click();
	    
   }
  
   public boolean isKitchenInnerPrinter() {
       try {
           // Wait for RecyclerView
           wait.until(ExpectedConditions.visibilityOfElementLocated(
               AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.pays.pos:id/rvKitchenPrinter\")")
           ));

           // Wait for the first LinearLayoutCompat inside RecyclerView
           wait.until(ExpectedConditions.visibilityOfElementLocated(
               AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.pays.pos:id/rvKitchenPrinter\"]/androidx.appcompat.widget.LinearLayoutCompat[1]")
           ));

           // Wait for and fetch `txtPrinterName` inside the first LinearLayoutCompat
           WebElement printerNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
               AppiumBy.xpath("//android.widget.TextView[@resource-id='com.pays.pos:id/txtPrinterName' and @text='InnerPrinter']\r\n"
               		+ "")
           ));

           return printerNameElement.getText().equals("InnerPrinter");
       } catch (Exception e) {
           System.out.println("Error: " + e.getMessage());
           return false;
       }
   }
   

   
   public void printAllAvailablePrinters() {
	    try {
	        // Get all printer elements in the RecyclerView
	        List<WebElement> printerElements = driver.findElements(AppiumBy.xpath(
	            "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvAvailablePrinter']" +
	            "/androidx.appcompat.widget.LinearLayoutCompat/android.widget.TextView[@resource-id='com.pays.pos:id/txtPrinterName']"
	        ));

	        // Iterate through each printer
	        for (WebElement printer : printerElements) {
	            String printerName = printer.getText();
	            WebElement parentLayout = printer.findElement(AppiumBy.xpath("./parent::*"));

	            // Find the switch button inside the same layout
	            WebElement switchButton = parentLayout.findElement(AppiumBy.id("com.pays.pos:id/swtOrderId"));

	            System.out.println("Printer: " + printerName + " | Switch Found: " + (switchButton != null));
	        }

	    } catch (Exception e) {
	        System.out.println("Error retrieving printer list: " + e.getMessage());
	    }
	}



   
}


