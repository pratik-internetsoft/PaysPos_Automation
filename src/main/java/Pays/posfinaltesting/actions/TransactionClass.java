package Pays.posfinaltesting.actions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class TransactionClass {

	private AppiumDriver driver;
	private WebDriverWait wait;
	private BigDecimal total;
	private double refundAmount;
	private String OrderId;
	// Constructor to gift card class
	public TransactionClass(AppiumDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));// Explicit wait of 10 sec
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.pays.pos:id/txtTransaction")
	private WebElement transactionButton;

	@AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id='com.pays.pos:id/tvTotalAmount'])[1]")
	private WebElement latestTransactionAmount;

	@AndroidFindBy(id = "com.pays.pos:id/txtHome")
	private WebElement homeButton;
	@AndroidFindBy(id = "com.pays.pos:id/tvCash0")
	private WebElement cash;
	@AndroidFindBy(id = "com.pays.pos:id/txtPaymentAmount")
	private WebElement transactionamt;
	// driver.findElement(AppiumBy.id("com.pays.pos:id/llHome")).click();
	@AndroidFindBy(id = "com.pays.pos:id/llHome")
	private WebElement HomeButton;

	///// Locators using @AndroidFindBy
	@AndroidFindBy(id = "com.pays.pos:id/txtSubTotal")
	public WebElement subtotalElement;

	@AndroidFindBy(id = "com.pays.pos:id/txtTax")
	private WebElement taxElement;

	@AndroidFindBy(id = "com.pays.pos:id/txtServiceCharge")
	private WebElement serviceChargeElement;

	@AndroidFindBy(id = "com.pays.pos:id/txtDiscount")
	private WebElement discountElement;

	@AndroidFindBy(id = "com.pays.pos:id/txt_noncashAdj")
	private WebElement surchargeElement;

	@AndroidFindBy(id = "com.pays.pos:id/txtTotal")
	private WebElement totalElement;

	@AndroidFindBy(id = "com.pays.pos:id/tvCard")
	private WebElement cardElement;

	@AndroidFindBy(id = "com.pays.pos:id/tvCash")
	private WebElement cashElement;

	@AndroidFindBy(id = "com.pays.pos:id/tvtipcard")
	private WebElement CardTip;

	@AndroidFindBy(id = "com.pays.pos:id/tvtipcash")
	private WebElement CashTip;
	// com.pays.pos:id/tvPayNow
	@AndroidFindBy(id = "com.pays.pos:id/tvPayNow")
	private WebElement Pay;

	@AndroidFindBy(id = "com.pays.pos:id/tab2")
	private WebElement SplitButton;

	@AndroidFindBy(id = "com.pays.pos:id/tv2ways")
	private WebElement TwoWay;

	@AndroidFindBy(id = "com.pays.pos:id/tvAmount")
	private WebElement SplitAmount;

	@AndroidFindBy(id = "com.pays.pos:id/tvwaysplit")
	private WebElement NoOfSplit;

	@AndroidFindBy(id = "com.pays.pos:id/linear_next_split")
	private WebElement NextSplit;
	// Refund
	@AndroidFindBy(id = "com.pays.pos:id/llNoReceipt")
	private WebElement NextPayment;

	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.pays.pos:id/rvTeamTimeSheet\"]/android.widget.LinearLayout[1]/android.widget.LinearLayout")
	private WebElement TransactionFirstLayout;

	// Define locators using POM
	@AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id='com.pays.pos:id/tvOrderType'])[1]")
	private WebElement orderType;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.pays.pos:id/tvIssueRefund']")
	private WebElement issueRefund;

	@AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/androidx.appcompat.widget.LinearLayoutCompat/android.widget.LinearLayout")
	private WebElement cashRefund;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtDone\"]")
	private WebElement done;

	@AndroidFindBy(id = "com.pays.pos:id/tvSave")
	private WebElement saveRefund;

	@AndroidFindBy(xpath = "//androidx.appcompat.widget.LinearLayoutCompat[@resource-id=\"com.pays.pos:id/llRefundAmount\"]")
	private WebElement refundedValueParent;

	@AndroidFindBy(xpath = "//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/linear_summary\"]/android.widget.LinearLayout/androidx.appcompat.widget.LinearLayoutCompat[3]")
	private WebElement totalValueParent;
	
	@AndroidFindBy(id="com.pays.pos:id/txtPrintReceipt")
	private WebElement PrintCustomerReceipt;
    
	@AndroidFindBy(id="com.pays.pos:id/txtPrintKitchenReceipt")
	private WebElement PrintKitchenReceipt;
	
	@AndroidFindBy(id="com.pays.pos:id/txtTextReceipt")
	private WebElement TextCustomerReciept;
	
	@AndroidFindBy(id="com.pays.pos:id/edtPhoneNo")
	private WebElement MobileNo;
	
	@AndroidFindBy(id="com.pays.pos:id/txt_send")
	private WebElement MobileNoSend;
	
    @AndroidFindBy(id="com.pays.pos:id/tvAddTip")
    private WebElement AddTipButton;
    
    
    @AndroidFindBy(id="com.pays.pos:id/txtLoyaltyBalance")
    private WebElement LoyaltyBalance;
    
    @AndroidFindBy(id="com.pays.pos:id/txtLoyaltyPoints")
    private WebElement LoyaltyPoints;
    
    @AndroidFindBy(id="com.pays.pos:id/checkloylaty")
    private WebElement LoyaltyPointCheckBox;
    
    @AndroidFindBy(id="com.pays.pos:id/txtLoyaltyAmount")
    private WebElement AppliedLoyaltyPoint;
    
    @AndroidFindBy(xpath ="//android.widget.TextView[@resource-id=\"com.pays.pos:id/txt20\"]")
    private WebElement TwentyPercentTip;
    
    @AndroidFindBy(id= "com.pays.pos:id/order_detail_order_id")
    private WebElement RefundOrderId;

    @AndroidFindBy(id ="com.pays.pos:id/imgDrawer")
    public WebElement MenuBurgerButton;
    
    @AndroidFindBy(id="com.pays.pos:id/linearCashLog")
    public WebElement CashLogButton;
	// Method to capture financial details and perform validation
	public boolean validateFinancialDetails() {
		try {
			// Extract text values and clean non-numeric characters
			BigDecimal subtotal = getFormattedValue(subtotalElement);
			BigDecimal tax = getFormattedValue(taxElement);
			BigDecimal serviceCharge = getFormattedValue(serviceChargeElement);

			BigDecimal surcharge = getFormattedValue(surchargeElement);
			BigDecimal total = getFormattedValue(totalElement);
			BigDecimal cardValue = getFormattedValue(cardElement);
			BigDecimal cashValue = getFormattedValue(cashElement);

			// Calculate expected cash and card amounts

			BigDecimal expectedCashTotal = subtotal.add(tax).add(serviceCharge);
			BigDecimal expectedCardTotal = expectedCashTotal.add(surcharge);

			// Assertions for validation
			Assert.assertEquals(expectedCashTotal, total, "Cash total does not match total value");
			Assert.assertEquals(cashValue, expectedCashTotal, "Cash value does not match expected cash total");
			Assert.assertEquals(expectedCardTotal, cardValue, "Card value does not match expected card total");

		} catch (Exception e) {
			System.out.println("Error capturing financial details: " + e.getMessage());
		}
		return true;
	}

	// Helper method to extract and format numerical values
	public BigDecimal getFormattedValue(WebElement element) {
		String valueText = wait.until(ExpectedConditions.visibilityOf(element)).getText().replaceAll("[^0-9.]", "");
		return new BigDecimal(valueText).setScale(2, RoundingMode.HALF_UP);
	}

	// Method to handle cash transaction and validate amounts
	public Boolean performCashTransaction() {
		try {
			// Select cash payment and get transaction amount
			cash.click();
			System.out.println("performed Cash ");
			String amt = getTransactionamount();
			BigDecimal finalAmt = new BigDecimal(amt).setScale(2, RoundingMode.HALF_UP);
			System.out.println("Final Transaction Amount: " + finalAmt);

			FinalHomeButton();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

			// Click on the transaction button
			transactionButton.click();

			// Retrieve latest transaction amount from history
			String transactionAmtText = wait.until(ExpectedConditions.visibilityOf(latestTransactionAmount)).getText()
					.replaceAll("[^0-9.]", "");
			BigDecimal transactionAmt = new BigDecimal(transactionAmtText).setScale(2, RoundingMode.HALF_UP);

			// Validate the transaction amounts
			Boolean isValid = finalAmt.compareTo(transactionAmt) == 0;
			Assert.assertTrue(isValid, "Amounts are not equal!");

			// Navigate back to home
			wait.until(ExpectedConditions.visibilityOf(homeButton)).click();

			return isValid;
		} catch (Exception e) {
			System.out.println("Error in cash transaction validation: " + e.getMessage());
			return false;
		}
	}

	public String getTransactionamount() {
		String amt = wait
				.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.pays.pos:id/txtPaymentAmount")))
				.getText().replaceAll("[^0-9.]", "");
		return amt;
	}

	public void FinalHomeButton() {
		wait.until(ExpectedConditions.elementToBeClickable(HomeButton)).click();
	}

