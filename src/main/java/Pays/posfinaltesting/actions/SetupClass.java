package Pays.posfinaltesting.actions;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Pays.posfinaltest.pageobjectlocators.SetupPageObject;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;

public class SetupClass {
    private AppiumDriver driver;
    private WebDriverWait wait;
    private SetupPageObject Setup;
    private TransactionClass tc;

    public SetupClass(AppiumDriver driver) {
        this.driver = driver;
        this.Setup = new SetupPageObject(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
        this.tc = new TransactionClass(driver);// Explicit wait of 10 sec
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait of 10 sec
        PageFactory.initElements(new AppiumFieldDecorator(driver), this); // Initialize PageFactory elements
    }

    // Locators for various elements on the UI
    @AndroidFindBy(id= "com.pays.pos:id/linearSettings")
    private WebElement linearSetting;

    @AndroidFindBy(xpath ="//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtTitle\" and @text=\"Tips\"]")
    private WebElement tipsButton;

    @AndroidFindBy(id ="com.pays.pos:id/txtAddNewTip")
    private WebElement AddNewTip;

    @AndroidFindBy(id="com.pays.pos:id/txtSave")
    private WebElement SaveButton;

    @AndroidFindBy(id="com.pays.pos:id/imgBack")
    private WebElement GoBack;

    @AndroidFindBy(id="com.pays.pos:id/tvAddTip")
    private WebElement AddTipButton;
    
 // ---------------------- TAXES Locators ----------------------
    
    @AndroidFindBy(xpath ="//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtTitle\" and @text=\"Taxes\"]")
    private WebElement TaxButton;
    
    @AndroidFindBy(id="com.pays.pos:id/etName")
    private WebElement NewTaxName;
    
    @AndroidFindBy(id="com.pays.pos:id/edtAmount")
    private WebElement NewTaxAmt;
    
    @AndroidFindBy(id="com.pays.pos:id/img_dropdown")
    private WebElement DropDownTax;
    // ---------------------- DISCOUNT Locators ----------------------
  
    
    
    
    // Method to click on the Menu Burger icon
    public void MenuBurgerClick() throws InterruptedException {
        Thread.sleep(10);  // Short delay before interaction
        WebElement sellCard = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().className(\"androidx.recyclerview.widget.RecyclerView\"))" +
                        ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtCategoryName\").text(\"Sell Card\"))"
                )
        ));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.pays.pos:id/imgDrawer")
        )).click();
    }
   
    // Method to click on Setup Button
    public void SetupButton() {
        wait.until(ExpectedConditions.elementToBeClickable(linearSetting)).click();
    }

    // Method to click on Tips Button
    public void TipsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(tipsButton)).click();
    }

    // Method to add a new tip
    public void AddNewTips() {
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.pays.pos:id/txtAddNewTip']"))).click();
    }

    // Method to set tip values (Name and Amount)
    public void SetTipvalues(String TipName, String TipAmt) {
        WebElement Name = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.EditText[@text=\"Tip Name *\"]")));
        Name.sendKeys(TipName);
        WebElement Amt = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.pays.pos:id/edt_tip")));
        Amt.sendKeys(TipAmt);
        wait.until(ExpectedConditions.elementToBeClickable(SaveButton)).click();
    }

    // Method to check the limit for adding tips
    public boolean CheckAddTipLimit() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement popUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.LinearLayout\").instance(1)")
        ));
        Assert.assertTrue(popUpMessage.isDisplayed(), "Pop-up message is not displayed.");
        String connectionmsg = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("com.pays.pos:id/tvMessage"))).getText();
        Assert.assertTrue(connectionmsg.contains("You can add upto 4 tip suggestions only."), "Limit of 4 is not Applicable");
        WebElement okButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("com.pays.pos:id/tvSave")
        ));
        okButton.click();
        return true;
    }

    // Method to navigate back
    public void GoBack() {
        wait.until(ExpectedConditions.elementToBeClickable(GoBack)).click();
    }

    // Method to delete a tip
    public void DeleteTip() {
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Menu\"])[4]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/content\"])[2]"))).click();
        WebElement popUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.LinearLayout\").instance(1)")
        ));
        Assert.assertTrue(popUpMessage.isDisplayed(), "Pop-up message is not displayed.");
        String connectionmsg = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("com.pays.pos:id/tvMessage"))).getText();
        Assert.assertTrue(connectionmsg.contains("This is an Active Tip.Are you sure you want to delete this Tip?"), "Limit of 4 is not Applicable");
        WebElement okButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("com.pays.pos:id/tvSave")
        ));
        okButton.click();
    }

    // Method to confirm tip deletion
    public void TipDeletionConfirmation() {
        MsgConfirmation("Tip was successfully deleted.");
    }

    // Method to confirm tip addition
    public void TipAdditionConfirmation() {
        MsgConfirmation("Tip was successfully created.");
    }

    // Common method to confirm tip actions
    private void MsgConfirmation(String expectedMessage) {
        WebElement popUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.LinearLayout\").instance(1)") 
        ));
        Assert.assertTrue(popUpMessage.isDisplayed(), "Pop-up message is not displayed.");
        String connectionmsg = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("com.pays.pos:id/tvMessage"))).getText();
        Assert.assertTrue(connectionmsg.contains(expectedMessage), expectedMessage + " was not displayed.");
        WebElement okButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("com.pays.pos:id/tvSave")
        ));
        okButton.click();
    }

    // Method to navigate to the home section
    public void GoHome() {
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/txtHome"))).click();
    }

    // Method to click the Add Tip button
    public void Click_AddTip() {
        wait.until(ExpectedConditions.elementToBeClickable(AddTipButton)).click();
    }

    // Method to scroll to a specific tip and retrieve its percentage
    public BigDecimal scrollToElement(String TipName) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).setAsHorizontalList().scrollIntoView(new UiSelector().text(\"" + TipName + "\"))")));
        String TipPercentage = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtValue\"])[3]"))).getText();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//androidx.appcompat.widget.LinearLayoutCompat[@resource-id=\"com.pays.pos:id/linearParent\"])[3]"))).click();
        return new BigDecimal(TipPercentage.replace("%", "")).divide(BigDecimal.valueOf(100));
    }

    // Method to continue after adding a tip
    public void ContinueTip() {
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/txtSave"))).click();
    }

	public void backtohome() {
		wait.until(ExpectedConditions.visibilityOf(SaveButton)).click();
	}
	
	
	
	
	
	 // ---------------------- TAXES TEST Functions----------------------	
		public void TaxButton() {
			wait.until(ExpectedConditions.elementToBeClickable(TaxButton)).click();
		}

		public void AddNewTax() {
			wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/txtNewTax"))).click();
		}

		public void SetTax(String TaxName, String TaxAmt) {
			  WebElement Name = wait.until(ExpectedConditions.elementToBeClickable(NewTaxName));
		        Name.sendKeys(TaxName);
		        WebElement Amt = wait.until(ExpectedConditions.elementToBeClickable(NewTaxAmt));
		        Amt.sendKeys(TaxAmt);
		        
		}
		public void  SaveNewTax() {
	        wait.until(ExpectedConditions.elementToBeClickable(SaveButton)).click();
	       
		}
		
		public void TaxAdditionConfirmation() throws InterruptedException {
			 Thread.sleep(7000);
	        MsgConfirmation("Tax was successfully created.");
	    }
		
		public void EditTax() {
			 wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Menu\"])[1]"))).click();
		        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/content\"])[1]"))).click();
		}
		public void TaxUpdationConfirmation() throws InterruptedException {
			 Thread.sleep(5000);
	         MsgConfirmation("Tax was successfully updated.");
	    }
		
		public void CheckTax(String TaxName,String TaxAmt) {
			wait.until(ExpectedConditions.elementToBeClickable(DropDownTax)).click();
			List<WebElement> linearLayouts = driver.findElements(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rv_tax']/android.widget.LinearLayout"));

			boolean isFound = false; // Flag to track if the condition is met

			// Loop through each LinearLayout
			for (int i = 0; i < linearLayouts.size(); i++) {
			    // Get all TextViews inside each LinearLayout
			    List<WebElement> textViews = linearLayouts.get(i).findElements(AppiumBy.xpath(".//android.widget.TextView"));
			    
			    System.out.println("LinearLayout " + (i + 1) + ":");
			    
			    boolean containsEmp = false;
			    boolean containsNegativeEightPercent = false;

			    // Loop through each TextView and check for the required values
			    for (WebElement textView : textViews) {
			        String text = textView.getText().trim();
			        System.out.println("  - " + text);
			        
			        if (text.equals(TaxName)) {
			            containsEmp = true;
			        }
			        if (text.equals(TaxAmt)) {
			            containsNegativeEightPercent = true;
			        }
			    }

			    // If both conditions are met, set flag to true and break the loop
			    if (containsEmp && containsNegativeEightPercent) {
			        isFound = true;
			        System.out.println("Found LinearLayout containing "+TaxName+" and "+ TaxAmt+ " at index: " + (i + 1));
			        break; // Stop searching once found
			    }
			}

			// Assertion to validate test case
			Assert.assertTrue(isFound, "No LinearLayout contains both "+TaxName+" and "+ TaxAmt+ "");
		}
		
		public void DeleteTax() {
			 wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Menu\"])[1]"))).click();
		        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/content\"])[2]"))).click();
		        WebElement popUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
		                AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.LinearLayout\").instance(1)")
		        ));
		        Assert.assertTrue(popUpMessage.isDisplayed(), "Pop-up message is not displayed.");
		        String connectionmsg = wait.until(ExpectedConditions.presenceOfElementLocated(
		                AppiumBy.id("com.pays.pos:id/tvMessage"))).getText();
		        Assert.assertTrue(connectionmsg.contains("This is an active Tax.Are you sure you want to delete this Tax?"), "Not able to delete the tax");
		        WebElement okButton = wait.until(ExpectedConditions.presenceOfElementLocated(
		                AppiumBy.id("com.pays.pos:id/tvSave")
		        ));
		        okButton.click();
		        MsgConfirmation("Tax was successfully deleted");
		}
		
		
		
		
		 // ---------------------- DISCOUNT TEST Functions----------------------	
		
		
			public void DiscountButton() {
				wait.until(ExpectedConditions.elementToBeClickable(Setup.DiscountButton)).click();
			}

			public void AddNewDiscount() {
				wait.until(
						ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/txtCreateDiscount")))
						.click();
			}

			public void SetDiscountvalues(String DiscountName, String DiscountAmt) {
				WebElement Name = wait.until(ExpectedConditions
				        .elementToBeClickable(AppiumBy.xpath("//android.widget.EditText[contains(@text, 'Discount Name') or contains(@text, 'Family Discount')]")));

				Name.sendKeys(DiscountName);
				WebElement Amt = wait
						.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.pays.pos:id/edt_discount")));
				Amt.sendKeys(DiscountAmt);
				wait.until(ExpectedConditions.elementToBeClickable(SaveButton)).click();
			}

			public void DiscountAdditionConfirmation() throws InterruptedException {
				Thread.sleep(10);
				MsgConfirmation("Discount was successfully created.");
			}

			public void EditDiscount() {
//			 wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Menu\"])[1]"))).click();
//		        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/content\"])[1]"))).click();
				// Find all rows in RecyclerView
				WebDriverWait wait =    new WebDriverWait(driver, Duration.ofSeconds(2)); 
				List<WebElement> rows = driver.findElements(AppiumBy.xpath(
						"//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvDiscountList']/androidx.appcompat.widget.LinearLayoutCompat"));

				// Loop through each row
				for (WebElement row : rows) {
					try {
						// Find "Family Restaurant" TextView inside the row
						WebElement textView = row
								.findElement(AppiumBy.xpath(".//android.widget.TextView[@text='Family Discount']"));

						// Find the corresponding "Menu" ImageView inside the same row
						WebElement imageView = row
								.findElement(AppiumBy.xpath(".//android.widget.ImageView[@content-desc='Menu']"));

						// Click the menu icon
						if (textView != null && imageView != null) {
							imageView.click();
							wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(
									"(//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/content\"])[1]")))
									.click();
							System.out.println("Clicked on the menu icon for Family Restaurant.");
							break; // Stop after clicking the first matching row
						}
					} catch (Exception e) {
						// Continue if the row doesn't contain the target elements
						continue;
					}
				}
			}

			public void DiscountUpdateConfirmation() throws InterruptedException {
				Thread.sleep(10);
				MsgConfirmation("Discount was successfully updated.");
			}
			
			public void CheckDiscount() {
			    int index = 4; // Index of the ImageView to interact with

			    try {
			        // ✅ Locate the 4th ImageView
			        WebElement imageView = driver.findElement(AppiumBy.xpath(
			            "(//android.widget.ImageView[@resource-id='com.pays.pos:id/imgCheckBox'])[" + index + "]"
			        ));

			        // ✅ Retrieve the "checked" or "selected" attribute
			        String checkedState = imageView.getAttribute("checked"); // Use the appropriate attribute

			        // Debugging: Print the attribute value
			        System.out.println("Checked state of ImageView at index " + index + ": " + checkedState);

			        // ✅ Check if the ImageView is already checked
			        if (checkedState == null || !checkedState.equalsIgnoreCase("true")) {
			            // ✅ Click on the ImageView to check it
			            imageView.click();
			            System.out.println("✅ Clicked on ImageView at index " + index + " to check it.");
			        } else {
			            System.out.println("✅ ImageView at index " + index + " is already checked.");
			        }
			    } catch (Exception e) {
			        System.out.println("❌ Failed to interact with ImageView at index " + index + ": " + e.getMessage());
			        throw e; // Re-throw the exception to fail the test
			    }
			}
			public void AddDiscountButton() {
				wait.until(ExpectedConditions.elementToBeClickable(Setup.PaymentMenu)).click();
				wait.until(ExpectedConditions.elementToBeClickable(Setup.SelectAddDiscount)).click();
			}
			
			public void scrollToDiscountElement(String DiscountName) {
		        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
		                AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).setAsHorizontalList().scrollIntoView(new UiSelector().text(\"" + DiscountName + "\"))")));
		        
		       element.click();
		       
		        
		    }
			
			public void SavePayMenu() {
				wait.until(ExpectedConditions.elementToBeClickable(SaveButton)).click();
			}
			public BigDecimal getSubtotal() {
				 BigDecimal subtotal = tc.getFormattedValue(tc.subtotalElement);
				return subtotal;
			}
			
			
			public void DeleteDiscount(String DiscountName) {
//				 wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Menu\"])[1]"))).click();
//			        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/content\"])[1]"))).click();
				WebDriverWait wait =    new WebDriverWait(driver, Duration.ofSeconds(2)); 
				List<WebElement> rows = driver.findElements(AppiumBy.xpath(
						"//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvDiscountList']/androidx.appcompat.widget.LinearLayoutCompat"));

				// Loop through each row
				for (WebElement row : rows) {
					try {
						// Find "Family Restaurant" TextView inside the row
						WebElement textView = row
								.findElement(AppiumBy.xpath(".//android.widget.TextView[@text='"+DiscountName+"']"));

						// Find the corresponding "Menu" ImageView inside the same row
						WebElement imageView = row
								.findElement(AppiumBy.xpath(".//android.widget.ImageView[@content-desc='Menu']"));

						// Click the menu icon
						if (textView != null && imageView != null) {
							imageView.click();
							wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(
									"(//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/content\"])[2]")))
									.click();
							System.out.println("Clicked on the menu icon for Family Restaurant.");
							break; // Stop after clicking the first matching row
						}
					} catch (Exception e) {
						// Continue if the row doesn't contain the target elements
						continue;
					}
				}
				}
			public void SelectPredefinedDiscount() {
				wait.until(ExpectedConditions.elementToBeClickable(Setup.TwentyPercentDiscount)).click();
			}
			
			public void DiscountDeleteConfirmation() throws InterruptedException {
				Thread.sleep(10);
				MsgConfirmation("This is an active Discount.Are you sure you want to delete this Discount?");
				MsgConfirmation("Discount was successfully deleted");
				
			}
			 // ---------------------- ORDER NOTE TEST Functions----------------------	
			public void OrderNoteButton() {
				wait.until(ExpectedConditions.elementToBeClickable(Setup.OrderNoteButton)).click();
			}
			
			public void AddNewOrderNote() {
				wait.until(ExpectedConditions.elementToBeClickable(Setup.AddOrderNote)).click();;
			}
			
			public void SetOrderNote(String OrderNote ) {
				WebElement Name = wait.until(ExpectedConditions
				        .elementToBeClickable(AppiumBy.xpath("//android.widget.EditText[@text=\"Note Title*\"]")));

				Name.sendKeys(OrderNote);
				wait.until(ExpectedConditions.elementToBeClickable(SaveButton)).click();
			}

			public void OrderNoteAdditonConfirmation() throws InterruptedException {
				Thread.sleep(5000);
				MsgConfirmation("Note was successfully created.");
			}
			
			public void DragandDropNote(String Note) throws Exception {
			    try {
			        JavascriptExecutor js = (JavascriptExecutor) driver;

			        // ✅ Scroll until the note is visible (with a maximum scroll limit)
			        boolean isFound = false;
			        int maxScrollAttempts = 10; // Maximum number of scroll attempts
			        int scrollAttempts = 0;

			        while (!isFound && scrollAttempts < maxScrollAttempts) {
			            try {
			                driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='" + Note + "']"));
			                isFound = true;
			            } catch (Exception e) {
			                HashMap<String, String> scrollParams = new HashMap<>();
			                scrollParams.put("direction", "down");
			                js.executeScript("mobile: scroll", scrollParams);
			                scrollAttempts++;
			            }
			        }

			        if (!isFound) {
			            throw new Exception("❌ Note '" + Note + "' not found after " + maxScrollAttempts + " scroll attempts!");
			        }
			        System.out.println("✅ '" + Note + "' is now visible!");

			        // ✅ Find all LinearLayoutCompat elements in the parent container
			        List<WebElement> linearLayouts = driver.findElements(AppiumBy.xpath(
			            "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvNoteLise']/androidx.appcompat.widget.LinearLayoutCompat"
			        ));

			        // ✅ Iterate through the list to find the index of the LinearLayoutCompat containing the note
			        int sourceIndex = -1;
			        for (int i = 0; i < linearLayouts.size(); i++) {
			            try {
			                // Check if the current LinearLayoutCompat contains the note TextView
			                linearLayouts.get(i).findElement(AppiumBy.xpath(".//android.widget.TextView[@text='" + Note + "']"));
			                sourceIndex = i + 1; // XPath indices start from 1
			                break;
			            } catch (Exception e) {
			                // Continue searching
			            }
			        }

			        if (sourceIndex == -1) {
			            throw new Exception("❌ LinearLayoutCompat containing '" + Note + "' not found!");
			        }

			        System.out.println("✅ Found LinearLayoutCompat containing '" + Note + "' at index: " + sourceIndex);

			        // ✅ Use the index to locate the source element
			        WebElement sourceElement = driver.findElement(AppiumBy.xpath(
			            "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvNoteLise']/androidx.appcompat.widget.LinearLayoutCompat[" + sourceIndex + "]"
			        ));

			        // ✅ Locate the target (3rd position)
			        WebElement targetElement = driver.findElement(AppiumBy.xpath(
			            "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvNoteLise']/androidx.appcompat.widget.LinearLayoutCompat[3]"
			        ));

			        // Debugging: Print source and target element details
			        System.out.println("Source Element: " + sourceElement.getAttribute("resource-id"));
			        System.out.println("Target Element: " + targetElement.getAttribute("resource-id"));

			        // ✅ Drag using JavaScript Executor
			        HashMap<String, Object> dragParams = new HashMap<>();
			        dragParams.put("elementId", ((RemoteWebElement) sourceElement).getId()); // Pass WebElement ID
			        dragParams.put("endX", targetElement.getLocation().getX());
			        dragParams.put("endY", targetElement.getLocation().getY());
			        dragParams.put("duration", 1000); // Drag duration in ms

			        js.executeScript("mobile: dragGesture", dragParams);

			        System.out.println("✅ Dragging completed using JavaScript Executor!");
			    } catch (Exception e) {
			        System.out.println("❌ Drag-and-drop failed: " + e.getMessage());
			        throw e; // Re-throw the exception to fail the test
			    }
			}
			
			public void OrderNotePositionConfirmation(){	
				MsgConfirmation("Note position was successfully reordered.");
			}

			public void AddONote() {
			
			  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
				wait.until(ExpectedConditions.elementToBeClickable(Setup.PaymentMenu)).click();
				wait.until(ExpectedConditions.elementToBeClickable(Setup.SelectAddONote)).click();
				
			}
			public void PhoneOrderAddONote() {
				
				  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
					wait.until(ExpectedConditions.elementToBeClickable(Setup.PaymentMenu)).click();
					wait.until(ExpectedConditions.elementToBeClickable(Setup.SelectPhoneOrderAddONote)).click();
					
				}
				
			public void SelectONote(String NoteName) {
				// Locate the RecyclerView
//				List<WebElement> items = driver.findElements(AppiumBy.xpath(
//				    "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvNotes']/androidx.appcompat.widget.LinearLayoutCompat"
//				));
//
//				// Print the number of elements in RecyclerView
//				System.out.println("Number of elements in RecyclerView: " + items.size());
//
//				// Iterate through each LinearLayoutCompat and check for the "Extra Sweet" TextView
//				for (WebElement item : items) {
//				    try {
//				        WebElement textView = item.findElement(AppiumBy.xpath(".//android.widget.TextView[@text='"+NoteName+"']"));
//				        if (textView.isDisplayed()) {
//				            // Click on the parent LinearLayoutCompat containing "Extra Sweet"
//				            item.click();
//				            System.out.println("Clicked on '"+NoteName+"'");
//				            break;
//				        }
//				    } catch (NoSuchElementException e) {
//				      
//				    	System.out.println("No Element was found");
//				    }
//				}
				    Random rand = new Random();
	    	        int randomNote = rand.nextInt(4) + 1;  // Generates a number between 1 and 4

	    	        String xpath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvNotes']/androidx.appcompat.widget.LinearLayoutCompat[%d]/androidx.appcompat.widget.LinearLayoutCompat", randomNote);
	    	        
	    	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(xpath))).click();
	    	      
			}
			
			public boolean CheckIfONoteIsAdded() {
				// Locate the EditText field
				WebElement noteField = driver.findElement(AppiumBy.id("com.pays.pos:id/edtNote"));

				// Get the text from the field
				String noteText = noteField.getText();

				// Check if it contains "Extra Sweet"
				if (noteText.equals("Extra Sweet")) {
				    System.out.println("The note field contains 'Extra Sweet'.");
				    return true;
				} else {
				    System.out.println("The note field does NOT contain 'Extra Sweet'. Found: " + noteText);
				    return false;
				}

			}
			
			
			public void DeleteONote(String ONote) {
//				 wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Menu\"])[1]"))).click();
//			        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/content\"])[1]"))).click();
					// Find all rows in RecyclerView
					List<WebElement> rows = driver.findElements(AppiumBy.xpath(
							"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.pays.pos:id/rvNoteLise\"]/androidx.appcompat.widget.LinearLayoutCompat[1]/androidx.appcompat.widget.LinearLayoutCompat"));

					// Loop through each row
					for (WebElement row : rows) {
						try {
							// Find "Family Restaurant" TextView inside the row
							WebElement textView = row
									.findElement(AppiumBy.xpath(".//android.widget.TextView[@text='"+ONote+"']"));

							// Find the corresponding "Menu" ImageView inside the same row
							WebElement imageView = row
									.findElement(AppiumBy.xpath(".//android.widget.ImageView[@content-desc='Menu']"));

							// Click the menu icon
							if (textView != null && imageView != null) {
								imageView.click();
								wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(
										"(//android.widget.LinearLayout[@resource-id=\"com.pays.pos:id/content\"])[2]")))
										.click();
								System.out.println("Clicked on the menu icon for Family Restaurant.");
								break; // Stop after clicking the first matching row
							}
						} catch (Exception e) {
							// Continue if the row doesn't contain the target elements
							continue;
						}
					}
				}
			public void ONoteDeleteConfirmation() throws InterruptedException {
				Thread.sleep(10);
				MsgConfirmation("This is an active Note.Are you sure you want to delete this Note?");
				MsgConfirmation("Note was successfully deleted");
				
			}
		}