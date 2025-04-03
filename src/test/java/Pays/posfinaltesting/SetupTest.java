package Pays.posfinaltesting;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pays.posfinaltesting.actions.LoginClass;
import Pays.posfinaltesting.actions.SetupClass;
import Pays.posfinaltesting.actions.StayClass;
import Pays.posfinaltesting.actions.TransactionClass;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class SetupTest extends BaseTest {
	private SetupClass tips; // Instance of SetupClass for handling tip-related operations
	private WebDriverWait wait; // Explicit wait instance for handling waits
	private StayClass stay; // Instance of StayClass for handling stay-related operations
	public BigDecimal TipPercent; // Variable to store the tip percentage
	private TransactionClass tc; // Instance of TransactionClass for handling transactions
	private SetupClass tax;
	private SetupClass discount;
	private SetupClass orderNote;
	public 	BigDecimal beforeDiscountSubtotal;
	


	@BeforeMethod
	public void Login(ITestResult result) throws MalformedURLException, URISyntaxException, InterruptedException {
		String testName = result.getMethod().getMethodName();
	
	    // Execute login logic only for specific test cases
	    if (testName.matches("Setup(Tips|Taxes|Discount|ONote)_setup")) {
	        System.out.println("Executing login for test: " + testName);

	        LoginClass lg = new LoginClass(driver);
	        
	        boolean loginSuccess = lg.LogintoApp();
	        if (!loginSuccess) {
	            System.out.println("Login failed for test: " + testName);
	          
	            return; // Exit if login fails
	        }

	        boolean passcodeSuccess = lg.Passcode();
	        if (!passcodeSuccess) {
	            System.out.println("Passcode entry failed for test: " + testName);
	            return; // Exit if passcode entry fails
	        }

	        System.out.println("Login and Passcode successful for test: " + testName);
			}
	}
	

	@AfterMethod
	public void tearDown(ITestResult result) throws InterruptedException {
	    String testName = result.getMethod().getMethodName();

	    // Runs @AfterMethod only if "SetupTips_SelectPayment" executes
	    if (testName.equals("SetupTips_SelectPayment")) {
	        tips.MenuBurgerClick(); // Navigate to menu
	        tips.backtohome(); // Return to home screen
	    }
	}

	@Test
	public void SetupTips_setup() {
	    // Initializes required instances
	    tips = new SetupClass(driver);
	    stay = new StayClass(driver);
	    tc = new  TransactionClass(driver);
	    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@Test(dependsOnMethods = "SetupTips_setup")
	public void SetupTips_SelectTips() throws InterruptedException {
	    tips.MenuBurgerClick(); // Click on the menu
	    tips.SetupButton(); // Navigate to setup screen
	    tips.TipsButton(); // Select tips section
	}

	@Test(dependsOnMethods = "SetupTips_SelectTips")
	public void SetupTips_AddTipData() {
	    tips.AddNewTips(); // Add new tip
	    tips.SetTipvalues("GoodService", "15"); // Set tip values
	}

	@Test(dependsOnMethods = "SetupTips_AddTipData")
	public void SetupTips_CheckAddTipsLimit() {
	    boolean result = tips.CheckAddTipLimit(); // Check if tip limit is reached
	    System.out.println(result); // Print the result
	    tips.GoBack(); // Go back to the previous screen
	}

	@Test(dependsOnMethods = "SetupTips_CheckAddTipsLimit")
	public void SetupTips_DeleteTip() {
	    tips.DeleteTip(); // Delete a tip
	    tips.TipDeletionConfirmation(); // Confirm tip deletion
	}

	@Test(dependsOnMethods = "SetupTips_DeleteTip")
	public void SetupTips_AddNewTipData() {
	    tips.AddNewTips(); // Add another new tip
	    tips.SetTipvalues("GoodService", "15"); // Set tip values
	    tips.TipAdditionConfirmation(); // Confirm tip addition
	}

	@Test(dependsOnMethods = "SetupTips_AddNewTipData")
	public void SetupTips_Trannsaction_Home() {
	    tips.GoHome(); // Navigate back to home screen
	}

	@Test(dependsOnMethods = "SetupTips_Trannsaction_Home")
	public void SetupTips__Transaction_StayOrder() {
	    stay.StayButtonClick(); // Click on stay order button
	    int numItemsToAdd = 2; // Define number of items to select
	    boolean itemSelectionResult = stay.selectRandomItems(numItemsToAdd); // Select random items
	    Assert.assertTrue(itemSelectionResult, "Item is not selected"); // Verify item selection
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    stay.Paynow(); // Proceed to payment
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@Test(dependsOnMethods = "SetupTips__Transaction_StayOrder")
	public void SetupTips__Transaction_AddTip() {
	    tips.Click_AddTip(); // Click add tip button
	}

	@Test(dependsOnMethods = "SetupTips__Transaction_AddTip")
	public void SetupTips__Transaction_SelectTip() {
	    // Scroll to the tip element and select it
	    TipPercent = tips.scrollToElement("GoodService");
	    tips.ContinueTip(); // Continue after selecting tip
	}

	@Test(dependsOnMethods = "SetupTips__Transaction_SelectTip")
	public void SetupTips__Transaction_ValidateFinancialDetails() {
	    tc.validateAddtipFinancialDetails(TipPercent,beforeDiscountSubtotal); // Validate financial details after adding tip
	}

	@Test(dependsOnMethods = "SetupTips__Transaction_ValidateFinancialDetails")
	public void SetupTips__Transaction_SelectPayment() {
	    tc.Synergy(); // Execute payment process
	}

	
	 // ---------------------- TAXES TEST CASE ----------------------
	@Test
	public void SetupTaxes_setup() {
	    tax = new SetupClass(driver);
	    stay = new StayClass(driver);
	    tc = new TransactionClass(driver);
	    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@Test(dependsOnMethods = "SetupTaxes_setup")
	public void SetupTaxes_SelectTaxes() throws InterruptedException {
	    tax.MenuBurgerClick();
	    tax.SetupButton();
	    tax.TaxButton();
	}

	@Test(dependsOnMethods = "SetupTaxes_SelectTaxes")
	public void SetupTaxes_AddTax() {
	    tax.AddNewTax();
	}
	
	@Test(dependsOnMethods = "SetupTaxes_AddTax")	
	public void SetupTaxes_SetTax() throws InterruptedException {
		tax.SetTax("GST", "15");
	
		tax.SaveNewTax();
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		tax.TaxAdditionConfirmation();
	}
	
	@Test(dependsOnMethods = "SetupTaxes_SetTax")	
	public void SetupTaxes_EditTax() throws InterruptedException {
		tax.EditTax();
		tax.SetTax("Emp","8");
		tax.SaveNewTax();
		tax.TaxUpdationConfirmation();
	}
	
	@Test(dependsOnMethods = "SetupTaxes_EditTax")
	public void SetupTaxes_Transaction_Home() {
	    tax.GoHome(); // Navigate back to home screen
	}

	@Test(dependsOnMethods = "SetupTaxes_Transaction_Home")
	public void SetupTaxes_SetupTaxes_Transaction_StayOrder() {
	    stay.StayButtonClick(); // Click on stay order button
	    int numItemsToAdd = 2; // Define number of items to select
	    boolean itemSelectionResult = stay.selectRandomItems(numItemsToAdd); // Select random items
	    Assert.assertTrue(itemSelectionResult, "Item is not selected"); // Verify item selection
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    stay.Paynow(); // Proceed to payment
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}
	
	@Test(dependsOnMethods ="SetupTaxes_SetupTaxes_Transaction_StayOrder")
	public void SetupTaxes_Transaction_Check() {
		tax.CheckTax("Emp", "8.00%");
	}
	
	@Test(dependsOnMethods = "SetupTaxes_Transaction_Check")
	public void SetupTaxes_Transaction_ValidateFinancialDetails() {
	    tc.validateFinancialDetails(); // Validate financial details after adding tip
	}

	@Test(dependsOnMethods = "SetupTaxes_Transaction_ValidateFinancialDetails")
	public void SetupTaxes_Transaction_Pay() {
	    tc.Synergy(); 
	    // Execute payment process
	}
	
	@Test(dependsOnMethods = "SetupTaxes_Transaction_Pay")
	public void SetupTaxes_TearDown_DeleteTax() throws InterruptedException {
		tax.MenuBurgerClick();
		tax.SetupButton();
		tax.TaxButton();
		tax.DeleteTax();
		tax.GoHome(); 
	}
	
	
	 // ---------------------- DISCOUNT TEST CASE ----------------------
	@Test
	public void SetupDiscount_setup() {
	    discount = new SetupClass(driver);
	    stay = new StayClass(driver);
	    tc = new TransactionClass(driver);
	    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@Test(dependsOnMethods = "SetupDiscount_setup")
	public void SetupDiscount_SelectDiscount() throws InterruptedException {
	    discount.MenuBurgerClick();
	    discount.SetupButton();
	    discount.DiscountButton();
	}

	@Test(dependsOnMethods = "SetupDiscount_SelectDiscount")
	public void SetupDiscount_AddDiscountData() throws InterruptedException {
	    discount.AddNewDiscount();
	    discount.SetDiscountvalues("Family Discount", "15");
	    discount.DiscountAdditionConfirmation();
	}

	@Test(dependsOnMethods = "SetupDiscount_AddDiscountData")
	public void SetupDiscount_EditDiscount() throws InterruptedException {
	    discount.EditDiscount();
	    discount.SetDiscountvalues("Native Discount", "20");
	    discount.DiscountUpdateConfirmation();
	}

	@Test(dependsOnMethods = "SetupDiscount_EditDiscount")
	public void SetupDiscount_CheckDiscount() throws InterruptedException {
	    discount.CheckDiscount();
	}

	@Test(dependsOnMethods = "SetupDiscount_CheckDiscount")
	public void SetupDiscount_Home() {
	    discount.GoHome();
	}

	@Test(dependsOnMethods = "SetupDiscount_Home")
	public void SetupDiscount_Transaction_StayOrder() {
	    stay.StayButtonClick();
	    int numItemsToAdd = 1;
	    boolean itemSelectionResult = stay.selectRandomItems(numItemsToAdd);
	    Assert.assertTrue(itemSelectionResult, "Item is not selected");
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@Test(dependsOnMethods = "SetupDiscount_Transaction_StayOrder")
	public void SetupDiscount_AddDiscount() {
	    beforeDiscountSubtotal = discount.getSubtotal();
	    discount.AddDiscountButton();
	    discount.scrollToDiscountElement("Native Discount");
	    discount.SavePayMenu();
	}

	@Test(dependsOnMethods = "SetupDiscount_AddDiscount") 
	public void SetupDiscount_Transaction_Pay() {
	    stay.Paynow();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    tc.validateFinancialDetailsAfterDiscount(beforeDiscountSubtotal);
	    tc.Synergy();
	}

	@Test(dependsOnMethods = "SetupDiscount_Transaction_Pay")
	public void SetupDiscount_DeleteDiscount() throws InterruptedException {
	    discount.MenuBurgerClick();
	    discount.SetupButton();
	    discount.DiscountButton();
	    discount.DeleteDiscount("Native Discount");
	    discount.DiscountDeleteConfirmation();
	    discount.GoHome(); 
	}

	
	
	 // ---------------------- ORDER NOTE TEST CASE ----------------------
	@Test
	public void SetupONote_setup() {
		orderNote = new SetupClass(driver);
	    stay = new StayClass(driver);
	    tc = new TransactionClass(driver);
	    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	@Test(dependsOnMethods = "SetupONote_setup")
	public void SetupONote_SelectOrderNote() throws InterruptedException {
	    orderNote.MenuBurgerClick();
	    orderNote.SetupButton();
	    orderNote.OrderNoteButton();
	}
	@Test(dependsOnMethods ="SetupONote_SelectOrderNote")
	public void SetupONote_AddONoteData() throws InterruptedException {
	    orderNote.AddNewOrderNote();
	    orderNote.SetOrderNote("Extra Sweet");// Set tip values
	    orderNote.OrderNoteAdditonConfirmation();
	}
	@Test(dependsOnMethods ="SetupONote_AddONoteData")
	public void SetupONote_DragandDrop() throws Exception{
	 orderNote.DragandDropNote("Extra Sweet");
	 orderNote.OrderNotePositionConfirmation();
	}
	@Test(dependsOnMethods = "SetupONote_DragandDrop")
	public void SetupONote_Home() {
	    orderNote.GoHome(); // Navigate back to home screen
	}
	@Test(dependsOnMethods = "SetupONote_Home")
	public void SetupONote_Transaction_StayOrder() {
	    stay.StayButtonClick(); // Click on stay order button
	    int numItemsToAdd = 1; // Define number of items to select
	    boolean itemSelectionResult = stay.selectRandomItems(numItemsToAdd); // Select random items
	    Assert.assertTrue(itemSelectionResult, "Item is not selected"); // Verify item selection
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

	}
		
	@Test(dependsOnMethods = "SetupONote_Transaction_StayOrder")
	public void SetupONote_AddONote() {
		orderNote.AddONote();
		orderNote.SelectONote("Extra Sweet");
		boolean  checkONote =orderNote.CheckIfONoteIsAdded();
		Assert.assertTrue(checkONote,"Order Note is Not Added");
		orderNote.SavePayMenu();
	}
	
	@Test(dependsOnMethods = "SetupONote_AddONote") 
	public void SetupONote_Transaction_Pay() {
		stay.Paynow();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		tc.validateFinancialDetails();
		tc.Synergy();
	}
		
  
	@Test(dependsOnMethods = "SetupONote_Transaction_Pay")
	public void SetupONote_DeleteONote() throws InterruptedException {
		orderNote.MenuBurgerClick();
		orderNote.SetupButton();
	    orderNote.OrderNoteButton();
	    orderNote.DeleteONote("Extra Sweet");
	    orderNote.ONoteDeleteConfirmation();
	    orderNote.GoHome(); 
	}
}	