//Final transaction through Synergy
	public void Synergy() {

		driver.findElement(AppiumBy.id("com.pays.pos:id/llDynamicPayment")).click();
		wait.until(ExpectedConditions.elementToBeClickable(HomeButton)).click();
	}

//Final transaction througgh card
	public Boolean CardTransaction() {
	
		try {
			// Select cash payment and get transaction amount
			driver.findElement(AppiumBy.id("com.pays.pos:id/llCreditCard")).click();
			Thread.sleep(1000);
			System.out.println("performed Card ");
			String amt = getTransactionamount();
			BigDecimal finalAmt = new BigDecimal(amt).setScale(2, RoundingMode.HALF_UP);
			System.out.println("Final Transaction Amount: " + finalAmt);

			FinalHomeButton();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

			// Click on the transaction button
			transactionButton.click();

			// Retrieve latest transaction amount from history
			String transactionAmtText = wait.until(ExpectedConditions.visibilityOf(latestTransactionAmount)).getText()
					.replaceAll("[^0-9.]", "");
			BigDecimal transactionAmt = new BigDecimal(transactionAmtText).setScale(2, RoundingMode.HALF_UP);

			// Validate the transaction amounts
			Boolean isValid = finalAmt.compareTo(transactionAmt) == 0;
			Assert.assertTrue(isValid, "Amounts are not equal!");

			// Navigate back to home
			wait.until(ExpectedConditions.visibilityOf(homeButton)).click();

			return isValid;
		} catch (Exception e) {
			System.out.println("Error in cash transaction validation: " + e.getMessage());
			return false;
		}
	}

	public boolean validateAddtipFinancialDetails(BigDecimal TipValue,BigDecimal subtotal) {
		try {
			// Extract text values and clean non-numeric characters
		
			BigDecimal tax = getFormattedValue(taxElement);
			BigDecimal serviceCharge = getFormattedValue(serviceChargeElement);
			BigDecimal discount = getFormattedValue(discountElement);
			BigDecimal surcharge = getFormattedValue(surchargeElement);
			BigDecimal total = getFormattedValue(totalElement);
			BigDecimal cardValue = getFormattedValue(cardElement);
			BigDecimal cashValue = getFormattedValue(cashElement);
			BigDecimal cardtip = getFormattedValue(CardTip);
			BigDecimal cashtip = getFormattedValue(CashTip);
			

			// Step 1: Calculate Cash Tip (Tip applied on base total)
			BigDecimal baseTotal = subtotal.add(tax).add(serviceCharge).subtract(discount);
			BigDecimal CashTip = baseTotal.multiply(TipValue).setScale(2, RoundingMode.HALF_UP);
			
			// Step 2: Calculate Expected Cash Total
			BigDecimal expectedCashTotal = baseTotal.add(CashTip).setScale(2, RoundingMode.HALF_UP);

			// Step 3: Calculate Card Tip (Tip applied to base total before adding
			// surcharge)
			BigDecimal cardTip = baseTotal.multiply(TipValue).setScale(2, RoundingMode.HALF_UP);

			// Step 4: Calculate Expected Card Total (Add surcharge at the end)
			BigDecimal expectedCardTotal = baseTotal.add(cardTip).add(surcharge).setScale(2, RoundingMode.HALF_UP);

			// Debugging - Print Values
			System.out.println("Expected Cash Total: " + expectedCashTotal);
			System.out.println("Actual Cash Value: " + cashValue);
			System.out.println("Expected Card Total: " + expectedCardTotal);
			System.out.println("Actual Card Value: " + cardValue);

			// Assertions for Validation
			Assert.assertEquals(cashValue.setScale(2, RoundingMode.HALF_UP), expectedCashTotal,
					"Cash value does not match expected cash total");

			BigDecimal tolerance = new BigDecimal("0.30");

			// Check if actual card value is within ±0.3 range of expected card total
			BigDecimal lowerBound = expectedCardTotal.subtract(tolerance);
			BigDecimal upperBound = expectedCardTotal.add(tolerance);

			Assert.assertTrue(cardValue.compareTo(lowerBound) >= 0 && cardValue.compareTo(upperBound) <= 0,
					"Card value does not match expected card total within tolerance of 0.3. " + "Expected: "
							+ expectedCardTotal + ", Actual: " + cardValue);

		} catch (Exception e) {
			System.out.println("Error capturing financial details: " + e.getMessage());
		}
		return true;
	}
	public BigDecimal getSubtotal() {
		 BigDecimal subtotal = getFormattedValue(subtotalElement);
		return subtotal;
	}

	public boolean validateFinancialDetailsAfterDiscount(BigDecimal subtotal) {
		try {
			// Extract text values and clean non-numeric characters

			BigDecimal tax = getFormattedValue(taxElement);
			BigDecimal serviceCharge = getFormattedValue(serviceChargeElement);
			BigDecimal discount = getFormattedValue(discountElement);
			BigDecimal surcharge = getFormattedValue(surchargeElement);
			total = getFormattedValue(totalElement);
			BigDecimal cardValue = getFormattedValue(cardElement);
			BigDecimal cashValue = getFormattedValue(cashElement);

			// Calculate expected cash and card amounts

			BigDecimal discountedSubtotal = subtotal.subtract(discount);
			BigDecimal expectedCashTotal = discountedSubtotal.add(tax).add(serviceCharge);
			BigDecimal expectedCardTotal = expectedCashTotal.add(surcharge);

			// Assertions for validation
			Assert.assertEquals(expectedCashTotal, total, "Cash total does not match total value");
			Assert.assertEquals(cashValue, expectedCashTotal, "Cash value does not match expected cash total");
			Assert.assertEquals(expectedCardTotal, cardValue, "Card value does not match expected card total");

		} catch (Exception e) {
			System.out.println("Error capturing financial details: " + e.getMessage());
		}
		return true;
	}// This method waits until the "Pay" button is visible before clicking on it

	public void Paynow() {
		wait.until(ExpectedConditions.visibilityOf(Pay)).click();
	}

	// SplitWise Payment

	public void SelectSplitWisePayment() {
		wait.until(ExpectedConditions.visibilityOf(SplitButton)).click();
	}

	public void SelectTwoWay() {
		wait.until(ExpectedConditions.visibilityOf(TwoWay)).click();

	}

	public void ValidateSplitPayment() {
		BigDecimal SplitAmt = getFormattedValue(SplitAmount);
		BigDecimal noofSplits = getFormattedValue(NoOfSplit);

		// Perform division with rounding mode
		BigDecimal Amt = total.divide(noofSplits, 2, RoundingMode.HALF_UP); // Round to 2 decimal places

		BigDecimal tolerance = new BigDecimal("0.3"); // Define the tolerance

		// Check if the absolute difference is within the tolerance
		BigDecimal difference = SplitAmt.subtract(Amt).abs();
		Assert.assertTrue(difference.compareTo(tolerance) <= 0, "SplitedAmt is not within the tolerance of 0.3");
	}

	public void SelectNextSplit() {
		wait.until(ExpectedConditions.visibilityOf(NextSplit)).click();
	}

	public void SelectCashButton() {
		wait.until(ExpectedConditions.visibilityOf(cash)).click();
	}

	public void SelectNextPayment() {
		wait.until(ExpectedConditions.visibilityOf(NextPayment)).click();
	}

