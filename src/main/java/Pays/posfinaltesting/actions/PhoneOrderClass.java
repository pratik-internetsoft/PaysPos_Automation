package Pays.posfinaltesting.actions;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Pays.posfinaltest.pageobjectlocators.PhoneOrderPageObject;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;


import java.util.Random;

public class PhoneOrderClass {
	 private AppiumDriver driver;
	  private WebDriverWait wait;
	  private Random random;
	  private PhoneOrderPageObject phoneOrder;
	  //Constructor to Phone Order class
	    public PhoneOrderClass(AppiumDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));// Explicit wait of 10 sec
	        this.random = new Random();
	        this.phoneOrder = new PhoneOrderPageObject(driver);
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	    }
	    
		
		
		// Scrollable container ID
	    private static final String SCROLLABLE_CONTAINER_ID = "com.pays.pos:id/scrollViewCustomer";
	    // Locators using AppiumBy
	    private static final String STREET_ID = "com.pays.pos:id/edtStreet";
	    private static final String SUITE_ID = "com.pays.pos:id/edtSuite";
	    private static final String CITY_ID = "com.pays.pos:id/edtCity";
	    private static final String STATE_ID = "com.pays.pos:id/edtState";
	    private static final String ZIP_ID = "com.pays.pos:id/edtZip";
	    
	    
		// Method to click on the "Pay Now" button
		// This method waits until the "Pay" button is visible before clicking on it
		public void Paynow() {
			wait.until(ExpectedConditions.visibilityOf(phoneOrder.Pay)).click();
		}

		// Method to click on the Main Layout
		// Ensures that the main layout element is clickable before performing the click
		// action
		public void clickMainLayout() {
			wait.until(ExpectedConditions.elementToBeClickable(phoneOrder.mainLayout)).click();
		}

		// Method to retrieve the text indicating whether the order is for Delivery or
		// Pickup
		// Waits until the text view is visible and then fetches the text content
		public String getDeliveryOrPickupText() {
			return wait.until(ExpectedConditions.visibilityOf(phoneOrder.deliveryOrPickupTextView)).getText();
		}

		// Method to update an existing order
		// Waits until the "Update Order" button is visible before clicking it
		public void UpdateOrder() {
			wait.until(ExpectedConditions.visibilityOf(phoneOrder.updateOrder)).click();
		}

		// Method to click on the "Phone Order" button
		// Adds an implicit wait before clicking the "Phone Order" button to ensure
		// smooth execution
		public void PhoneOrderButtonClick() {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			wait.until(ExpectedConditions.visibilityOf(phoneOrder.PhoneOrderButton)).click();
		}

		// Method to fill the Pickup Add Customer Form
		// This method generates random customer details such as name, surname, phone
		// number, and email
		public void AddCustomerForm() {
			// Arrays containing sample names, surnames, and email domains
			String[] names = { "John", "Emma", "Michael", "Sophia", "David" };
			String[] surnames = { "Anderson", "Miller", "Wilson", "Taylor", "Martinez" };
			String[] domains = { "gmail.com", "yahoo.com", "outlook.com", "hotmail.com" };

			// Generate a random first name and surname
			String randomName = names[new Random().nextInt(names.length)];
			String randomSurname = surnames[new Random().nextInt(surnames.length)];

			// Enter the randomly generated first name and surname into input fields
			phoneOrder.Firstname.sendKeys(randomName);
			phoneOrder.Surname.sendKeys(randomSurname);

			// Generate a random phone number following a (XXX) XXX-XXXX format
			Random random = new Random();
			int areaCode = 200 + random.nextInt(800); // Ensures a valid area code (200-999)
			int prefix = 100 + random.nextInt(900); // Generates a 3-digit prefix (100-999)
			int lineNumber = 1000 + random.nextInt(9000); // Generates a 4-digit line number (1000-9999)
			String randomPhoneNumber = String.format("(%d) %d-%d", areaCode, prefix, lineNumber);

			// Clear the existing phone number field before entering a new one
		
			wait.until(ExpectedConditions.visibilityOf(phoneOrder.PhoneOrderNext)).click();
			  WebElement popUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
	    	            AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.LinearLayout\").instance(1)")
	    	        ));

	    	        Assert.assertTrue(popUpMessage.isDisplayed(), "Pop-up message is not displayed.");

	    	        String connectionMsg = wait.until(ExpectedConditions.presenceOfElementLocated(
	    	            AppiumBy.id("com.pays.pos:id/tvMessage")
	    	        )).getText();

	    	        Assert.assertTrue(connectionMsg.contains("Please enter Mobile Number"), " Mobile Number is not Mandotary");

	    	        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
	    	            AppiumBy.id("com.pays.pos:id/tvSave") // Replace with actual ID
	    	        ));
	    	        okButton.click();
	    	    	phoneOrder.PhoneNumber.clear();
			phoneOrder.PhoneNumber.sendKeys(randomPhoneNumber);

			// Generate a random email address using the name and a random number
			int randomNumber = 1000 + random.nextInt(9000); // Generates a 4-digit random number
			String randomDomain = domains[random.nextInt(domains.length)];
			String randomEmail = randomName + randomNumber + "@" + randomDomain;

			// Enter the randomly generated email into the input field
			phoneOrder.EmailID.sendKeys(randomEmail);
		}

		// Method to click next after the customer form filling
		public void OrderNext() {
			wait.until(ExpectedConditions.visibilityOf(phoneOrder.PhoneOrderNext)).click();
		}

		// Method to select items from the categories
		public boolean selectRandomItems(int numItemsTotal) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
	        Random random = new Random();
	        int numItemsToAdd = random.nextInt(numItemsTotal) + 1;
	        System.out.println("Adding " + numItemsToAdd + " items.");

	        if (phoneOrder.categoryList.size() < 2) {
	            System.out.println("Not enough categories to skip the first one.");
	            return false;
	        }

	        for (int i = 0; i < numItemsToAdd; i++) {
	            try {
	            	List<WebElement> categoriesExcludingFirst = phoneOrder.categoryList.subList(1, phoneOrder.categoryList.size());
	            	int categoryIndex = random.nextInt(categoriesExcludingFirst.size());
	            	WebElement category = categoriesExcludingFirst.get(categoryIndex);
//	                int categoryIndex = random.nextInt(phoneOrder.categoryList.size() - 1) + 1;
//	                WebElement category = phoneOrder.categoryList.get(categoryIndex);

	                if (category.isDisplayed() && category.isEnabled()) {
	                    category.click();
	                } else {
	                    wait.until(ExpectedConditions.elementToBeClickable(category)).click();
	                }
	                System.out.println("Category selected (excluding the first one).");

	                if (phoneOrder.itemList.isEmpty()) {
	                    System.out.println("No items in this category.");
	                    continue;
	                }

	                int itemIndex = random.nextInt(phoneOrder.itemList.size());
	                WebElement item = phoneOrder.itemList.get(itemIndex);

	                if (item.isDisplayed() && item.isEnabled()) {
	                    item.click();
	                } else {
	                    wait.until(ExpectedConditions.elementToBeClickable(item)).click();
	                }
	                System.out.println("Item selected.");

	                if (!phoneOrder.modifierList.isEmpty()) {
	                    int modifierIndex1 = random.nextInt(phoneOrder.modifierList.size());
	                    int modifierIndex2;
	                    do {
	                        modifierIndex2 = random.nextInt(phoneOrder.modifierList.size());
	                    } while (modifierIndex1 == modifierIndex2);

	                    phoneOrder.modifierList.get(modifierIndex1).click();
	                    phoneOrder.modifierList.get(modifierIndex2).click();
	                    System.out.println("Modifiers selected.");
	                } else {
	                    System.out.println("No modifiers found.");
	                }

	                if (phoneOrder.doneButton.isDisplayed() && phoneOrder.doneButton.isEnabled()) {
	                	phoneOrder.doneButton.click();
	                } else {
	                    wait.until(ExpectedConditions.elementToBeClickable(phoneOrder.doneButton)).click();
	                }
	                System.out.println("Item added.");

	            } catch (Exception e) {
	                System.out.println("Error during item selection: " + e.getMessage());
	            }
	        }
	        return true;
	    }

		// Method to save the order
		public void ClickOrderSave() {
			wait.until(ExpectedConditions.visibilityOf(phoneOrder.OrderSave)).click();
		}

		
		// Method to update items from category 'cat2'
		// This method selects a random number of items (between 2 and 3) from 'cat2',
		// updates them with modifiers, and saves the changes
		public boolean updateItems() {
			int numItemsToUpdate = random.nextInt(2) + 1; // Random number between 2 and 3
			System.out.println("Updating " + numItemsToUpdate + " items from category 'cat2'.");

			try {
				// Wait for 'cat2' category to be clickable and select it
				wait.until(ExpectedConditions.elementToBeClickable(phoneOrder.cat2Category)).click();
				System.out.println("Category 'cat2' selected.");

				for (int i = 0; i < numItemsToUpdate; i++) {
					try {
						// Wait for the item list to be visible
						wait.until(ExpectedConditions.visibilityOfAllElements(phoneOrder.itemList));

						// Check if there are items available in the category
						if (phoneOrder.itemList.isEmpty()) {
							System.out.println("No items found in category 'cat2' for updating.");
							return false;
						}

						// Select a random item from the list
						int itemIndex = random.nextInt(phoneOrder.itemList.size());
						WebElement item = phoneOrder.itemList.get(itemIndex);
						item.click();
						System.out.println("Item " + (itemIndex + 1) + " selected for update.");

						// Wait for the list of available modifiers to be visible
						wait.until(ExpectedConditions.visibilityOfAllElements(phoneOrder.modifierList));

						// If modifiers are available, select two random ones
						if (!phoneOrder.modifierList.isEmpty()) {
							int modifierIndex1 = random.nextInt(phoneOrder.modifierList.size());
							int modifierIndex2;

							// Ensure the second modifier is different from the first
							do {
								modifierIndex2 = random.nextInt(phoneOrder.modifierList.size());
							} while (modifierIndex1 == modifierIndex2);

							// Click on the selected modifiers
							phoneOrder.modifierList.get(modifierIndex1).click();
							phoneOrder.modifierList.get(modifierIndex2).click();
							System.out.println("Updated modifiers.");
						} else {
							System.out.println("No modifiers available for update.");
						}

						// Click the 'Done' button after updating the item
						wait.until(ExpectedConditions.elementToBeClickable(phoneOrder.doneButton)).click();
						System.out.println("Done button clicked after update.");

					} catch (Exception e) {
						System.out.println("Error updating item from category 'cat2': " + e.getMessage());
					}
				}
			} catch (Exception e) {
				System.out.println("Error selecting category 'cat2' for update: " + e.getMessage());
			}

			return true;
		}
	   
		// Method to click on the Delivery option  
		public void deliveryClick() {  
		    wait.until(ExpectedConditions.visibilityOf(phoneOrder.delivery)).click();  
		}

		// Method to scroll to the 'City' field within a scrollable container  
		public void scrollToCityField() {  
		    WebElement cityElement = wait.until(ExpectedConditions.presenceOfElementLocated(  
		            AppiumBy.androidUIAutomator(  
		                    "new UiScrollable(new UiSelector().resourceId(\"" + SCROLLABLE_CONTAINER_ID + "\"))" +  
		                            ".scrollIntoView(new UiSelector().text(\"City\"))"  
		            )  
		    ));  
		    System.out.println("Scrolled to the element with text 'City'.");  
		}

		// Method to fill in address fields with random values  
		// Generates random values for street, suite, city, state, and ZIP code  
		public void fillAddressFields() {  
		    enterText(STREET_ID, "Street " + random.nextInt(1000));  
		    enterText(SUITE_ID, "Suite " + random.nextInt(100));  
		    enterText(CITY_ID, "City " + random.nextInt(100));  
		    enterText(STATE_ID, "State " + random.nextInt(50));  
		    enterText(ZIP_ID, String.valueOf(10000 + random.nextInt(90000)));  
		}

		// Helper method to enter text into a given field  
		// Ensures the field is located, cleared, and populated with the provided value  
		private void enterText(String fieldId, String value) {  
		    try {  
		        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id(fieldId)));  
		        field.clear();  
		        field.sendKeys(value);  
		        System.out.println("Sent value: " + value + " to field: " + fieldId);  
		    } catch (Exception e) {  
		        System.out.println("Error interacting with field: " + fieldId + ". Error: " + e.getMessage());  
		    }  
		}

		// Method to get the Order ID from the first order in the list  
		// Locates and retrieves the order ID text from the UI  
		public String getOrderIdElements() {  
		    WebElement orderIdElement = wait.until(ExpectedConditions.presenceOfElementLocated(  
		            AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id='com.pays.pos:id/llMainLayout'])[1]//android.widget.TextView[@resource-id='com.pays.pos:id/tvOrderID']")  
		    ));  
		    String orderIdText = orderIdElement.getText();  
		    return orderIdText;  
		}



		// Method to click on the Cancel Order button  
		public void clickCancelOrder() {  
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  
		    WebElement cancelOrderButton = wait.until(ExpectedConditions.visibilityOf(phoneOrder.CancelOrderButton));  
		    cancelOrderButton.click();  
		    System.out.println("Clicked on the Cancel Order button.");
		}

		// Method to cancel an order based on OrderID and verify cancellation  
		public boolean cancelOrder(String OrderID) {  
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  
		    Random random = new Random();

		    // Step 1: Click Cancel Order Button  
		    clickCancelOrder();  

		    // Step 2: Select a random cancellation reason  
		    String[] reasons = {"Took too Long", "Food Burnt", "Received Wrong item", "Server Error", "Changed Mind"};  
		    String selectedReason = reasons[random.nextInt(reasons.length)];  
		    System.out.println("Selected Reason: " + selectedReason);  

		    // Step 3: Click on the randomly selected reason  
		    WebElement reasonTextView = wait.until(ExpectedConditions.elementToBeClickable(  
		            AppiumBy.xpath("//android.widget.TextView[@text='" + selectedReason + "']")  
		    ));  
		    reasonTextView.click();  
		    System.out.println("Clicked on: " + selectedReason);  

		    // Step 4: Validate that the selected reason appears in the EditText field  
		    WebElement editTextView = wait.until(ExpectedConditions.presenceOfElementLocated(  
		            AppiumBy.id("com.pays.pos:id/etReason")  
		    ));  
		    String editTextValue = editTextView.getText();  
		    Assert.assertTrue(editTextValue.contains(selectedReason), "The selected reason is not present in the EditText field.");  
		   

		    // Step 5: Click 'Done' and 'Save'  
		    wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.pays.pos:id/txtDone"))).click();  
		  
		    wait.until(ExpectedConditions.presenceOfElementLocated(
			        AppiumBy.id("com.pays.pos:id/tvSave")
			)).click();
		
		    wait.until(ExpectedConditions.presenceOfElementLocated(
			        AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/custom\"]/android.widget.LinearLayout")
			));
		   
		    wait.until(ExpectedConditions.presenceOfElementLocated(
			        AppiumBy.id("com.pays.pos:id/tvSave")
			)).click();
		    // Step 6: Ensure cancellation confirmation  
