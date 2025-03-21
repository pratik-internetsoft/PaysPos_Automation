package Pays.posfinaltest.pageobjectlocators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PrinterConnectionPageobject {
	 private AppiumDriver driver;
	    private WebDriverWait wait;
	public  PrinterConnectionPageobject(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this); // Initialize PageFactory elements

	}
	  @AndroidFindBy(id = "com.pays.pos:id/imgDrawer")
	    public WebElement MenuBurgerButton;
	    @AndroidFindBy(id = "com.pays.pos:id/linearHardware")
	    public WebElement HardwareButton;
	    @AndroidFindBy(id = "com.pays.pos:id/txtPrinter")
	    public WebElement Printer_Button;
	    @AndroidFindBy(id="com.pays.pos:id/imgEdit")
	    public WebElement EditPrinter;
	    @AndroidFindBy(id="com.pays.pos:id/txtSave")
	    public WebElement Save;
	    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/custom']/android.widget.LinearLayout")
	    public WebElement customFrameLayout;

	    @AndroidFindBy(id = "com.pays.pos:id/tvSave")
	    public WebElement saveButton;

	    @AndroidFindBy(id="//com.pays.pos:id/spnPrinterCat")
	    public WebElement ChangeprinterUsage;
}