// Refund Payment

	public void selectHomeTransaction() {
		wait.until(ExpectedConditions.visibilityOf(transactionButton)).click();

	}
	
	public Boolean cashRefundItemwise() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(orderType)).click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			OrderId = wait.until(ExpectedConditions.visibilityOf(RefundOrderId)).getText();
			System.out.println(OrderId);
			Thread.sleep(10);
			wait.until(ExpectedConditions.elementToBeClickable(issueRefund)).click();
			System.out.println("Refund Initiated");

			try {
				wait.until(ExpectedConditions.visibilityOf(cashRefund));
				List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy.xpath("(//android.widget.ImageView[@resource-id='com.pays.pos:id/ivCheck'])")));
	            int numItems = items.size();
	            int randomItemIndex = new Random().nextInt(numItems) + 1;

	            // Select a random item to refund
	            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@resource-id='com.pays.pos:id/ivCheck'])[" + randomItemIndex + "]"))).click();

	            // Get the item amount
	            String itemAmountText = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.pays.pos:id/linearLayoutCompat'])[" + randomItemIndex + "]/androidx.appcompat.widget.LinearLayoutCompat/android.widget.LinearLayout/following-sibling::*"))).getText();
	            float itemAmount = Float.parseFloat(itemAmountText.trim().replace("$", ""));
	            System.out.println("The Item that user is not refunding is of " + itemAmount + "$\n");

				wait.until(ExpectedConditions.elementToBeClickable(done)).click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				Thread.sleep(10);			
				wait.until(ExpectedConditions.elementToBeClickable(done)).click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				System.out.println("Refund Button click");
				Thread.sleep(10);	
				wait.until(ExpectedConditions.elementToBeClickable(saveRefund)).click();
				System.out.println("Save Button click");
				return true;

			} catch (Exception e) {
				System.out.println("Error during refund process: " + e.getMessage());
				return false;
			}

