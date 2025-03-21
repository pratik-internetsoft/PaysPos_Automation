package Pays.posfinaltesting.actions;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PasscodeClass {
	private AppiumDriver driver;
	   private WebDriverWait wait;
	
	public PasscodeClass(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
	 @CacheLookup
	@AndroidFindBy(id="com.pays.pos:id/first")
	private WebElement firstbutton;
	
	
	public void  pass() {
		for(int i = 0;i<4;i++) {
			wait.until(ExpectedConditions.elementToBeClickable(firstbutton)).click();
		}
	}


}
