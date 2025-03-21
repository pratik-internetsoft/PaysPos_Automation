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
import Pays.posfinaltesting.actions.OpenOrderClass;
import Pays.posfinaltesting.actions.PaxClass;
import Pays.posfinaltesting.actions.SetupClass;
import Pays.posfinaltesting.actions.TransactionClass;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
@Epic("Order Management") // High-level feature or module
@Feature("Open Order") // Feature under test
@Story("Create and Manage Open Orders") 
public class OpenOderTest extends BaseTest {
	private OpenOrderClass openOrder;
	private WebDriverWait wait;
	private TransactionClass tc;
	private SetupClass orderNote;
	private SetupClass discount;
	public BigDecimal beforeDiscountSubtotal;
	public BigDecimal TipPercent;

	@BeforeMethod
	public void Login(ITestResult result) throws MalformedURLException, URISyntaxException, InterruptedException {
		String testName = result.getMethod().getMethodName();

		// Execute login logic only for specific test cases
		if (testName.matches("OpenOrder_Setup")) {
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
    @Description("Setup for Open Order tests")
    @Step("Initialize all necessary classes and WebDriverWait")
    @Story("Setup and Initialization")
    public void OpenOrder_Setup() {
        openOrder = new OpenOrderClass(driver);
        orderNote = new SetupClass(driver);
        tc = new TransactionClass(driver);
        discount = new SetupClass(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(dependsOnMethods = "OpenOrder_Setup")
    @Description("Connect to Pax device for credit card processing")
    @Step("Connect to Pax device")
    @Story("Pax Device Integration")
    public void OpenOrder_Card_ConnecttoPax() throws InterruptedException, TimeoutException {
        PaxClass pax = new PaxClass(driver);
        pax.MenuBurgerclick();
        pax.HardwareButtonClick();
        pax.CreditcardClick();
        pax.Paxconnection();
    }

    @Test(dependsOnMethods = "OpenOrder_Setup")
    @Description("Select Open Order from the menu")
    @Step("Click on Open Order")
    @Story("Order Selection")
    public void OpenOrder_SelectOpenOrder() {
        openOrder.ClickOpenOrder();
    }

    @Test(dependsOnMethods = "OpenOrder_SelectOpenOrder")
    @Description("Select random items for the order")
    @Step("Select {numItemsToAdd} random items")
    @Story("Item Selection")
    public void OpenOrder_ItemSelection() {
        int numItemsToAdd = 2;
        boolean itemSelectionResult = openOrder.selectRandomItems(numItemsToAdd);
        Assert.assertTrue(itemSelectionResult, "Item is not selected");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @Test(dependsOnMethods = "OpenOrder_ItemSelection")
    @Description("Add manual items to the order")
    @Step("Add manual item 'Take Out'")
    @Story("Manual Item Addition")
    public void OpenOrder_AddManualItems() {
        Boolean result = openOrder.manualItem("Take Out");
        Assert.assertTrue(result, "Manual Items Not added");
    }

    @Test(dependsOnMethods = "OpenOrder_AddManualItems")
    @Description("Update item quantity in the order")
    @Step("Update item quantity")
    @Story("Order Modification")
    public void OpenOrder_UpdateItem() {
        Boolean result = openOrder.updateItemQuantity();
        Assert.assertTrue(result, "Items Not Updated");
    }

    @Test(dependsOnMethods = "OpenOrder_UpdateItem")
    @Description("Remove an item from the order")
    @Step("Remove item from the order")
    @Story("Order Modification")
    public void OpenOrder_RemoveItem() {
        Boolean result = openOrder.removeItem();
        Assert.assertTrue(result, "Items Not Removed");
    }

    @Test(dependsOnMethods = "OpenOrder_RemoveItem")
    @Description("Save the current order")
    @Step("Click on Save Order")
    @Story("Order Saving")
    public void OpenOrder_SaveOrder() {
        openOrder.ClickOrderSave();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @Test(dependsOnMethods = "OpenOrder_SaveOrder")
    @Description("Check the order type")
    @Step("Verify order type is 'Open Order'")
    @Story("Order Validation")
    public void OpenOrder_CheckOrderType() throws InterruptedException {
        String OrderTypeText = openOrder.getOrderTypeText();
        Assert.assertTrue(OrderTypeText.equals("Open Order"), "The OrderType is not 'OpenOrder'");
    }

    @Test(dependsOnMethods = "OpenOrder_CheckOrderType")
    @Description("Update the order")
    @Step("Update the order details")
    @Story("Order Modification")
    public void OpenOrder_UpdateOrder() {
        openOrder.clickMainLayout();
        openOrder.UpdateOrder();
    }
    @Test(dependsOnMethods = "OpenOrder_UpdateOrder")
    @Description("Add a note to the order")
    @Step("Add order note 'Extra Spicy'")
    @Story("Order Notes")
    public void OpenOrder_AddOrderNote() {
        orderNote.AddONote();
        orderNote.SelectONote("Extra Spicy");
        boolean checkONote = orderNote.CheckIfONoteIsAdded();
        orderNote.SavePayMenu();
    }

    @Test(dependsOnMethods = "OpenOrder_AddOrderNote")
    @Description("Add discount to the order")
    @Step("Add discount and save payment menu")
    @Story("Discount Application")
    public void OpenOrder_AddDiscount() {
        beforeDiscountSubtotal = discount.getSubtotal();
        discount.AddDiscountButton();
        discount.SelectPredefinedDiscount();
        discount.SavePayMenu();
    }

   
    @Test(dependsOnMethods = "OpenOrder_AddDiscount")
    @Description("Change the order type")
    @Step("Change order type to 'Take Out' and back to 'Open Order'")
    @Story("Order Type Change")
    public void OpenOrder_ChangeOrderType() {
        boolean changeOrder = openOrder.ChangeOrderType("Take Out");
        Assert.assertTrue(changeOrder, "Items Not Removed");
        boolean Ordertype = openOrder.ChangeOrderType("Open Order");
        Assert.assertTrue(Ordertype, "Items Not Removed");
    }

    
    @Test(dependsOnMethods = "OpenOrder_ChangeOrderType")
    @Description("Add a customer to the order")
    @Step("Add customer with phone number '(292) 119-7050'")
    @Story("Customer Management")
    public void OpenOrder_AddCustomer() {
        openOrder.AddCustomer();
        openOrder.scrollToPhoneNumberAndClick("(292) 119-7050");
    }
    
    @Test (dependsOnMethods="OpenOrder_AddCustomer")
	public void OpenOrder_Loyalty_AppliedLoyaltyPoint() {
		BigDecimal LoyaltyBalance = tc.getLoyalty();
		BigDecimal LoyaltyPoints = tc.getAppliedLoyaltyPoints();
	    tc.ApplyLoyalty();
	    boolean result = tc.ValidateloyaltyBalance(LoyaltyBalance, LoyaltyPoints);
	    Assert.assertTrue(result,"Loyalty failed");
		
	}

    @Test(dependsOnMethods = "OpenOrder_AddCustomer")
    @Description("Process the transaction")
    @Step("Click on Pay Now")
    @Story("Payment Processing")
    public void OpenOrder_Transaction() {
        tc.Paynow();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @Test(dependsOnMethods = "OpenOrder_Transaction")
    @Description("Select split payment option")
    @Step("Select split payment and validate")
    @Story("Split Payment")
    public void OpenOrder_Split_SelectSplit() {
        tc.validateFinancialDetailsAfterDiscount(beforeDiscountSubtotal);
        tc.SelectSplitWisePayment();
        tc.SelectTwoWay();
        tc.ValidateSplitPayment();
        tc.SelectNextSplit();
    }

    @Test(dependsOnMethods = "OpenOrder_Split_SelectSplit")
    @Description("Select cash payment option")
    @Step("Click on Cash Button")
    @Story("Cash Payment")
    public void OpenOrder_Split_SelectCash() {
        tc.SelectCashButton();
    }

    @Test(dependsOnMethods = "OpenOrder_Split_SelectCash")
    @Description("Select next payment option")
    @Step("Click on Next Payment")
    @Story("Split Payment")
    public void OpenOrder_Split_SelectNextPayment() {
        tc.SelectNextPayment();
    }

    @Test(dependsOnMethods = "OpenOrder_Split_SelectNextPayment")
    @Description("Perform final cash transaction")
    @Step("Perform cash transaction")
    @Story("Cash Payment")
    public void OpenOrder_Split_FinalWayPayment() {
        tc.performCashTransaction();
    }

    @Test(dependsOnMethods = "OpenOrder_Split_FinalWayPayment")
    @Description("Select refund option")
    @Step("Click on Refund")
    @Story("Refund Processing")
    public void OpenOrder_Split_Refund_SelectRefund() {
        tc.selectHomeTransaction();
        Boolean result = tc.cashRefund();
        Assert.assertTrue(result, "Failure");
    }

    @Test(dependsOnMethods = "OpenOrder_Split_Refund_SelectRefund")
    @Description("Print receipts for the transaction")
    @Step("Print customer and kitchen receipts")
    @Story("Receipt Generation")
    public void OpenOrder_Split_Refund_PrintReceipt() {
        tc.PrintCustomerReceipt();
        tc.PrintKitchenReceipt();
        tc.ClickHome();
    }

    @Test(dependsOnMethods = "OpenOrder_Transaction")
    @Description("Add tip to the transaction")
    @Step("Add 20% tip")
    @Story("Tip Addition")
    public void OpenOrder_Tip_AddTip() {
        tc.Click_AddTip();
        TipPercent = BigDecimal.valueOf(20.00).divide(BigDecimal.valueOf(100));
        tc.SelectTip();
        tc.ContinueTip();
        tc.validateAfterLoyaltyFinancialDetails(TipPercent, beforeDiscountSubtotal);
    }

    @Test(dependsOnMethods = "OpenOrder_Tip_AddTip")
    @Description("Perform cash refund item-wise")
    @Step("Perform cash refund item-wise")
    @Story("Refund Processing")
    public void OpenOrder_Cash_RefundItemWise_Payment() {
        Boolean result = tc.performCashTransaction();
        Assert.assertTrue(result, "Cash Transaction failed");
    }

    @Test(dependsOnMethods = "OpenOrder_Cash_RefundItemWise_Payment")
    @Description("Select item-wise refund option")
    @Step("Click on Item-wise Refund")
    @Story("Refund Processing")
    public void OpenOrder_Cash_RefundItemWise_SelectRefund() {
        tc.selectHomeTransaction();
        Boolean result = tc.cashRefundItemwise();
        Assert.assertTrue(result, "Cash Item-wise Refund failed");
    }

    @Test(dependsOnMethods = "OpenOrder_Cash_RefundItemWise_SelectRefund")
    @Description("Print receipts for item-wise refund")
    @Step("Print customer and kitchen receipts")
    @Story("Receipt Generation")
    public void OpenOrder_Cash_RefundItemWise_PrintReceipt() {
        tc.PrintCustomerReceipt();
        tc.PrintKitchenReceipt();
        tc.ClickHome();
    }

    @Test(dependsOnMethods = "OpenOrder_Cash_RefundItemWise_PrintReceipt")
    @Description("Check cash log for item-wise refund")
    @Step("Validate cash log for item-wise refund")
    @Story("Cash Log Validation")
    public void OpenOrder_Cash_RefundItemWise_CheckCashLog() throws InterruptedException {
        tc.MenuBurgerclick();
        tc.CashLogButtonClick();
        Boolean result = tc.ValidateCashLogRefund();
        Assert.assertTrue(result, "Cash Log Result Not Same");
        tc.ClickHome();
    }

    @Test(dependsOnMethods = "OpenOrder_Tip_AddTip")
    @Description("Perform full cash refund")
    @Step("Perform full cash refund")
    @Story("Refund Processing")
    public void OpenOrder_Cash_FullRefund_Payment() {
        Boolean result = tc.performCashTransaction();
        Assert.assertTrue(result, "Cash Transaction failed");
    }

    @Test(dependsOnMethods = "OpenOrder_Cash_FullRefund_Payment")
    @Description("Select full refund option")
    @Step("Click on Full Refund")
    @Story("Refund Processing")
    public void OpenOrder_Cash_FullRefund_SelectRefund() {
        tc.selectHomeTransaction();
        Boolean result = tc.cashRefund();
        Assert.assertTrue(result, "Cash Refund failed");
    }

    @Test(dependsOnMethods = "OpenOrder_Cash_FullRefund_SelectRefund")
    @Description("Print receipts for full refund")
    @Step("Print customer and kitchen receipts")
    @Story("Receipt Generation")
    public void OpenOrder_Cash_FullRefund_PrintReceipt() {
        tc.PrintCustomerReceipt();
        tc.PrintKitchenReceipt();
        tc.ClickHome();
    }

    @Test(dependsOnMethods = "OpenOrder_Cash_FullRefund_PrintReceipt")
    @Description("Check cash log for full refund")
    @Step("Validate cash log for full refund")
    @Story("Cash Log Validation")
    public void OpenOrder_Cash_FullRefund_CheckCashLog() throws InterruptedException {
        tc.MenuBurgerclick();
        tc.CashLogButtonClick();
        Boolean result = tc.ValidateCashLogRefund();
        Assert.assertTrue(result, "Cash Log Result Not Same");
        tc.ClickHome();
    }

    @Test(dependsOnMethods = "OpenOrder_Tip_AddTip")
    @Description("Perform final card transaction")
    @Step("Perform card transaction")
    @Story("Card Payment")
    public void OpenOrder_Card_FinalWayPayment() {
        Boolean result = tc.CardTransaction();
        Assert.assertTrue(result, "Card Transaction failed");
    }

    @Test(dependsOnMethods = "OpenOrder_Card_FinalWayPayment")
    @Description("Select card refund option")
    @Step("Click on Card Refund")
    @Story("Refund Processing")
    public void OpenOrder_Card_Refund_SelectRefund() {
        tc.selectHomeTransaction();
        Boolean result = tc.cardRefund();
        Assert.assertTrue(result, "Card Refund failed");
    }

    @Test(dependsOnMethods = "OpenOrder_Card_Refund_SelectRefund")
    @Description("Print receipts for card refund")
    @Step("Print customer and kitchen receipts")
    @Story("Receipt Generation")
    public void OpenOrder_Card_Refund_PrintReceipt() {
        tc.PrintCustomerReceipt();
        tc.PrintKitchenReceipt();
        tc.ClickHome();
    }

//	@Test(dependsOnMethods ="OpenOrder_Refund_PrintReceipt")
//	public void OpenOrder_Refund_TextCustomerReceipt() {
//       tc.TextCustomerReceipt();
//       t
//	}
//	
}