package Pays.posfinaltesting.actions;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Pays.posfinaltest.pageobjectlocators.PaxConnectionPageObject;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PaxClass {
	  private AppiumDriver driver;
	  private WebDriverWait wait;
	  private PaxConnectionPageObject pax;
	    public PaxClass(AppiumDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        this.pax = new PaxConnectionPageObject(driver);// Explicit wait of 10 sec
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	    }
	    
//	  
	   //Method to click menu burger 
	    public void MenuBurgerclick() throws InterruptedException {
	    	 Thread.sleep(10);
	    	 WebElement sellCard = wait.until(ExpectedConditions.presenceOfElementLocated(
		                AppiumBy.androidUIAutomator(
		                        "new UiScrollable(new UiSelector().className(\"androidx.recyclerview.widget.RecyclerView\"))" +
		                        ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtCategoryName\").text(\"Sell Card\"))"
		                )
		        ));
	    	wait.until(ExpectedConditions.presenceOfElementLocated(
	    		     AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"com.pays.pos:id/imgDrawer\"]")
	    		)).click();   	
	    }
	    //method to click hardware button click
	    public void HardwareButtonClick() {
	        wait.until(ExpectedConditions.elementToBeClickable(pax.HardwareButton)).click();
	    }
	    //Method to click credit card click
	    public void CreditcardClick() {
	    	wait.until(ExpectedConditions.presenceOfElementLocated(
	    		     AppiumBy.id("com.pays.pos:id/txtCardMachine")
	    		)).click();  
	    }
	    //Method to connect to pax
	    public void Paxconnection() {
	    	try {
	    	    // Check if PAX is connected or disconnected
	    	    WebElement paxElement;
	    	    String paxStatus;
	    	    
	    	    try {
	    	        paxElement = wait.until(ExpectedConditions.presenceOfElementLocated(
	    	            AppiumBy.id("com.pays.pos:id/tvPax")
	    	        ));
	    	        paxStatus = paxElement.getText();
	    	    } catch (NoSuchElementException | WebDriverException e) {
	    	        // If "CONNECT TO PAX" is not found, check for "DISCONNECT TO PAX"
	    	        paxElement = wait.until(ExpectedConditions.presenceOfElementLocated(
	    	            AppiumBy.id("com.pays.pos:id/tvDisconnectPax")
	    	        ));
	    	        paxStatus = paxElement.getText();
	    	    }

	    	    System.out.println("PAX Status: " + paxStatus); // Debugging

	    	    if (paxStatus.equalsIgnoreCase("CONNECT TO PAX")) {
	    	        paxElement.click();

	    	        WebElement popUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
	    	            AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.LinearLayout\").instance(1)")
	    	        ));

	    	        Assert.assertTrue(popUpMessage.isDisplayed(), "Pop-up message is not displayed.");

	    	        String connectionMsg = wait.until(ExpectedConditions.presenceOfElementLocated(
	    	            AppiumBy.id("com.pays.pos:id/tvMessage")
	    	        )).getText();

	    	        Assert.assertTrue(connectionMsg.contains("connected successfully"), "Device Not Connected.");

	    	        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
	    	            AppiumBy.id("com.pays.pos:id/tvSave") // Replace with actual ID
	    	        ));
	    	        okButton.click();

	    	        wait.until(ExpectedConditions.elementToBeClickable(
	    	            AppiumBy.id("com.pays.pos:id/txtHome")
	    	        )).click();
	    	    } else if (paxStatus.equalsIgnoreCase("DISCONNECT PAX")) {
	    	        System.out.println("Device is already connected, navigating to home.");
	    	        wait.until(ExpectedConditions.elementToBeClickable(
	    	            AppiumBy.id("com.pays.pos:id/txtHome")
	    	        )).click();
	    	    } else {
	    	        System.out.println("Unexpected PAX status: " + paxStatus);
	    	    }
	    	} catch (NoSuchElementException e) {
	    	    System.out.println("Element not found: " + e.getMessage());
	    	} catch (WebDriverException e) {
	    	    System.out.println("WebDriver issue encountered: " + e.getMessage());
	    	} catch (Exception e) {
	    	    System.out.println("An unexpected error occurred: " + e.getMessage());
	    	}



	 
	    
	    }   
	   
}