//		    WebElement cancelledElement = wait.until(ExpectedConditions.presenceOfElementLocated(  
//		            AppiumBy.xpath("//android.widget.TextView[@resource-id='com.pays.pos:id/txt_title' and @text='Cancelled']")  
//		    ));  
//		    cancelledElement.click();  
			

		    // Step 7: Verify Order ID after cancellation  
		    WebElement Oid = wait.until(ExpectedConditions.presenceOfElementLocated(  
		            AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id='com.pays.pos:id/llMainLayout'])[1]//android.widget.TextView[@resource-id='com.pays.pos:id/tvOrderID']")  
		    ));  
		    String orderNo = Oid.getText();  
		    Assert.assertEquals(OrderID, orderNo, "The OrderId and OrderNo are not equal!");  

		    

		    return true;  
		}

		// Method to click on 'Print Customer Receipt' button  
		public void clickPrintCustomerReceipt() {  
		    WebElement customerReceiptElement = wait.until(ExpectedConditions.elementToBeClickable(  
		            AppiumBy.id("com.pays.pos:id/txtCustomerReceipt")  
		    ));  
		    customerReceiptElement.click();  
		    System.out.println("Customer Receipt clicked.");  
		}

	  
	  
	//// Method To Click Kitchen Receipt 
	 public void KitcheRePrintReceipt() {
		 WebElement customerReceiptElement = wait.until(ExpectedConditions.elementToBeClickable(  
		            AppiumBy.id("com.pays.pos:id/txtRePrintKitchenReceipt")  
		    ));  
		    customerReceiptElement.click();  
		    System.out.println("Customer Receipt clicked.");  
	 }
	  public Boolean manualItem(String orderType) {
	        try {
	            // Click the keypad
	        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	
	        	wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(phoneOrder.keypad))).click();

	         // ✅ Correct


	            // List of food items to add
	            String[] foodItems = {"Mango Ice Cream", "Seasonal Fruits", "Sandwich Chocolate"};

	            // Loop through each food item
	            for (String foodItem : foodItems) {
	                // Enter the food item name
	            	wait.until(ExpectedConditions.visibilityOf(phoneOrder.customItemName)).sendKeys(foodItem); // ✅ Correct


	                // Click random prize elements 3 times
	                Random random = new Random();
	                String[] numbers = {"tvOne", "tvTwo", "tvThree", "tvFour", "tvFive", "tvSix", "tvSeven", "tvEight", "tvNine"};
	                for (int i = 0; i < 3; i++) {
	                    int randomNum = random.nextInt(9); // Random number between 0 and 8
	                    String prizeXpath = String.format("//android.widget.TextView[@resource-id='com.pays.pos:id/%s']", numbers[randomNum]);
	                    wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(prizeXpath))).click();
	                }

	                // Click the add button
	                wait.until(ExpectedConditions.elementToBeClickable(phoneOrder.imgAdd)).click();
	            }

	            // Save the order
	            wait.until(ExpectedConditions.elementToBeClickable(phoneOrder.txtsave)).click();

	            return true; // Return true if successful
	        } catch (Exception e) {
	            return false;// Return error message if an exception occurs
	        }
	    }
	  
	  public void AddCustomer() {
			wait.until(ExpectedConditions.visibilityOf(phoneOrder.SearchCustomerButton)).click();
		}
	    public void scrollToPhoneNumberAndClick(String phoneNumber) {
			WebElement phoneElement = wait.until(ExpectedConditions.presenceOfElementLocated(
					AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
							+ ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtPhone\").text(\""
							+ phoneNumber + "\"))")));
			phoneElement.click();
		}
	    
	    public boolean updateItemQuantity() {
	        try {
	            // Get the number of items in the cart
	        	List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElements(phoneOrder.cartQty));
	        	int cartQtyCount = cartItems.size();

	            // Select a random item from the cart
	            Random random = new Random();
	            int randomItemIndex = random.nextInt(cartQtyCount) + 1; // Random index between 1 and cartQtyCount
	            String itemPath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCartList']/androidx.appcompat.widget.LinearLayoutCompat[%d]/android.widget.LinearLayout", randomItemIndex);
	            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(itemPath))).click();

	            // Click the plus button a random number of times (1 to 5)
	            int clicks = random.nextInt(5) + 1; // Random number between 1 and 5
	            for (int i = 0; i < clicks; i++) {
	                wait.until(ExpectedConditions.elementToBeClickable(phoneOrder.plusButton)).click();
	            }

	            // Add a note
	           boolean result = addNote("item_note");

	            // Click the done button
	            wait.until(ExpectedConditions.elementToBeClickable(phoneOrder.doneButton)).click();

	            // Verify the updated quantity
	            String qtyPath = String.format("(//android.widget.TextView[@resource-id='com.pays.pos:id/txtQuantity'])[%d]", randomItemIndex+1);
	            WebElement quantityElement = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(qtyPath)));
	            int updatedQuantity = Integer.parseInt(quantityElement.getText());

	            // Check if the updated quantity matches the expected value
	           return true;
	        } catch (Exception e) {
	            System.out.println("Error in updateItemQuantity method: " + e.getMessage());
	            return false; // Return false if an exception occurs
	        }
	    }
	    private int cartQtyCount;

	    public boolean removeItem() {
	        try {
	            // Get the number of items in the cart
	            List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElements(phoneOrder.cartQty));
	            cartQtyCount = cartItems.size();

	            if (cartQtyCount == 0) {
	                System.out.println("Cart is empty. No item to remove.");
	                return false;
	            }

	            // Select a random item from the cart
	            Random random = new Random();
	            int randomItemIndex = random.nextInt(cartQtyCount); // Corrected index range
	            String itemPath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCartList']/androidx.appcompat.widget.LinearLayoutCompat[%d]/android.widget.LinearLayout", randomItemIndex + 1 );
	            
	            WebElement itemElement = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(itemPath)));
	            itemElement.click();

	            // Perform delete action (without item note)
	            boolean deleteSuccess = deleteItem();
	            if (!deleteSuccess) {
	                System.out.println("Failed to delete item.");
	                return false;
	            }

	            return true;

	            // Check if cart size has decreased
