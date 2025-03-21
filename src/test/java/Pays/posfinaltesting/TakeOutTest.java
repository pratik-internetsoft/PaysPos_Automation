package Pays.posfinaltesting;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import Pays.posfinaltesting.actions.LoginClass;
import Pays.posfinaltesting.actions.OpenOrderClass;
import Pays.posfinaltesting.actions.PaxClass;
import Pays.posfinaltesting.actions.SetupClass;
import Pays.posfinaltesting.actions.TakeOutClass;
import Pays.posfinaltesting.actions.TransactionClass;

public class TakeOutTest extends BaseTest {
	private TakeOutClass takeout;
	private WebDriverWait wait; 
	private TransactionClass tc; 
	private SetupClass orderNote;
	private SetupClass discount;
	public 	BigDecimal beforeDiscountSubtotal;
	public BigDecimal TipPercent; 
	public BigDecimal beforeAppliedLoyalty ;
	
	@Epic("TakeOut Management") // High-level feature or module
	@Feature("Take Out") // Feature under test
	@Story("Create and Manage TakeOut Orders") 
	@BeforeMethod
	public void Login(ITestResult result) throws MalformedURLException, URISyntaxException, InterruptedException {
		String testName = result.getMethod().getMethodName();
	
	    // Execute login logic only for specific test cases
	    if (testName.matches("TakeOut_Setup")) {
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
	 @Test
	    @Description("Setup for Takeout Order")
	    @Severity(SeverityLevel.BLOCKER)
	    @Story("Takeout Setup")
	    @TmsLink("TC-001")
	    public void TakeOut_Setup() {
	        takeout = new TakeOutClass(driver);
	        orderNote = new SetupClass(driver);
	        tc = new TransactionClass(driver);
	        discount = new SetupClass(driver);
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    }

	    @Test(dependsOnMethods = "TakeOut_Setup")
	    @Description("Connect to Pax for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Pax Connection")
	    @TmsLink("TC-002")
	    public void TakeOut_Card_ConnecttoPax() throws InterruptedException, TimeoutException {
	        PaxClass pax = new PaxClass(driver); // Initialize Pax class for connection
	        pax.MenuBurgerclick(); // Click the menu burger button
	        pax.HardwareButtonClick(); // Click the hardware button
	        pax.CreditcardClick(); // Click the credit card machine option
	        pax.Paxconnection(); // Connect to Pax machine
	    }

	    @Test(dependsOnMethods = "TakeOut_Setup")
	    @Description("Select Takeout Option")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Selection")
	    @TmsLink("TC-003")
	    public void TakeOut_SelectTakeout() {
	        takeout.ClickTakeOut();
	    }

	    @Test(dependsOnMethods = "TakeOut_SelectTakeout")
	    @Description("Select Items for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Item Selection")
	    @TmsLink("TC-004")
	    public void TakeOut_ItemSelection() {
	        int numItemsToAdd = 2;
	        boolean itemSelectionResult = takeout.selectRandomItems(numItemsToAdd);
	        Assert.assertTrue(itemSelectionResult, "Item is not selected");
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    }

	    @Test(dependsOnMethods = "TakeOut_ItemSelection")
	    @Description("Add Manual Items to Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Manual Item Addition")
	    @TmsLink("TC-005")
	    public void TakeOut_AddManualItems() {
	        Boolean result = takeout.manualItem("Take Out");
	        Assert.assertTrue(result, "Manual Items Not added");
	    }

	    @Test(dependsOnMethods = "TakeOut_AddManualItems")
	    @Description("Update Item Quantity in Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Item Update")
	    @TmsLink("TC-006")
	    public void TakeOut_UpdateItem() {
	        Boolean result = takeout.updateItemQuantity();
	        Assert.assertTrue(result, "Items Not Updated");
	    }

	    @Test(dependsOnMethods = "TakeOut_UpdateItem")
	    @Description("Remove Item from Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Item Removal")
	    @TmsLink("TC-007")
	    public void TakeOut_RemoveItem() {
	        Boolean result = takeout.removeItem();
	        Assert.assertTrue(result, "Items Not Removed");
	    }

	    @Test(dependsOnMethods = "TakeOut_UpdateItem")
	    @Description("Save Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Order Save")
	    @TmsLink("TC-008")
	    public void TakeOut_SaveOrder() {
	        takeout.ClickOrderSave();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    }

	    @Test(dependsOnMethods = "TakeOut_SaveOrder")
	    @Description("Check Order Type for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Order Type Check")
	    @TmsLink("TC-009")
	    public void TakeOut_CheckOrderType() throws InterruptedException {
	        String OrderTypeText = takeout.getOrderTypeText();
	        Assert.assertTrue(OrderTypeText.equals("Open Order"), "The OrderType is not 'OpenOrder'");
	    }

	    @Test(dependsOnMethods = "TakeOut_CheckOrderType")
	    @Description("Update Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Order Update")
	    @TmsLink("TC-010")
	    public void Takeout_UpdateOrder() {
	        takeout.clickMainLayout();
	        takeout.UpdateOrder();
	    }

	    @Test(dependsOnMethods = "Takeout_UpdateOrder")
	    @Description("Add Order Note to Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Order Note Addition")
	    @TmsLink("TC-011")
	    public void TakeOut_AddOrderNote() {
	        orderNote.AddONote();
	        orderNote.SelectONote("Extra Spicy");
	        boolean checkONote = orderNote.CheckIfONoteIsAdded();
	        orderNote.SavePayMenu();
	    }

	    @Test(dependsOnMethods = "TakeOut_AddOrderNote")
	    @Description("Add Discount to Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Discount Addition")
	    @TmsLink("TC-012")
	    public void TakeOut_AddDiscount() {
	        beforeDiscountSubtotal = tc.getSubtotal();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        discount.AddDiscountButton();
	        discount.SelectPredefinedDiscount();
	        discount.SavePayMenu();
	    }

	    @Test(dependsOnMethods = "TakeOut_AddOrderNote")
	    @Description("Change Order Type for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Order Type Change")
	    @TmsLink("TC-013")
	    public void TakeOut_ChangeOrderType() {
	        boolean changeOrder = takeout.ChangeOrderType("Stay");
	        Assert.assertTrue(changeOrder, "Order Type not changed");
	        boolean Ordertype = takeout.ChangeOrderType("Take Out");
	        Assert.assertTrue(Ordertype, "Order Type not changed");
	    }

	    @Test(dependsOnMethods = "TakeOut_ChangeOrderType")
	    @Description("Add Customer to Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Customer Addition")
	    @TmsLink("TC-014")
	    public void TakeOut_AddCustomer() {
	        takeout.AddCustomer();
	        takeout.scrollToPhoneNumberAndClick("(292) 119-7050");
	    }

	    @Test(dependsOnMethods = "TakeOut_AddCustomer")
	    @Description("Apply Loyalty Points to Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Loyalty Points Application")
	    @TmsLink("TC-015")
	    public void TakeOut_AppliedLoyaltyPoint() {
	        BigDecimal LoyaltyBalance = tc.getLoyalty();
	        BigDecimal LoyaltyPoints = tc.getAppliedLoyaltyPoints();
	        tc.ApplyLoyalty();
	        boolean result = tc.ValidateloyaltyBalance(LoyaltyBalance, LoyaltyPoints);
	        Assert.assertTrue(result, "Loyalty failed");
	    }

	    @Test(dependsOnMethods = "TakeOut_AppliedLoyaltyPoint")
	    @Description("Perform Transaction for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Transaction")
	    @TmsLink("TC-016")
	    public void TakeOut_Transaction() {
	        tc.Paynow();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    }

	    @Test(dependsOnMethods = "TakeOut_Transaction")
	    @Description("Add Tip to Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Tip Addition")
	    @TmsLink("TC-017")
	    public void TakeOut_AddTip() {
	        tc.Click_AddTip();
	        TipPercent = BigDecimal.valueOf(20.00).divide(BigDecimal.valueOf(100));
	        tc.SelectTip();
	        tc.ContinueTip();
	        tc.validateAfterLoyaltyFinancialDetails(TipPercent, beforeDiscountSubtotal);
	    }

	    @Test(dependsOnMethods = "TakeOut_AddTip")
	    @Description("Perform Cash Refund Item-wise Payment for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Cash Refund Item-wise Payment")
	    @TmsLink("TC-018")
	    public void TakeOut_Cash_RefundItemWise_Payment() {
	        Boolean result = tc.performCashTransaction();
	        Assert.assertTrue(result, "Cash Transaction failed");
	    }

	    @Test(dependsOnMethods = "TakeOut_Cash_RefundItemWise_Payment")
	    @Description("Select Cash Refund Item-wise for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Cash Refund Item-wise Selection")
	    @TmsLink("TC-019")
	    public void TakeOut_Cash_RefundItemWise_SelectRefund() {
	        tc.selectHomeTransaction();
	        Boolean result = tc.cashRefundItemwise();
	        Assert.assertTrue(result, "Cash Item-wise Refund failed");
	    }

	    @Test(dependsOnMethods = "TakeOut_Cash_RefundItemWise_SelectRefund")
	    @Description("Print Receipt for Cash Refund Item-wise for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Cash Refund Item-wise Receipt Printing")
	    @TmsLink("TC-020")
	    public void TakeOut_Cash_RefundItemWise_PrintReceipt() {
	        tc.PrintCustomerReceipt();
	        tc.PrintKitchenReceipt();
	        tc.ClickHome();
	    }

	    @Test(dependsOnMethods = "TakeOut_Cash_RefundItemWise_PrintReceipt")
	    @Description("Check Cash Log for Cash Refund Item-wise for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Cash Refund Item-wise Cash Log Check")
	    @TmsLink("TC-021")
	    public void TakeOut_Cash_RefundItemWise_CheckCashLog() throws InterruptedException {
	        tc.MenuBurgerclick();
	        tc.CashLogButtonClick();
	        Boolean result = tc.ValidateCashLogRefund();
	        Assert.assertTrue(result, "Cash Log Result Not Same");
	        tc.ClickHome();
	    }

	    @Test(dependsOnMethods = "TakeOut_AddTip")
	    @Description("Perform Cash Full Refund Payment for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Cash Full Refund Payment")
	    @TmsLink("TC-022")
	    public void TakeOut_Cash_FullRefund_Payment() {
	        Boolean result = tc.performCashTransaction();
	        Assert.assertTrue(result, "Cash Transaction failed");
	    }

	    @Test(dependsOnMethods = "TakeOut_Cash_FullRefund_Payment")
	    @Description("Select Cash Full Refund for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Cash Full Refund Selection")
	    @TmsLink("TC-023")
	    public void TakeOut_Cash_FullRefund_SelectRefund() {
	        tc.selectHomeTransaction();
	        Boolean result = tc.cashRefund();
	        Assert.assertTrue(result, "Cash Refund failed");
	    }

	    @Test(dependsOnMethods = "TakeOut_Cash_FullRefund_SelectRefund")
	    @Description("Print Receipt for Cash Full Refund for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Cash Full Refund Receipt Printing")
	    @TmsLink("TC-024")
	    public void TakeOut_Cash_FullRefund_PrintReceipt() {
	        tc.PrintCustomerReceipt();
	        tc.PrintKitchenReceipt();
	        tc.ClickHome();
	    }

	    @Test(dependsOnMethods = "TakeOut_Cash_FullRefund_PrintReceipt")
	    @Description("Check Cash Log for Cash Full Refund for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Cash Full Refund Cash Log Check")
	    @TmsLink("TC-025")
	    public void TakeOut_Cash_FullRefund_CheckCashLog() throws InterruptedException {
	        tc.MenuBurgerclick();
	        tc.CashLogButtonClick();
	        Boolean result = tc.ValidateCashLogRefund();
	        Assert.assertTrue(result, "Cash Log Result Not Same");
	        tc.ClickHome();
	    }

	    @Test(dependsOnMethods = "TakeOut_AddTip")
	    @Description("Perform Card Final Way Payment for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Card Final Way Payment")
	    @TmsLink("TC-026")
	    public void TakeOut_Card_FinalWayPayment() {
	        Boolean result = tc.CardTransaction();
	        Assert.assertTrue(result, "Card Transaction failed");
	    }

	    @Test(dependsOnMethods = "TakeOut_Card_FinalWayPayment")
	    @Description("Select Card Refund for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Card Refund Selection")
	    @TmsLink("TC-027")
	    public void TakeOut_Card_Refund_SelectRefund() {
	        tc.selectHomeTransaction();
	        Boolean result = tc.cardRefund();
	        Assert.assertTrue(result, "Card Refund failed");
	    }

	    @Test(dependsOnMethods = "TakeOut_Card_Refund_SelectRefund")
	    @Description("Print Receipt for Card Refund for Takeout Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Story("Takeout Card Refund Receipt Printing")
	    @TmsLink("TC-028")
	    public void TakeOut_Card_Refund_PrintReceipt() {
	        tc.PrintCustomerReceipt();
	        tc.PrintKitchenReceipt();
	        tc.ClickHome();
	    }


       
	
}
