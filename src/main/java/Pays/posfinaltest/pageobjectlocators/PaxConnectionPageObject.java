package Pays.posfinaltest.pageobjectlocators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PaxConnectionPageObject {
	private AppiumDriver driver;
    private WebDriverWait wait;
    public  PaxConnectionPageObject(AppiumDriver driver) {
	this.driver = driver;
	PageFactory.initElements(new AppiumFieldDecorator(driver), this); // Initialize PageFactory elements

}
 // driver.findElement(AppiumBy.id("com.pays.pos:id/imgDrawer"))
    @AndroidFindBy(id = "com.pays.pos:id/imgDrawer")
    public WebElement MenuBurgerButton;

    // driver.findElement(AppiumBy.id("com.pays.pos:id/linearHardware"))
    @AndroidFindBy(id = "com.pays.pos:id/linearHardware")
    public WebElement HardwareButton;

    // driver.findElement(AppiumBy.id("com.pays.pos:id/txtCardMachine"))
    @AndroidFindBy(id = "com.pays.pos:id/txtCardMachine")
    public WebElement CreditCard_Button;

    // driver.findElement(AppiumBy.id("com.pays.pos:id/tvPax"))
    @AndroidFindBy(id = "com.pays.pos:id/tvPax")
    public WebElement ConnecttoPax;

    // driver.findElement(AppiumBy.id("com.pays.pos:id/tvMessage"))
    @AndroidFindBy(id = "com.pays.pos:id/tvMessage")
    public WebElement ConnectionMessage;

    // driver.findElement(AppiumBy.id("com.pays.pos:id/txtHome"))
    @AndroidFindBy(id = "com.pays.pos:id/txtHome")
    public WebElement HomeButton;

}
