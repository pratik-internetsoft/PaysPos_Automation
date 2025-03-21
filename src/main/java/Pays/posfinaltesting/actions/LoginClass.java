package Pays.posfinaltesting.actions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginClass {
    private AppiumDriver driver;
    private WebDriverWait wait;

    // Constructor to initialize PageFactory elements
    public LoginClass(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // Locate email input field
    @CacheLookup
    @AndroidFindBy(id = "com.pays.pos:id/edtEmail")
    private WebElement usernameField;

    // Locate password input field
    @CacheLookup
    @AndroidFindBy(id = "com.pays.pos:id/edtPassword")
    private WebElement passwordField;

    @CacheLookup
    @AndroidFindBy(id= "com.pays.pos:id/txtSignIn")
    private WebElement loginButton;
    
    // Method to enter email
    public void enterEmail(String email) {
    	  wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(email);
    }

    // Method to enter password
    public void enterPassword(String password) {

    	 wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }

    // Method to click login button (if required)
    public void clickLogin() {
        loginButton.click();
    }

	public boolean LogintoApp() throws MalformedURLException, URISyntaxException, InterruptedException {
		// First, check if the user is already logged in
		if (isLoggedIn()) {
			System.out.println("User is already logged in.");
			return true;
		} else {
			System.out.println("User is not logged in. Attempting to log in...");
			try {
				// Perform login steps
			
				this.enterEmail("automation@yopmail.com");
				this.enterPassword("654321");
				this.clickLogin();
				return true;
			} catch (Exception e) {
				System.out.println("Login Failed: " + e.getMessage());
				return false;
			}
		}
		
	}

    public boolean isLoggedIn() {
    	try {
    		
            // Use a shorter timeout to check if the home element is present
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement homeElement = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(
                "//android.widget.TextView[@resource-id='com.pays.pos:id/txtTitle' and @text='Phone Order']"
            )));

            // Check if the home element is displayed
            if (homeElement.isDisplayed()) {
                System.out.println("Home element is displayed. User is logged in.");
                return true;
            } else {
                System.out.println("Home element is not displayed. User is not logged in.");
                return false;
            }
        }  catch (Exception e) {
            // If the element is not found at all, return false
            System.out.println("Home element not found. User is not logged in.");
            return false;
        }
    }
    public boolean Passcode() {
    	 if (isLoggedIn()) {
             System.out.println("User is already logged in.");
             return true;
         }else {
        	 PasscodeClass code = new PasscodeClass(driver);
     		code.pass();
     		return true;
         }
    }
}
