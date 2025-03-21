package Pays.posfinaltesting;


import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Random;



import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import Pays.posfinaltesting.actions.LoginClass;
import Pays.posfinaltesting.actions.PhoneOrderClass;
import Pays.posfinaltesting.actions.SetupClass;
import Pays.posfinaltesting.actions.TransactionClass;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

import org.openqa.selenium.interactions.Sequence;

@Epic("Phone Order")
@Feature("Phone Order")
public class PhoneOrderTest extends BaseTest{
	private PhoneOrderClass pickUp;
	private PhoneOrderClass delivery;
	private PhoneOrderClass cancelOrder;
	private PhoneOrderClass printReceipt;
	private TransactionClass deliverycash;
	private TransactionClass pickUpCash;
	private SetupClass orderNote;
	private SetupClass discount;
	public BigDecimal TipPercent; 
	public TransactionClass tc;
	public 	BigDecimal beforeDiscountSubtotal;
	
	/// PickUp Test Cases
	@BeforeMethod
	public void Login(ITestResult result) throws MalformedURLException, URISyntaxException, InterruptedException {
		String testName = result.getMethod().getMethodName();
	
	    // Execute login logic only for specific test cases
	    if (testName.matches("PhoneOrder_PrintCustomerReceipt_setup|PhoneOrder_PickUp_setup|PhoneOrder_Delivery_setup|PhoneOrder_Cancel_setup")) {
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
    @Severity(SeverityLevel.CRITICAL)
    @Description("Setup for Pickup Phone Order Test")
    @Step("Initializing test setup")
	@Feature("Phone  Pick upOrder")
    public void PhoneOrder_PickUp_setup() {
        pickUp = new PhoneOrderClass(driver);
        pickUpCash = new TransactionClass(driver);
        orderNote = new SetupClass(driver);
        discount = new SetupClass(driver);
        tc = new TransactionClass(driver);
    }

    @Test(dependsOnMethods = {"PhoneOrder_PickUp_setup"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Click the Phone Order Pickup Button")
    @Step("Clicking on Phone Order Pickup Button")
    public void PhoneOrder_PickUp_ButtonClick() {
        pickUp = new PhoneOrderClass(driver);
        pickUp.PhoneOrderButtonClick();
    }

    @Test(dependsOnMethods = {"PhoneOrder_PickUp_ButtonClick"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Add customer details and proceed")
    @Step("Adding a customer and proceeding to the next step")
    public void PhoneOrder_PickUp_AddCustomer() {
        pickUp.AddCustomer();
        pickUp.scrollToPhoneNumberAndClick("(570) 458-4827");
        pickUp.OrderNext();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @Test(dependsOnMethods = {"PhoneOrder_PickUp_AddCustomer"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Select items for the order")
    @Step("Selecting random items for the order")
    public void PhoneOrder_PickUp_ItemSelection() {
        int numItemsToAdd = 4;
        boolean itemSelectionResult = pickUp.selectRandomItems(numItemsToAdd);
        Assert.assertTrue(itemSelectionResult, "Item is not selected");
    }

    @Test(dependsOnMethods="PhoneOrder_PickUp_ItemSelection")
    @Severity(SeverityLevel.MINOR)
    @Description("Add manual items to the order")
    @Step("Adding manual items to the order")
    public void PhoneOrder_PickUp_AddManualItems() {
        Boolean result = pickUp.manualItem("Take Out");
        Assert.assertTrue(result, "Manual Items Not added");
    }

    @Test(dependsOnMethods = "PhoneOrder_PickUp_AddManualItems")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify the order type is Pickup")
    @Step("Checking if the order type is 'Pickup'")
    public void PhoneOrder_PickUp_CheckDeliveryType() {
        pickUp.ClickOrderSave();    
        String deliveryOrPickupText = pickUp.getDeliveryOrPickupText();
        Assert.assertTrue(deliveryOrPickupText.equals("Pickup"), "The text is not 'Pickup'");
    }

    @Test(dependsOnMethods = "PhoneOrder_PickUp_CheckDeliveryType")
    @Severity(SeverityLevel.MINOR)
    @Description("Update the order")
    @Step("Updating the order")
    public void PhoneOrder_PickUp_UpdateOrder() {
        pickUp.clickMainLayout();
        pickUp.UpdateOrder();
    }

    @Test(dependsOnMethods="PhoneOrder_PickUp_UpdateOrder")
    @Severity(SeverityLevel.MINOR)
    @Description("Add an order note")
    @Step("Adding an order note and saving it")
    public void PhoneOrder_PickUp_AddOrderNote() throws InterruptedException {
        orderNote.PhoneOrderAddONote();
        orderNote.SelectONote("Extra Spicy");
        boolean checkONote = orderNote.CheckIfONoteIsAdded();
        orderNote.SavePayMenu();
    }

    @Test(dependsOnMethods = "PhoneOrder_PickUp_UpdateOrder")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Update items and proceed to payment")
    @Step("Updating items and proceeding to payment")
    public void PhoneOrder_PickUp_UpdateItemsandPay() {
        boolean updateResult = pickUp.updateItems();
        Assert.assertTrue(updateResult, "Item not updated");
    }

    @Test(dependsOnMethods="PhoneOrder_PickUp_UpdateItemsandPay")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Apply discount to the order")
    @Step("Applying predefined discount")
    public void PhoneOrder_PickUp_AddDiscount() {
        beforeDiscountSubtotal = discount.getSubtotal();
        discount.AddDiscountButton();
        discount.SelectPredefinedDiscount();
        discount.SavePayMenu();
    }

    @Test(dependsOnMethods = "PhoneOrder_PickUp_AddDiscount")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validate financial details after discount")
    @Step("Validating financial details post-discount")
    public void PhoneOrder_PickUp_ValidateFinancialDetails() {
        pickUp.Paynow();
        boolean transaction = pickUpCash.validateFinancialDetails();
        Assert.assertTrue(transaction, "Financial Details are incorrect");
    }

    @Test(dependsOnMethods="PhoneOrder_PickUp_ValidateFinancialDetails")
    @Severity(SeverityLevel.MINOR)
    @Description("Add a tip to the order")
    @Step("Adding a tip of 20% to the order")
    public void PhoneOrder_PickUp_Tip_AddTip() {
        tc.Click_AddTip();
        TipPercent = BigDecimal.valueOf(20.00).divide(BigDecimal.valueOf(100));
        tc.SelectTip();
        tc.ContinueTip();
        tc.validateAfterLoyaltyFinancialDetails(TipPercent, beforeDiscountSubtotal);
    }

    @Test(dependsOnMethods = "PhoneOrder_PickUp_Tip_AddTip")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Complete the order with a cash payment")
    @Step("Performing a cash transaction to complete the order")
    public void PhoneOrder_PickUp_Cash_CheckTransactionDetails() {
        boolean result = tc.performCashTransaction();
        assert result : "Function did not return true!";
    }

    @Test(dependsOnMethods="PhoneOrder_PickUp_Cash_CheckTransactionDetails")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Refund a specific item in the order")
    @Step("Performing item-wise cash refund")
    public void PhoneOrder_PickUp_Cash_RefundItemWise_SelectRefund() {
        tc.selectHomeTransaction();
        Boolean result = tc.cashRefundItemwise();
        Assert.assertTrue(result, "Cash Item-wise Refund failed");
    }

    @Test(dependsOnMethods="PhoneOrder_PickUp_Cash_RefundItemWise_SelectRefund")
    @Severity(SeverityLevel.MINOR)
    @Description("Print customer and kitchen receipts after refund")
    @Step("Printing customer and kitchen receipts")
    public void PhoneOrder_PickUp_Cash_RefundItemWise_PrintReceipt() {
        tc.PrintCustomerReceipt();
        tc.PrintKitchenReceipt();
        tc.ClickHome();
    }

    @Test(dependsOnMethods="PhoneOrder_PickUp_Cash_RefundItemWise_PrintReceipt")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify the cash log after the refund")
    @Step("Validating cash log after refund")
    public void PhoneOrder_PickUp_Cash_RefundItemWise_CheckCashLog() throws InterruptedException {
        tc.MenuBurgerclick();
        tc.CashLogButtonClick();
        Boolean result = tc.ValidateCashLogRefund();
        Assert.assertTrue(result, "Cash Log Result Not Same");
        tc.ClickHome();
    }
	
	
/// Delivery Test Cases
	       
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Setup for Delivery Phone Order Test")
    @Step("Initializing test setup")
    @Feature("Phone Delivery Order")
    public void PhoneOrder_Delivery_setup() {
        delivery = new PhoneOrderClass(driver);
        deliverycash = new TransactionClass(driver);
        orderNote = new SetupClass(driver);
        discount = new SetupClass(driver);
        tc = new TransactionClass(driver);
    }

    @Test(dependsOnMethods = {"PhoneOrder_Delivery_setup"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Click the Phone Order Delivery Button")
    @Step("Clicking on Phone Order Delivery Button")
    public void PhoneOrder_Delivery_ButtonClick() {
        delivery.PhoneOrderButtonClick();
        delivery.deliveryClick(); // Clicks on Delivery option
    }

    @Test(dependsOnMethods = {"PhoneOrder_Delivery_ButtonClick"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Add customer details and proceed")
    @Step("Adding a customer and entering address details")
    public void PhoneOrder_Delivery_AddCustomer() {
        delivery.AddCustomerForm();
        delivery.scrollToCityField();
        delivery.fillAddressFields(); // Filling address details for delivery
        delivery.OrderNext();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @Test(dependsOnMethods = {"PhoneOrder_Delivery_AddCustomer"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Select items for the order")
    @Step("Selecting random items for the order")
    public void PhoneOrder_Delivery_ItemSelection() {
        int numItemsToAdd = 2;
        boolean itemSelectionResult = delivery.selectRandomItems(numItemsToAdd);
        Assert.assertTrue(itemSelectionResult, "Item is not selected");
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_ItemSelection")
    @Severity(SeverityLevel.MINOR)
    @Description("Add manual items to the order")
    @Step("Adding manual items to the order")
    public void PhoneOrder_Delivery_AddManualItems() {
        Boolean result = delivery.manualItem("Take Out");
        Assert.assertTrue(result, "Manual Items Not added");
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_AddManualItems")
    @Severity(SeverityLevel.MINOR)
    @Description("Update an item in the order")
    @Step("Updating the item quantity")
    public void PhoneOrder_Delivery_UpdateItem() {
        Boolean result = delivery.updateItemQuantity();
        Assert.assertTrue(result, "Items Not Updated");
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_UpdateItem")
    @Severity(SeverityLevel.MINOR)
    @Description("Remove an item from the order")
    @Step("Removing an item from the order")
    public void PhoneOrder_Delivery_RemoveItem() {
        Boolean result = delivery.removeItem();
        Assert.assertTrue(result, "Items Not Removed");
    }

    @Test(dependsOnMethods = "PhoneOrder_Delivery_RemoveItem")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify the order type is Delivery")
    @Step("Checking if the order type is 'Delivery'")
    public void PhoneOrder_Delivery_CheckDeliveryType() {
        delivery.ClickOrderSave();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        String deliveryOrPickupText = delivery.getDeliveryOrPickupText();
        Assert.assertTrue(deliveryOrPickupText.equals("Delivery"), "The text is not 'Delivery'");
    }

    @Test(dependsOnMethods = "PhoneOrder_Delivery_CheckDeliveryType")
    @Severity(SeverityLevel.MINOR)
    @Description("Update the order")
    @Step("Updating the order")
    public void PhoneOrder_Delivery_UpdateOrder() {
        delivery.clickMainLayout();
        delivery.UpdateOrder();
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_UpdateOrder")
    @Severity(SeverityLevel.MINOR)
    @Description("Add an order note")
    @Step("Adding an order note and saving it")
    public void PhoneOrder_Delivery_AddOrderNote() throws InterruptedException {
        Thread.sleep(5);
        orderNote.PhoneOrderAddONote();
        orderNote.SelectONote("Extra Spicy");
        boolean checkONote = orderNote.CheckIfONoteIsAdded();
        orderNote.SavePayMenu();
    }

    @Test(dependsOnMethods = "PhoneOrder_Delivery_UpdateOrder")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Update items and proceed to payment")
    @Step("Updating items and proceeding to payment")
    public void PhoneOrder_Delivery_UpdateItemsandPay() {
        boolean updateResult = delivery.updateItems();
        Assert.assertTrue(updateResult, "Item not updated");
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_UpdateItemsandPay")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Apply discount to the order")
    @Step("Applying predefined discount")
    public void PhoneOrder_Delivery_AddDiscount() {
        beforeDiscountSubtotal = discount.getSubtotal();
        discount.AddDiscountButton();
        discount.SelectPredefinedDiscount();
        discount.SavePayMenu();
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_AddDiscount")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Select Split Payment")
    @Step("Performing split payment")
    public void PhoneOrder_Delivery_Split_SelectSplit() {
        delivery.Paynow();
        tc.validateFinancialDetailsAfterDiscount(beforeDiscountSubtotal);
        tc.SelectSplitWisePayment();
        tc.SelectTwoWay();
        tc.ValidateSplitPayment();
        tc.SelectNextSplit();
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_Split_SelectSplit")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Add a tip to the order")
    @Step("Adding a tip of 20% to the order")
    public void PhoneOrder_Delivery_Tip_AddTip() {
        tc.Click_AddTip();
        TipPercent = BigDecimal.valueOf(20.00).divide(BigDecimal.valueOf(100));
        tc.SelectTip();
        tc.ContinueTip();
        tc.validateAfterLoyaltyFinancialDetails(TipPercent, beforeDiscountSubtotal);
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_Tip_AddTip")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Select Cash Payment for Split Order")
    @Step("Selecting cash payment")
    public void PhoneOrder_Delivery_Split_SelectCash() {
        tc.SelectCashButton();
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_Split_SelectCash")
    @Severity(SeverityLevel.NORMAL)
    @Description("Proceed to next split payment")
    @Step("Selecting next split payment option")
    public void PhoneOrder_Delivery_Split_SelectNextPayment() {
        tc.SelectNextPayment();
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_Split_SelectNextPayment")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Complete the final split payment")
    @Step("Performing final split payment")
    public void PhoneOrder_Delivery_Split_FinalWayPayment() {
        tc.performCashTransaction();
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_Split_FinalWayPayment")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Refund the order")
    @Step("Performing order refund")
    public void PhoneOrder_Delivery_Split_Refund_SelectRefund() {
        tc.selectHomeTransaction();
        Boolean result = tc.cashRefund();
        Assert.assertTrue(result, "Refund failed");
    }

    @Test(dependsOnMethods="PhoneOrder_Delivery_Split_Refund_SelectRefund")
    @Severity(SeverityLevel.MINOR)
    @Description("Print receipts after refund")
    @Step("Printing customer and kitchen receipts")
    public void PhoneOrder_Delivery_Split_Refund_PrintReceipt() {
        tc.PrintCustomerReceipt();
        tc.PrintKitchenReceipt();
        tc.ClickHome();
    }


//@Test(dependsOnMethods = "PhoneOrder_Delivery_UpdateItemsandPay")
//public void PhoneOrder_Delivery_ValidateFinancialDetails() {
//    boolean transaction = tc.validateFinancialDetails(); 
//    Assert.assertTrue(transaction, "Financial Details are incorrect");
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
//}
//
//@Test(dependsOnMethods = "PhoneOrder_Delivery_ValidateFinancialDetails")
//public void PhoneOrder_Delivery_CheckTransactionDetails() {
//	 
//	    boolean result = tc.performCashTransaction();
//	    assert result : "Function did not return true!";
//}

// Cancel Order TestCases




/// Cancel Order Test Cases

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Setup for Phone Order Cancellation Test")
    @Step("Initializing test setup for order cancellation")
    @Feature("Phone Cancel Order")
    public void PhoneOrder_Cancel_setup() {
        cancelOrder = new PhoneOrderClass(driver);
        tc = new TransactionClass(driver);
        printReceipt = new PhoneOrderClass(driver);
    }

    @Test(dependsOnMethods = {"PhoneOrder_Cancel_setup"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Click on Phone Order and select Delivery")
    @Step("Clicking on Phone Order Button and selecting Delivery")
    public void PhoneOrder_Cancel_ButtonClick() {
        cancelOrder.PhoneOrderButtonClick();
        cancelOrder.deliveryClick();
    }

    @Test(dependsOnMethods = {"PhoneOrder_Cancel_ButtonClick"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Add customer details and proceed")
    @Step("Adding customer details and proceeding to next step")
    public void PhoneOrder_Cancel_AddCustomer() {
        cancelOrder.AddCustomerForm();
        cancelOrder.scrollToCityField();
        cancelOrder.fillAddressFields();
        driver.findElement(AppiumBy.id("com.pays.pos:id/txtNext")).click();
    }

    @Test(dependsOnMethods = {"PhoneOrder_Cancel_AddCustomer"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Select items for the order")
    @Step("Selecting random items for the order")
    public void PhoneOrder_Cancel_ItemSelection() {
        int numItemsToAdd = 2;
        boolean itemSelectionResult = cancelOrder.selectRandomItems(numItemsToAdd);
        Assert.assertTrue(itemSelectionResult, "Item is not selected");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        cancelOrder.ClickOrderSave();
    }

    @Test(dependsOnMethods = {"PhoneOrder_Cancel_ItemSelection"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify the order type is Delivery")
    @Step("Checking if the order type is 'Delivery'")
    public void PhoneOrder_Cancel_CheckDeliveryType() {
        String deliveryOrPickupText = cancelOrder.getDeliveryOrPickupText();
        Assert.assertTrue(deliveryOrPickupText.equals("Delivery"), "The text is not 'Delivery'");
    }

    @Test(dependsOnMethods = {"PhoneOrder_Cancel_CheckDeliveryType"})
    @Severity(SeverityLevel.MINOR)
    @Description("Print customer receipt for the order")
    @Step("Printing the customer receipt")
    public void PhoneOrder_PrintCustomerReceipt_PrintReceipt() {
        printReceipt.clickMainLayout();
        printReceipt.clickPrintCustomerReceipt();
    }

    @Test(dependsOnMethods = {"PhoneOrder_PrintCustomerReceipt_PrintReceipt"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Cancel the order")
    @Step("Cancelling the order using order ID")
    public void PhoneOrder_Cancel_CancelOrder() {
        String orderIdText = cancelOrder.getOrderIdElements();
        System.out.println("Order ID: " + orderIdText);

        boolean cancelOrderResult = cancelOrder.cancelOrder(orderIdText);
        Assert.assertTrue(cancelOrderResult, "Order cancellation failed!");
        tc.ClickHome();
    }

// PrintReceipt_Setup


///// Test Cases for Printer Reciept 
//@Test
//public void PhoneOrder_PrintCustomerReceipt_setup() {
//    printReceipt = new PhoneOrderClass(driver);
//	tc = new TransactionClass(driver);
//}
//
//
//@Test(dependsOnMethods = {"PhoneOrder_PrintCustomerReceipt_setup"})
//public void PhoneOrder_PrintCustomerReceipt_ButtonClick() {
//    printReceipt.PhoneOrderButtonClick();
//    printReceipt.deliveryClick();
//}
//
//@Test(dependsOnMethods = {"PhoneOrder_PrintCustomerReceipt_ButtonClick"})
//public void PhoneOrder_PrintCustomerReceipt_AddCustomer() {
//    printReceipt.AddCustomerForm();
//    printReceipt.scrollToCityField();
//    printReceipt.fillAddressFields();
//    printReceipt.OrderNext();
//}
//
//@Test(dependsOnMethods = {"PhoneOrder_PrintCustomerReceipt_AddCustomer"})
//public void PhoneOrder_PrintCustomerReceipt_ItemSelection() {
//    int numItemsToAdd = 2; 
//    boolean itemSelectionResult = printReceipt.selectRandomItems(numItemsToAdd);
//    Assert.assertTrue(itemSelectionResult, "Item is not selected");
//
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
//    printReceipt.ClickOrderSave();
//}
//
//@Test(dependsOnMethods = {"PhoneOrder_PrintCustomerReceipt_ItemSelection"})
//public void PhoneOrder_PrintCustomerReceipt_CheckDeliveryType() {
//    String deliveryOrPickupText = printReceipt.getDeliveryOrPickupText();
//    Assert.assertTrue(deliveryOrPickupText.equals("Delivery"), "The text is not 'Delivery'");
//}



}