//			try {
//				System.out.println("Final Check");
//				WebElement refundedValueParentElem = wait.until(ExpectedConditions.visibilityOf(refundedValueParent));
//				List<WebElement> textViews = refundedValueParentElem
//						.findElements(AppiumBy.className("android.widget.TextView"));
//				refundAmount = Double.parseDouble(textViews.get(1).getText().trim().replace("$", ""));
//				System.out.println("Refunded Amount: " + refundAmount);
//
//				WebElement totalValueParentElem = wait.until(ExpectedConditions.visibilityOf(totalValueParent));
//				List<WebElement> textView = totalValueParentElem
//						.findElements(AppiumBy.className("android.widget.TextView"));
//				double paidAmount = Double.parseDouble(textView.get(1).getText().trim().replace("$", ""));
//				System.out.println("Paid Amount: " + paidAmount);
//				WebElement secondTextView = driver.findElement(AppiumBy.xpath(
//					    "//android.widget.ScrollView/android.widget.LinearLayout/androidx.appcompat.widget.LinearLayoutCompat[3]//android.widget.TextView[2]"
//					));
//
//					// Extract and clean the text
//					String text = secondTextView.getText().trim().replace("$", ""); // Remove currency symbols if present
//					System.out.println("Second TextView Text: " + text);
//
//					// Convert to double
//					double value = Double.parseDouble(text);
//
//					// Subtract from paidAmount
//					paidAmount -= value;
//
//					System.out.println("Updated paidAmount: " + paidAmount);
//				System.out.println(paidAmount - refundAmount);
//				if ((paidAmount - refundAmount) <= 0.3) {
//					
//					//wait.until(ExpectedConditions.elementToBeClickable(homeButton)).click();
//					//System.out.println(paidAmount - refundAmount);
//					return true;
//
//				} else {
//					return false;
//				}
//			} catch (Exception e) {
//				System.out.println("Error during final check: " + e.getMessage());
//				return false;
//			}
		} catch (Exception e) {
			System.out.println("Exception in cashRefund method: " + e.getMessage());
			return false;
		}
	}
	
	public Boolean cashRefund()  {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(orderType)).click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			OrderId = wait.until(ExpectedConditions.visibilityOf(RefundOrderId)).getText();
			System.out.println(OrderId);
			wait.until(ExpectedConditions.elementToBeClickable(issueRefund)).click();
			System.out.println("Refund Initiated");

			try {
				wait.until(ExpectedConditions.visibilityOf(cashRefund));
				Thread.sleep(10);
				wait.until(ExpectedConditions.elementToBeClickable(done)).click();
				
				  Thread.sleep(1000);
				System.out.println("Clicked Done");
				wait.until(ExpectedConditions.elementToBeClickable(done)).click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				Thread.sleep(10);	
				System.out.println("Clicked Done");
				wait.until(ExpectedConditions.elementToBeClickable(saveRefund)).click();

			} catch (Exception e) {
				System.out.println("Error during refund process: " + e.getMessage());
				return false;
			}

			try {
				System.out.println("Final Check");
				WebElement refundedValueParentElem = wait.until(ExpectedConditions.visibilityOf(refundedValueParent));
				List<WebElement> textViews = refundedValueParentElem
						.findElements(AppiumBy.className("android.widget.TextView"));
				refundAmount = Double.parseDouble(textViews.get(1).getText().trim().replace("$", ""));
				System.out.println("Refunded Amount: " + refundAmount);

				WebElement totalValueParentElem = wait.until(ExpectedConditions.visibilityOf(totalValueParent));
				List<WebElement> textView = totalValueParentElem
						.findElements(AppiumBy.className("android.widget.TextView"));
				double paidAmount = Double.parseDouble(textView.get(1).getText().trim().replace("$", ""));
				System.out.println("Paid Amount: " + paidAmount);
				WebElement secondTextView = driver.findElement(AppiumBy.xpath(
					    "//android.widget.ScrollView/android.widget.LinearLayout/androidx.appcompat.widget.LinearLayoutCompat[3]//android.widget.TextView[2]"
					));

					// Extract and clean the text
					String text = secondTextView.getText().trim().replace("$", ""); // Remove currency symbols if present
					System.out.println("Second TextView Text: " + text);

					// Convert to double
					double value = Double.parseDouble(text);

					// Subtract from paidAmount
					paidAmount -= value;

					System.out.println("Updated paidAmount: " + paidAmount);
				System.out.println(paidAmount - refundAmount);
				if ((paidAmount - refundAmount) <= 0.3) {
					
					//wait.until(ExpectedConditions.elementToBeClickable(homeButton)).click();
					//System.out.println(paidAmount - refundAmount);
					return true;

				} else {
					return false;
				}
			} catch (Exception e) {
				System.out.println("Error during final check: " + e.getMessage());
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception in cashRefund method: " + e.getMessage());
			return false;
		}
	}
	
	
	public Boolean cardRefund() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(orderType)).click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			OrderId = wait.until(ExpectedConditions.visibilityOf(RefundOrderId)).getText();
			System.out.println(OrderId);
			wait.until(ExpectedConditions.elementToBeClickable(issueRefund)).click();
			System.out.println("Refund Initiated");
			WebElement popUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
		  		      AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.LinearLayout\").instance(1)")
			    		  ));
			    	 Assert.assertTrue(popUpMessage.isDisplayed(), "Pop-up message is not displayed.");
			    	 
			    	 String connectionmsg = wait.until(ExpectedConditions.presenceOfElementLocated(
			    		     AppiumBy.id("com.pays.pos:id/tvMessage"))).getText();
			    	 
			    
