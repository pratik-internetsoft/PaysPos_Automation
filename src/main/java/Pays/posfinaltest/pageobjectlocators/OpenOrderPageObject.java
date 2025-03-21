package Pays.posfinaltest.pageobjectlocators;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class OpenOrderPageObject {
	 private AppiumDriver driver;
	    private WebDriverWait wait;
	 
		public OpenOrderPageObject(AppiumDriver driver) {
			this.driver = driver;
			PageFactory.initElements(new AppiumFieldDecorator(driver), this); // Initialize PageFactory elements

		}
		@CacheLookup
		@AndroidFindBy(xpath="//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtTitle\" and @text=\"Open Order\"]")
		public WebElement OpenOrder;
	//// androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCategory']/androidx.appcompat.widget.LinearLayoutCompat

			@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCategory']/androidx.appcompat.widget.LinearLayoutCompat")
			public List<WebElement> categoryList;
			// android.widget.LinearLayout[@resource-id='com.pays.pos:id/linear_item']

			@AndroidFindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.pays.pos:id/linear_item']")
			public List<WebElement> itemList;
			// (//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.pays.pos:id/llMain'])
	
			@AndroidFindBy(xpath = "(//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.pays.pos:id/llMain'])")
			public List<WebElement> modifierList;
		//// android.widget.TextView[@resource-id='com.pays.pos:id/txtDone']
	        
			@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.pays.pos:id/txtDone']")
			public WebElement doneButton;
	     
			@AndroidFindBy(id = "com.pays.pos:id/txtKeypad")
		    public WebElement keypad;

		    @AndroidFindBy(xpath= "//android.widget.EditText[@resource-id=\"com.pays.pos:id/edtItemName\"]")
		    public WebElement customItemName;

		    @AndroidFindBy(id = "com.pays.pos:id/imgAdd")
		    public WebElement imgAdd;

		    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtSave\"]")
		    public WebElement txtsave;
		    // Locators
		  
		    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.pays.pos:id/rvCartList\"]/androidx.appcompat.widget.LinearLayoutCompat/android.widget.LinearLayout")
		    public WebElement cartQty;

		    @AndroidFindBy(id = "com.pays.pos:id/imgPlus")
		    public WebElement plusButton;


		    @AndroidFindBy(id = "com.pays.pos:id/txtQuantity")
		    public WebElement quantityText;
		    

		    @AndroidFindBy(id = "com.pays.pos:id/txtAddNote")
		    public WebElement addItemNote;
		    
		    @AndroidFindBy(id="com.pays.pos:id/txtRemoveItem")
		    public WebElement deleteItem;
		    
		    @AndroidFindBy(id="com.pays.pos:id/txtAddCustomer")
		    public WebElement AddCustomerButton;
		    
		    @AndroidFindBy(id="com.pays.pos:id/orderTypeDisplay")
		    public WebElement OrderDisplay;
 
		    @AndroidFindBy(xpath ="//android.widget.FrameLayout[@resource-id='android:id/content']/androidx.appcompat.widget.LinearLayoutCompat/androidx.appcompat.widget.LinearLayoutCompat")
		    public WebElement OrderTypeLinearLayout;
			// com.pays.pos:id/tvSave
			@AndroidFindBy(id = "com.pays.pos:id/tvSave")
			public WebElement OrderSave;
			@AndroidFindBy(xpath ="(//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtOrderType\"])[1]")
			public WebElement OrderTypeSaved;
			@AndroidFindBy(xpath = "(//android.widget.LinearLayout[@resource-id='com.pays.pos:id/llMainLayout'])[1]")
			public WebElement mainLayout;
			// com.pays.pos:id/txtEditOrder
			@AndroidFindBy(id = "com.pays.pos:id/txtEditOrder")
			public WebElement updateOrder;

}
