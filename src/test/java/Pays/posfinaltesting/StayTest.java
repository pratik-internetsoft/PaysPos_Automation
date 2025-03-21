package Pays.posfinaltesting;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pays.posfinaltesting.actions.LoginClass;
import Pays.posfinaltesting.actions.PaxClass;
import Pays.posfinaltesting.actions.SetupClass;
import Pays.posfinaltesting.actions.StayClass;
import Pays.posfinaltesting.actions.TransactionClass;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Stay Order Management")  // High-Level Module
@Feature("Order Processing and Refunds")
public class StayTest extends BaseTest {
	
	  private StayClass stayOrder;
 
		private WebDriverWait wait; 
		private TransactionClass tc; 
		private SetupClass orderNote;
		private SetupClass discount;
		public 	BigDecimal beforeDiscountSubtotal;
		public BigDecimal TipPercent; 
		
	  @BeforeMethod
	  public void Login(ITestResult result) throws MalformedURLException, URISyntaxException, InterruptedException {
			String testName = result.getMethod().getMethodName();
		
		    // Execute login logic only for specific test cases
		    if (testName.matches("StayOrder_Setup")) {
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
	    @Feature("Stay Order Management")
	    @Story("Setup Stay Order")
	    @Severity(SeverityLevel.BLOCKER)
	    @Description("Initial setup of Stay Order, instantiating required classes and setting up wait time.")
	    public void StayOrder_Setup() {
	        stayOrder = new StayClass(driver);
	        orderNote = new SetupClass(driver);
	        tc = new TransactionClass(driver);
	        discount = new SetupClass(driver);
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    }

	    @Test(dependsOnMethods="StayOrder_Setup")
	    @Feature("Payment Integration")
	    @Story("Connect Card to Pax")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Connecting credit card to Pax device for payment processing.")
	    public void StayOrder_Card_ConnecttoPax() throws InterruptedException, TimeoutException {
	        PaxClass pax = new PaxClass(driver);
	        pax.MenuBurgerclick();
	        pax.HardwareButtonClick();
	        pax.CreditcardClick();
	        pax.Paxconnection();
	    }

	    @Test(dependsOnMethods="StayOrder_Setup")
	    @Feature("Order Management")
	    @Story("Select Stay Order")
	    @Severity(SeverityLevel.BLOCKER)
	    @Description("Selecting Stay Order from available order types.")
	    public void StayOrder_SelectStayOrder() {
	        stayOrder.StayButtonClick();
	    }

	    @Test(dependsOnMethods="StayOrder_SelectStayOrder")
	    @Feature("Item Management")
	    @Story("Item Selection")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Selecting random items for Stay Order.")
	    public void StayOrder_ItemSelection() {
	        int numItemsToAdd = 4;
	        boolean itemSelectionResult = stayOrder.selectRandomItems(numItemsToAdd);
	        Assert.assertTrue(itemSelectionResult, "Item is not selected");
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    }

	    @Test(dependsOnMethods="StayOrder_ItemSelection")
	    @Feature("Item Management")
	    @Story("Adding Manual Items")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Manually adding an item to Stay Order.")
	    public void StayOrder_AddManualItems() {
	        Boolean result = stayOrder.manualItem("Take Out");
	        Assert.assertTrue(result, "Manual Items Not added");
	    }

	    @Test(dependsOnMethods="StayOrder_AddManualItems")
	    @Feature("Item Management")
	    @Story("Updating Item Quantity")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Updating the quantity of selected items.")
	    public void StayOrder_UpdateItem() {
	        Boolean result = stayOrder.updateItemQuantity();
	        Assert.assertTrue(result, "Items Not Updated");
	    }

	    @Test(dependsOnMethods="StayOrder_UpdateItem")
	    @Feature("Item Management")
	    @Story("Removing Items")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Removing selected items from the order.")
	    public void StayOrder_RemoveItem() {
	        Boolean result = stayOrder.removeItem();
	        Assert.assertTrue(result, "Items Not Removed");
	    }

	    @Test(dependsOnMethods="StayOrder_RemoveItem")
	    @Feature("Order Management")
	    @Story("Saving Order")
	    @Severity(SeverityLevel.BLOCKER)
	    @Description("Saving the Stay Order after item selection.")
	    public void StayOrder_SaveOrder() {
	        stayOrder.ClickOrderSave();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    }

	    @Test(dependsOnMethods="StayOrder_SaveOrder")
	    @Feature("Order Verification")
	    @Story("Checking Order Type")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Verifying if the order type is correctly set to 'Stay Order'.")
	    public void StayOrder_CheckOrderType() {
	        String OrderTypeText = stayOrder.getOrderTypeText();
	        System.out.println(OrderTypeText);
	        Assert.assertTrue(OrderTypeText.equals("Open Order"), "The OrderType is not 'Open Order'");
	    }

	    @Test(dependsOnMethods="StayOrder_CheckOrderType")
	    @Feature("Order Modification")
	    @Story("Updating Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Making updates to the Stay Order.")
	    public void StayOrder_UpdateOrder() {
	        stayOrder.clickMainLayout();
	        stayOrder.UpdateOrder();
	    }

	    @Test(dependsOnMethods="StayOrder_UpdateOrder")
	    @Feature("Order Customization")
	    @Story("Adding Order Notes")
	    @Severity(SeverityLevel.MINOR)
	    @Description("Adding special instructions or notes to Stay Order.")
	    public void StayOrder_AddOrderNote() {
	        orderNote.AddONote();
	        orderNote.SelectONote("Extra Spicy");
	        boolean checkONote = orderNote.CheckIfONoteIsAdded();
	        orderNote.SavePayMenu();
	    }

	    @Test(dependsOnMethods="StayOrder_AddOrderNote")
	    @Feature("Discount Management")
	    @Story("Applying Discount")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Applying a predefined discount to Stay Order.")
	    public void StayOrder_AddDiscount() {
	        beforeDiscountSubtotal = discount.getSubtotal();
	        discount.AddDiscountButton();
	        discount.SelectPredefinedDiscount();
	        discount.SavePayMenu();
	    }

	    @Test(dependsOnMethods="StayOrder_AddDiscount")
	    @Feature("Order Type Management")
	    @Story("Changing Order Type")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Changing Stay Order type to 'Take Out' and then back to 'Stay Order'.")
	    public void StayOrder_ChangeOrderType() {
	        boolean changeOrder = stayOrder.ChangeOrderType("Take Out");
	        Assert.assertTrue(changeOrder, "Items Not Removed");
	        boolean Ordertype = stayOrder.ChangeOrderType("Stay");
	        Assert.assertTrue(Ordertype, "Items Not Removed");
	    }

	    @Test(dependsOnMethods="StayOrder_ChangeOrderType")
	    @Feature("Customer Management")
	    @Story("Adding Customer to Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Attaching a customer to the Stay Order.")
	    public void StayOrder_AddCustomer() {
	        stayOrder.AddCustomer();
	        stayOrder.scrollToPhoneNumberAndClick("(292) 119-7050");
	    }

	    @Test(dependsOnMethods = "StayOrder_AddCustomer")
	    @Feature("Loyalty Program")
	    @Story("Applying Loyalty Points")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Applying and validating loyalty points for Stay Order.")
	    public void StayOrder_Loyalty_AppliedLoyaltyPoint() {
	        BigDecimal LoyaltyBalance = tc.getLoyalty();
	        BigDecimal LoyaltyPoints = tc.getAppliedLoyaltyPoints();
	        tc.ApplyLoyalty();
	        boolean result = tc.ValidateloyaltyBalance(LoyaltyBalance, LoyaltyPoints);
	        Assert.assertTrue(result, "Loyalty failed");
	    }

	    @Test(dependsOnMethods="StayOrder_AddCustomer")
	    @Feature("Transaction Processing")
	    @Story("Finalizing Transaction")
	    @Severity(SeverityLevel.BLOCKER)
	    @Description("Completing the transaction for Stay Order.")
	    public void StayOrder_Transaction() {
	        tc.Paynow();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	    }

	    @Test(dependsOnMethods="StayOrder_Transaction")
	    @Feature("Split Payment & Refund Management")
	    @Story("Initiate Split Payment")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Validates financial details after discount and initiates a split payment method.")
	    public void StayOrder_Split_SelectSplit() {
	        tc.validateFinancialDetailsAfterDiscount(beforeDiscountSubtotal);
	        tc.SelectSplitWisePayment();
	        tc.SelectTwoWay();
	        tc.ValidateSplitPayment();
	        tc.SelectNextSplit();
	    }

	    @Test(dependsOnMethods="StayOrder_Split_SelectSplit")
	    @Feature("Split Payment & Refund Management")
	    @Story("Select Cash Payment in Split")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Selects cash payment as one of the split payment methods.")
	    public void StayOrder_Split_SelectCash() {
	        tc.SelectCashButton();
	    }

	    @Test(dependsOnMethods="StayOrder_Split_SelectCash")
	    @Feature("Split Payment & Refund Management")
	    @Story("Proceed to Next Payment Split")
	    @Severity(SeverityLevel.MINOR)
	    @Description("Moves to the next split payment method after selecting cash.")
	    public void StayOrder_Split_SelectNextPayment() {
	        tc.SelectNextPayment();
	    }

	    @Test(dependsOnMethods="StayOrder_Split_SelectNextPayment")
	    @Feature("Split Payment & Refund Management")
	    @Story("Complete Final Split Payment")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Processes the final payment method in the split transaction.")
	    public void StayOrder_Split_FinalWayPayment() {
	        tc.performCashTransaction();
	    }

	    @Test(dependsOnMethods="StayOrder_Split_FinalWayPayment")
	    @Feature("Refund Management")
	    @Story("Select Refund after Split Payment")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Initiates a refund for a split payment transaction.")
	    public void StayOrder_Split_Refund_SelectRefund() {
	        tc.selectHomeTransaction();
	        Boolean result = tc.cashRefund();
	        Assert.assertTrue(result, "Failure");
	    }

	    @Test(dependsOnMethods ="StayOrder_Split_Refund_SelectRefund")
	    @Feature("Refund Management")
	    @Story("Print Receipts after Refund")
	    @Severity(SeverityLevel.MINOR)
	    @Description("Prints both customer and kitchen receipts after the refund process.")
	    public void StayOrder_Split_Refund_PrintReceipt() {
	        tc.PrintCustomerReceipt();
	        tc.PrintKitchenReceipt();
	        tc.ClickHome();
	    }


	    @Test(dependsOnMethods="StayOrder_Transaction")
	    @Feature("Tip & Refund Management")
	    @Story("Add Tip to Stay Order")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Adds a 20% tip to the order and validates the updated financial details.")
	    public void StayOrder_Tip_AddTip() {
	       tc.Click_AddTip();
	       TipPercent = BigDecimal.valueOf(20.00).divide(BigDecimal.valueOf(100));
	       tc.SelectTip();
	       tc.ContinueTip();
	       tc.validateAfterLoyaltyFinancialDetails(TipPercent, beforeDiscountSubtotal);	    
	    }

	    @Test(dependsOnMethods="StayOrder_Tip_AddTip")
	    @Feature("Refund Management")
	    @Story("Perform Cash Transaction for Refund")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Completes a cash transaction for the order before processing an item-wise refund.")
	    public void StayOrder_Cash_RefundItemWise_Payment() {
	        Boolean result = tc.performCashTransaction();
	        Assert.assertTrue(result, "Cash Transaction failed");
	    }

	    @Test(dependsOnMethods="StayOrder_Cash_RefundItemWise_Payment")
	    @Feature("Refund Management")
	    @Story("Process Item-wise Refund")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Performs an item-wise refund for a previously paid cash transaction.")
	    public void StayOrder_Cash_RefundItemWise_SelectRefund() {
	        tc.selectHomeTransaction();
	        Boolean result = tc.cashRefundItemwise();
	        Assert.assertTrue(result, "Cash Item-wise Refund failed");
	    }

	    @Test(dependsOnMethods="StayOrder_Cash_RefundItemWise_SelectRefund")
	    @Feature("Refund Management")
	    @Story("Print Receipts after Item-wise Refund")
	    @Severity(SeverityLevel.MINOR)
	    @Description("Prints both customer and kitchen receipts after processing an item-wise refund.")
	    public void StayOrder_Cash_RefundItemWise_PrintReceipt() {
	        tc.PrintCustomerReceipt();
	        tc.PrintKitchenReceipt();
	        tc.ClickHome();
	    }

	    @Test(dependsOnMethods="StayOrder_Cash_RefundItemWise_PrintReceipt")
	    @Feature("Refund Management")
	    @Story("Validate Cash Log after Refund")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Checks the cash log to verify that the refund transaction has been recorded correctly.")
	    public void StayOrder_Cash_RefundItemWise_CheckCashLog() throws InterruptedException {
	        tc.MenuBurgerclick();
	        tc.CashLogButtonClick();
	        Boolean result = tc.ValidateCashLogRefund();
	        Assert.assertTrue(result, "Cash Log Result Not Same");
	        tc.ClickHome();
	    }

	    @Test(dependsOnMethods="StayOrder_Tip_AddTip")
	    @Feature("Refund Management")
	    @Story("Perform Full Cash Refund Transaction")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Completes a cash transaction before processing a full refund.")
	    public void StayOrder_Cash_FullRefund_Payment() {
	        Boolean result = tc.performCashTransaction();
	        Assert.assertTrue(result, "Cash Transaction failed");
	    }

	    @Test(dependsOnMethods="StayOrder_Cash_FullRefund_Payment")
	    @Feature("Refund Management")
	    @Story("Select Full Cash Refund")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Processes a full refund for a previously paid cash transaction.")
	    public void StayOrder_Cash_FullRefund_SelectRefund() {
	        tc.selectHomeTransaction();
	        Boolean result = tc.cashRefund();
	        Assert.assertTrue(result, "Cash Refund failed");
	    }

	    @Test(dependsOnMethods="StayOrder_Cash_FullRefund_SelectRefund")
	    @Feature("Refund Management")
	    @Story("Print Receipts after Full Cash Refund")
	    @Severity(SeverityLevel.MINOR)
	    @Description("Prints both customer and kitchen receipts after a full cash refund.")
	    public void StayOrder_Cash_FullRefund_PrintReceipt() {
	        tc.PrintCustomerReceipt();
	        tc.PrintKitchenReceipt();
	        tc.ClickHome();
	    }

	    @Test(dependsOnMethods="StayOrder_Cash_FullRefund_PrintReceipt")
	    @Feature("Refund Management")
	    @Story("Validate Cash Log after Full Refund")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Checks the cash log to verify that the full refund transaction has been recorded correctly.")
	    public void StayOrder_Cash_FullRefund_CheckCashLog() throws InterruptedException {
	        tc.MenuBurgerclick();
	        tc.CashLogButtonClick();
	        Boolean result = tc.ValidateCashLogRefund();
	        Assert.assertTrue(result, "Cash Log Result Not Same");
	        tc.ClickHome();
	    }

	    @Test(dependsOnMethods = "StayOrder_Tip_AddTip")
	    @Feature("Payment Processing")
	    @Story("Perform Card Payment")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Processes a payment using a credit/debit card.")
	    public void StayOrder_Card_FinalWayPayment() {
	        Boolean result = tc.CardTransaction();
	        Assert.assertTrue(result, "Card Transaction failed");
	    }

	    @Test(dependsOnMethods = "StayOrder_Card_FinalWayPayment")
	    @Feature("Refund Management")
	    @Story("Select Card Refund")
	    @Severity(SeverityLevel.CRITICAL)
	    @Description("Processes a refund for a transaction paid via card.")
	    public void StayOrder_Card_Refund_SelectRefund() {
	        tc.selectHomeTransaction();
	        Boolean result = tc.cardRefund();
	        Assert.assertTrue(result, "Card Refund failed");
	    }

	    @Test(dependsOnMethods = "StayOrder_Card_Refund_SelectRefund")
	    @Feature("Refund Management")
	    @Story("Print Receipts after Card Refund")
	    @Severity(SeverityLevel.MINOR)
	    @Description("Prints customer and kitchen receipts after processing a card refund.")
	    public void StayOrder_Card_Refund_PrintReceipt() {
	        tc.PrintCustomerReceipt();
	        tc.PrintKitchenReceipt();
	        tc.ClickHome();
	    }
}
