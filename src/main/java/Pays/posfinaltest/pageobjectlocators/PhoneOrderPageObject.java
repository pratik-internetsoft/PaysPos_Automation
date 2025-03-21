package Pays.posfinaltest.pageobjectlocators;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PhoneOrderPageObject {
	 private AppiumDriver driver;
	    private WebDriverWait wait;
	 
		public PhoneOrderPageObject(AppiumDriver driver) {
			this.driver = driver;
			PageFactory.initElements(new AppiumFieldDecorator(driver), this); // Initialize PageFactory elements

		}
		// android.widget.TextView[@resource-id='com.pays.pos:id/txtTitle' and @text='Phone Order']
		@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.pays.pos:id/txtTitle' and @text='Phone Order']")
		public WebElement PhoneOrderButton;

		// com.pays.pos:id/edtFName
		@AndroidFindBy(id = "com.pays.pos:id/edtFName")
		public WebElement Firstname;

		// com.pays.pos:id/edtLName
		@AndroidFindBy(id = "com.pays.pos:id/edtLName")
		public WebElement Surname;

		// com.pays.pos:id/edtPhoneNo
		@AndroidFindBy(id = "com.pays.pos:id/edtPhoneNo")
		public WebElement PhoneNumber;

		// com.pays.pos:id/edtEmail
		@AndroidFindBy(id = "com.pays.pos:id/edtEmail")
		public WebElement EmailID;

		// com.pays.pos:id/txtNext
		@AndroidFindBy(id = "com.pays.pos:id/txtNext")
		public WebElement PhoneOrderNext;

		@CacheLookup
		@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCategory']/androidx.appcompat.widget.LinearLayoutCompat")
		public List<WebElement> categoryList;

		// android.widget.LinearLayout[@resource-id='com.pays.pos:id/linear_item']

		
		@AndroidFindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.pays.pos:id/linear_item']")
		public List<WebElement> itemList;

		// (//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.pays.pos:id/llMain'])

	
		@AndroidFindBy(xpath = "(//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.pays.pos:id/llMain'])")
		public List<WebElement> modifierList;

		// android.widget.TextView[@resource-id='com.pays.pos:id/txtDone']

		@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.pays.pos:id/txtDone']")
		public WebElement doneButton;

		// com.pays.pos:id/tvSave
		@AndroidFindBy(id = "com.pays.pos:id/tvSave")
		public WebElement OrderSave;

		// android.widget.TextView[@resource-id='com.pays.pos:id/txtCategoryName' and @text='cat2']
		@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.pays.pos:id/txtCategoryName' and @text='cat2']")
		public WebElement cat2Category;

		// com.pays.pos:id/txtEditOrder
		@AndroidFindBy(id = "com.pays.pos:id/txtEditOrder")
		public WebElement updateOrder;

		// (//android.widget.TextView[@resource-id='com.pays.pos:id/txtDeliveryOrPickup'])[1]
		@AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id='com.pays.pos:id/txtDeliveryOrPickup'])[1]")
		public WebElement deliveryOrPickupTextView;

		// (//android.widget.LinearLayout[@resource-id='com.pays.pos:id/llMainLayout'])[1]
		@AndroidFindBy(xpath = "(//android.widget.LinearLayout[@resource-id='com.pays.pos:id/llMainLayout'])[1]")
		public WebElement mainLayout;

		// com.pays.pos:id/tvPayNow
		@CacheLookup
		@AndroidFindBy(id = "com.pays.pos:id/tvPayNow")
		public WebElement Pay;

		/// Delivery Locators and methods
	
		@AndroidFindBy(id = "com.pays.pos:id/txtDelivery")
		public WebElement delivery;

		// com.pays.pos:id/txtCancelOrder
		@AndroidFindBy(id = "com.pays.pos:id/txtCancelOrder")
		public WebElement CancelOrderButton;

		// Print Customer unpaid Receipt
		// com.pays.pos:id/txtCustomerReceipt
		@AndroidFindBy(id = "com.pays.pos:id/txtCustomerReceipt")
		public WebElement PrintCustomerReceipt;
		@AndroidFindBy(id = "com.pays.pos:id/txtKeypad")
	    public WebElement keypad;
		 @AndroidFindBy(xpath= "//android.widget.EditText[@resource-id=\"com.pays.pos:id/edtItemName\"]")
		    public WebElement customItemName;

		    @AndroidFindBy(id = "com.pays.pos:id/imgAdd")
		    public WebElement imgAdd;

		    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtSave\"]")
		    public WebElement txtsave;
		    
		    @AndroidFindBy(id="com.pays.pos:id/etSearch")
		    public WebElement SearchCustomerButton;
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
}