//	            List<WebElement> updatedCartItems = wait.until(ExpectedConditions.visibilityOfAllElements(openOrder.cartQty));
//	            return updatedCartItems.size() == cartQtyCount - 1;

	        } catch (Exception e) {
	            System.out.println("Error in removeItem method: " + e.getMessage());
	            return false;
	        }
	    }
	    public boolean deleteItem() {
	        try {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	            wait.until(ExpectedConditions.elementToBeClickable(phoneOrder.deleteItem)).click(); // Directly delete item

	            return true;

	        } catch (Exception e) {
	            System.out.println("Error in deleteItem method: " + e.getMessage());
	            return false;
	        }
	    }

	    public boolean addNote(String noteType) {
	    	  try {
	    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    	        wait.until(ExpectedConditions.elementToBeClickable(phoneOrder.addItemNote)).click();
	    	        

	    	        Random rand = new Random();
	    	        int randomNote = rand.nextInt(4) + 1;  // Generates a number between 1 and 4

	    	        String xpath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvNotes']/androidx.appcompat.widget.LinearLayoutCompat[%d]/androidx.appcompat.widget.LinearLayoutCompat", randomNote);
	    	        
	    	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(xpath))).click();
	    	        wait.until(ExpectedConditions.visibilityOf(phoneOrder.txtsave)).click();
	    	        
	    	        return true;
	    	    } catch (Exception e) {
	    	        System.out.println("Cannot add Note: " + e.getMessage());
	    	        return false;
	    	    }
	    }

}