//			    	 Assert.assertTrue(connectionmsg.contains("Please confirm if you want to void this transaction."), "Device Not Connected.");

			    		 WebElement okButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		    			      AppiumBy.id("com.pays.pos:id/tvSave") // Replace with the actual ID of the OK button
		 			  ));
			    	 okButton.click();
			    	 WebElement SecondpopUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
				  		      AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.LinearLayout\").instance(1)")
					    		  ));
					    	 Assert.assertTrue(SecondpopUpMessage.isDisplayed(), "Pop-up message is not displayed.");
					    	 
					    	 String confirmationmsg = wait.until(ExpectedConditions.presenceOfElementLocated(
					    		     AppiumBy.id("com.pays.pos:id/tvMessage"))).getText();
					    	 
					    
					    	 Assert.assertTrue(confirmationmsg.contains("Payment was successfully refunded."), "Device Not Connected.");

					    		 WebElement SaveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
				    			      AppiumBy.id("com.pays.pos:id/tvSave") // Replace with the actual ID of the OK button
				 			  ));
					    		 SaveButton.click();

//			try {
//				wait.until(ExpectedConditions.visibilityOf(cashRefund));
//
//				wait.until(ExpectedConditions.elementToBeClickable(done)).click();
//				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//				wait.until(ExpectedConditions.elementToBeClickable(done)).click();
//				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//				wait.until(ExpectedConditions.elementToBeClickable(saveRefund)).click();
//
//			} catch (Exception e) {
//				System.out.println("Error during refund process: " + e.getMessage());
//				return false;
//			}

			try {
				System.out.println("Final Check");
				WebElement refundedValueParentElem = wait.until(ExpectedConditions.visibilityOf(refundedValueParent));
				List<WebElement> textViews = refundedValueParentElem
						.findElements(AppiumBy.className("android.widget.TextView"));
				refundAmount = Double.parseDouble(textViews.get(1).getText().trim().replace("$", ""));
				System.out.println("Refunded Amount: " + refundAmount);

				WebElement totalValueParentElem = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/linear_summary\"]/android.widget.LinearLayout/androidx.appcompat.widget.LinearLayoutCompat[4]")));
				List<WebElement> textView = totalValueParentElem
						.findElements(AppiumBy.className("android.widget.TextView"));
				double paidAmount = Double.parseDouble(textView.get(1).getText().trim().replace("$", ""));
				System.out.println("Paid Amount: " + paidAmount);
				WebElement secondTextView = driver.findElement(AppiumBy.xpath(
					    "//android.widget.ScrollView/android.widget.LinearLayout/androidx.appcompat.widget.LinearLayoutCompat[3]//android.widget.TextView[2]"
					));

					// Extract and clean the text
					String text = secondTextView.getText().trim().replace("$", ""); // Remove currency symbols if present
					System.out.println("Second TextView Text: " + text);

					// Convert to double
					double value = Double.parseDouble(text);

					// Subtract from paidAmount
					paidAmount -= value;

					System.out.println("Updated paidAmount: " + paidAmount);
				System.out.println(paidAmount - refundAmount);
				if ((paidAmount - refundAmount) <= 0.3) {
					
					//wait.until(ExpectedConditions.elementToBeClickable(homeButton)).click();
					//System.out.println(paidAmount - refundAmount);
					return true;

				} else {
					return false;
				}
			} catch (Exception e) {
				System.out.println("Error during final check: " + e.getMessage());
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception in cashRefund method: " + e.getMessage());
			return false;
		}
	}
	public void PrintCustomerReceipt() {
		wait.until(ExpectedConditions.elementToBeClickable(PrintCustomerReceipt)).click();

	}
	
	public void PrintKitchenReceipt() {
		wait.until(ExpectedConditions.elementToBeClickable(PrintKitchenReceipt)).click();

	}
	
	public void TextCustomerReceipt() {
		wait.until(ExpectedConditions.elementToBeClickable(TextCustomerReciept)).click();
		WebElement mobileNumberElement = wait.until(ExpectedConditions.visibilityOf(MobileNo));
        mobileNumberElement.sendKeys("9729990159");
        wait.until(ExpectedConditions.elementToBeClickable(MobileNoSend)).click();
	}
	
	public void ClickHome() {
		wait.until(ExpectedConditions.elementToBeClickable(homeButton)).click();
	}
	
	///  
	 public void Click_AddTip() {
        wait.until(ExpectedConditions.elementToBeClickable(AddTipButton)).click();
    }
	 // Method to scroll to a specific tip and retrieve its percentage
	    public BigDecimal scrollToElement(String TipName) {
	        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
	                AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).setAsHorizontalList().scrollIntoView(new UiSelector().text(\"" + TipName + "\"))")));
	        String TipPercentage = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtValue\"])[3]"))).getText();
	        System.out.println(TipPercentage);
	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//androidx.appcompat.widget.LinearLayoutCompat[@resource-id=\"com.pays.pos:id/linearParent\"])[3]"))).click();
	        return new BigDecimal(TipPercentage.replace("%", "")).divide(BigDecimal.valueOf(100));
	    
	    }
	    // Method to continue after adding a tip
	    
	    public void ContinueTip() {
	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/txtSave"))).click();
	    }
	  
	    public void SelectTip() {
	    	wait.until(ExpectedConditions.elementToBeClickable(TwentyPercentTip)).click();
	    }
	    
	    
	    // Loyalty Program 
	    
	    public BigDecimal getLoyalty() {
	    	BigDecimal LoyaltyB = getFormattedValue(LoyaltyBalance);
	    	System.out.println(LoyaltyB);
			return LoyaltyB;
	    }
	    
	    public BigDecimal getAppliedLoyaltyPoints() {
	    	BigDecimal LoyaltyPoint = getFormattedValue(LoyaltyPoints);
	    	System.out.println(LoyaltyPoint);
			return LoyaltyPoint;
	    }
	    
	    public void ApplyLoyalty()
	    {
	    	wait.until(ExpectedConditions.elementToBeClickable(LoyaltyPointCheckBox)).click();
	    }
	    
	    public Boolean ValidateloyaltyBalance(BigDecimal Balance , BigDecimal Points) {
	    	BigDecimal AfterApplyingLoyaltyP = getLoyalty();
	      	System.out.println(AfterApplyingLoyaltyP);
	      	System.out.println(Balance.subtract(Points));
	     // Use compareTo() to compare BigDecimal values
	        if (Balance.subtract(Points).compareTo(AfterApplyingLoyaltyP) == 0) {
	            System.out.println("Successfully applied Loyalty Points");
	            return true;
	        }
	    	
	    	return false;
	    }
	    
	    public boolean validateAfterLoyaltyFinancialDetails(BigDecimal TipValue,BigDecimal subtotal) {
			try {
				// Extract text values and clean non-numeric characters
			    BigDecimal appliedLP = getFormattedValue(AppliedLoyaltyPoint); 
				BigDecimal tax = getFormattedValue(taxElement);
				BigDecimal serviceCharge = getFormattedValue(serviceChargeElement);
				BigDecimal discount = getFormattedValue(discountElement);
				BigDecimal surcharge = getFormattedValue(surchargeElement);
				BigDecimal total = getFormattedValue(totalElement);
				BigDecimal cardValue = getFormattedValue(cardElement);
				BigDecimal cashValue = getFormattedValue(cashElement);
				BigDecimal cardtip = getFormattedValue(CardTip);
				BigDecimal cashtip = getFormattedValue(CashTip);
				

				// Step 1: Calculate Cash Tip (Tip applied on base total)
				BigDecimal baseTotal = subtotal.add(tax).add(serviceCharge).subtract(discount).subtract(appliedLP);
				BigDecimal CashTip = baseTotal.multiply(TipValue).setScale(2, RoundingMode.HALF_UP);
				
				// Step 2: Calculate Expected Cash Total
				BigDecimal expectedCashTotal = baseTotal.add(CashTip).setScale(2, RoundingMode.HALF_UP);

				// Step 3: Calculate Card Tip (Tip applied to base total before adding
				// surcharge)
				BigDecimal cardTip = baseTotal.multiply(TipValue).setScale(2, RoundingMode.HALF_UP);

				// Step 4: Calculate Expected Card Total (Add surcharge at the end)
				BigDecimal expectedCardTotal = baseTotal.add(cardTip).add(surcharge).setScale(2, RoundingMode.HALF_UP);

				// Debugging - Print Values
				System.out.println("Expected Cash Total: " + expectedCashTotal);
				System.out.println("Actual Cash Value: " + cashValue);
				System.out.println("Expected Card Total: " + expectedCardTotal);
				System.out.println("Actual Card Value: " + cardValue);

				// Assertions for Validation
				Assert.assertEquals(cashValue.setScale(2, RoundingMode.HALF_UP), expectedCashTotal,
						"Cash value does not match expected cash total");

				BigDecimal tolerance = new BigDecimal("0.30");

				// Check if actual card value is within ±0.3 range of expected card total
				BigDecimal lowerBound = expectedCardTotal.subtract(tolerance);
				BigDecimal upperBound = expectedCardTotal.add(tolerance);

				Assert.assertTrue(cardValue.compareTo(lowerBound) >= 0 && cardValue.compareTo(upperBound) <= 0,
						"Card value does not match expected card total within tolerance of 0.3. " + "Expected: "
								+ expectedCardTotal + ", Actual: " + cardValue);

			} catch (Exception e) {
				System.out.println("Error capturing financial details: " + e.getMessage());
			}
			return true;
		}

		 public void MenuBurgerclick() throws InterruptedException {
	    	 Thread.sleep(10);
	    	 WebElement sellCard = wait.until(ExpectedConditions.presenceOfElementLocated(
		                AppiumBy.androidUIAutomator(
		                        "new UiScrollable(new UiSelector().className(\"androidx.recyclerview.widget.RecyclerView\"))" +
		                        ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtCategoryName\").text(\"Sell Card\"))"
		                )
		        ));
	    	 wait.until(ExpectedConditions.visibilityOf(MenuBurgerButton)).click();  	
	    }
		 public void CashLogButtonClick() {
			 wait.until(ExpectedConditions.visibilityOf(CashLogButton)).click();  
		    }
		 
		 
		 public Boolean ValidateCashLogRefund() {
			 boolean flag = true;
			// Locate the first order container inside RecyclerView
			 WebElement firstOrder = driver.findElement(AppiumBy.xpath(
			     "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvOpenOrder']"
			     + "/androidx.appcompat.widget.LinearLayoutCompat[1]/android.widget.LinearLayout"
			 ));
    
			 // Extract Order ID
			 WebElement orderIdElement = firstOrder.findElement(AppiumBy.xpath(
			     ".//android.widget.TextView[@resource-id='com.pays.pos:id/txtOrderId']"
			 ));
			 String cashlogorderID = orderIdElement.getText();
			 
			 System.out.println("Order ID: " + cashlogorderID);

			 // Extract Price (Dynamic)
			 WebElement priceElement = firstOrder.findElement(AppiumBy.xpath(
			     ".//android.widget.TextView[contains(@text, '$')]"
			 ));
			 String price = priceElement.getText().trim().replace("$", "");;
			  // Remove currency symbols if present
		

				// Convert to double
				double amount = Double.parseDouble(price);
			    System.out.println("Order Price: " + amount);
			  

			 // Extract Reason
			 WebElement reasonElement = firstOrder.findElement(AppiumBy.xpath(
			     ".//android.widget.TextView[@resource-id='com.pays.pos:id/txt_reason']"
			 ));
			 String reason = reasonElement.getText();
			 System.out.println("Reason: " + reason);
			 
			 if (OrderId.equals(cashlogorderID)) {
				    System.out.println("OrderID equal");
				    flag = true;

				    if (Double.compare(amount, refundAmount) > 0) {
				        System.out.println("Amount is greater than Refund Amount");
				    }

				    if (reason.equals("Refund Intiated")) {
				        System.out.println("Reason not same");
				    } else {
				        System.out.println("Reason is same");
				    }

				} else {
				    System.out.println("OrderID not equal");
				    flag = false;
				}
			 
			 
			 if(flag) {
				 return true;
			 }
			
			 return false;
		 }
}
