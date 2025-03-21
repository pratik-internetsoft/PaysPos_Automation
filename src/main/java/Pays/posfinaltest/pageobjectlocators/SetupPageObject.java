package Pays.posfinaltest.pageobjectlocators;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SetupPageObject {
	 private AppiumDriver driver;
	    private WebDriverWait wait;
	 
		public SetupPageObject(AppiumDriver driver) {
			this.driver = driver;
			PageFactory.initElements(new AppiumFieldDecorator(driver), this); // Initialize PageFactory elements

		}
	   
		 // ---------------------- DISCOUNT Locators ----------------------
	    
	    @AndroidFindBy(xpath ="//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtTitle\" and @text=\"Discounts\"]")
	    public WebElement DiscountButton;
	    // ---------------------- ORDER NOTE Locators ----------------------
	    @AndroidFindBy(xpath ="//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtTitle\" and @text=\"Order Notes\"]")
	    public WebElement OrderNoteButton;
	    
	    @AndroidFindBy(id= "com.pays.pos:id/txtCreateNote")
	    public WebElement AddOrderNote;
	    
	    @AndroidFindBy(xpath = "//android.widget.EditText[@text=\"Note Title*\"]")
	    public WebElement EditNoteBox;
	    
	    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc=\"Menu\"]")
	    public WebElement PaymentMenu;
	    
	    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.pays.pos:id/title\" and @text=\"Add Discount\"]")
	    public WebElement SelectAddDiscount;
	    
	    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.pays.pos:id/title\" and @text=\"Add Order Note\"] " )
	    public WebElement SelectAddONote;
	    
	    @AndroidFindBy(id="com.pays.pos:id/txt20")
	    public WebElement TwentyPercentDiscount;
	    
	    @AndroidFindBy(xpath ="(//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/content\"])[3]")
	    public WebElement SelectPhoneOrderAddONote;
}