package Pays.posfinaltest.pageobjectlocators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Categories_ItemsPageObject {
	 private AppiumDriver driver;
	    private WebDriverWait wait;
	 
		public Categories_ItemsPageObject(AppiumDriver driver) {
			this.driver = driver;
			PageFactory.initElements(new AppiumFieldDecorator(driver), this); // Initialize PageFactory elements

		}
		@AndroidFindBy(id = "com.pays.pos:id/imgDrawer")
	    public WebElement MenuBurgerButton; // Menu burger button to open the navigation drawer

	    @AndroidFindBy(id = "com.pays.pos:id/linearInventory")
	    public WebElement MenuButton; // Menu button to access inventory options

	    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.pays.pos:id/txt_title' and @text='Categories']")
	    public WebElement CategoriesButton; // Button to navigate to the "Categories" section

	    @AndroidFindBy(id = "com.pays.pos:id/txtCreatecatagory")
	    public WebElement createCategory; // Button to create a new category

	    @AndroidFindBy(id = "com.pays.pos:id/etCategoryName")
	    public WebElement AddCategoryName; // Input field to enter a new category name

	    @AndroidFindBy(id = "com.pays.pos:id/txtSave")
	    public WebElement SaveButton; // Button to save the new category

	    @AndroidFindBy(id = "com.pays.pos:id/txtCreateItem")
	    public WebElement AddItem; // Button to add a new item

	    @AndroidFindBy(id = "com.pays.pos:id/txtDone")
	    public WebElement ItemDone; // Button to confirm item addition

	    @AndroidFindBy(id = "com.pays.pos:id/etItemPrice")
	    public WebElement ItemPrice; // Input field to set item price

	    @AndroidFindBy(id = "com.pays.pos:id/etStock")
	    public WebElement Quantity; // Input field to enter item quantity

	    @AndroidFindBy(id = "com.pays.pos:id/tvPayNow")
	    public WebElement Pay; // Button to proceed with payment
	    
	    @AndroidFindBy(id = "com.pays.pos:id/txtcreatemodifieer")
	    public WebElement CreateModifier;
	    
	    @AndroidFindBy(id = "android:id/text1")
	    public WebElement SelectModifier; 
}
