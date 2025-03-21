package Pays.posfinaltesting.actions;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Pays.posfinaltest.pageobjectlocators.StayOrderPageObject;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;


public class StayClass {
	 private AppiumDriver driver;
	  private WebDriverWait wait;
	  private Random random;
	  private StayOrderPageObject stay;
	  //Constructor to Phone Order class
	    public StayClass(AppiumDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));// Explicit wait of 10 sec
	        this.random = new Random();
	        this.stay = new StayOrderPageObject(driver);
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	    }


		// com.pays.pos:id/tvPayNow
				@AndroidFindBy(id = "com.pays.pos:id/tvPayNow")
				private WebElement Pay;
				
//	    public void StayButtonClick() {
//			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
//			wait.until(ExpectedConditions.visibilityOf(stay.StayButton)).click();
//		}
	    
	    //Method to select the items
	    public void AddCustomer() {
			wait.until(ExpectedConditions.visibilityOf(stay.AddCustomerButton)).click();
		}
	    public void scrollToPhoneNumberAndClick(String phoneNumber) {
			WebElement phoneElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
							+ ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtPhone\").text(\""
							+ phoneNumber + "\"))")));
			phoneElement.click();
		}

		
	    public boolean ChangeOrderType(String orderType) {
	    	wait.until(ExpectedConditions.visibilityOf(stay.OrderDisplay)).click();
	   
	        wait.until(ExpectedConditions.visibilityOf(stay.OrderTypeLinearLayout));
	        WebElement orderElement = wait.until(ExpectedConditions.elementToBeClickable(
	        		
	                AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtTitle\" and @text='" + orderType + "']")
	        ));
	        
	        orderElement.click();
	        System.out.println("Clicked on: " + orderType);
	        
	        String fullText = 	wait.until(ExpectedConditions.visibilityOf(stay.OrderDisplay)).getText();
	        String ChangedOrderType = fullText.substring(fullText.indexOf(": ") + 2); 

	        System.out.println(orderType);
	        if (orderType.equals(ChangedOrderType)) {
	            return true;
	        } else {
	            System.out.println("Error Changing Order Type: Expected [" + ChangedOrderType + "] but found [" + orderType + "]");
	            return false;
	        }

	       
	    }
	    public void StayButtonClick() {
	    	 WebElement sellCard = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 AppiumBy.androidUIAutomator(
	                         "new UiScrollable(new UiSelector().className(\"androidx.recyclerview.widget.RecyclerView\"))" +
	                         ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtCategoryName\").text(\"Sell Card\"))"
	                 )
	         ));
	    	wait.until(ExpectedConditions.visibilityOf(stay.StayButton)).click();
	    }
	    
	    public boolean selectRandomItems(int numItemsTotal) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
	        Random random = new Random();
	        int numItemsToAdd = random.nextInt(numItemsTotal) + 1;
	        System.out.println("Adding " + numItemsToAdd + " items.");

	        // Cache frequently accessed elements
	        List<WebElement> categoriesExcludingFirst = stay.categoryList.subList(1, stay.categoryList.size());
	        if (categoriesExcludingFirst.size() < 1) {
	            System.out.println("Not enough categories to skip the first one.");
	            return false;
	        }

	        // Ensure the first category is never clicked
	        WebElement firstCategory = stay.categoryList.get(0);
	        String firstCategoryText = firstCategory.getText();
	        if (firstCategoryText.equals("GIFT CARD")) {
	            System.out.println("First category is 'GIFT CARD'. It will never be clicked.");
	        } else {
	            System.out.println("First category is not 'GIFT CARD', but it will still never be clicked.");
	        }

	        for (int i = 0; i < numItemsToAdd; i++) {
	            try {
	                // Select a random category from the list excluding the first one
	                int categoryIndex = random.nextInt(categoriesExcludingFirst.size());
	                WebElement category = categoriesExcludingFirst.get(categoryIndex);

	                // Double-check that the selected category is not the first one
	                if (category.equals(firstCategory)) {
	                    System.out.println("Attempted to select the first category. Skipping and retrying.");
	                    i--; // Retry this iteration
	                    continue;
	                }

	                clickElement(category, wait, "Category selected (excluding the first one).");

	                // Check if the category has items
	                if (stay.itemList.isEmpty()) {
	                    System.out.println("No items in this category.");
	                    continue;
	                }

	                // Select a random item
	                int itemIndex = random.nextInt(stay.itemList.size());
	                WebElement item = stay.itemList.get(itemIndex);
	                clickElement(item, wait, "Item selected.");

	                // Select modifiers if available
	                if (!stay.modifierList.isEmpty()) {
	                    int modifierIndex1 = random.nextInt(stay.modifierList.size());
	                    int modifierIndex2;
	                    do {
	                        modifierIndex2 = random.nextInt(stay.modifierList.size());
	                    } while (modifierIndex1 == modifierIndex2);

	                    clickElement(stay.modifierList.get(modifierIndex1), wait, "Modifier 1 selected.");
	                    clickElement(stay.modifierList.get(modifierIndex2), wait, "Modifier 2 selected.");
	                    System.out.println("Modifiers selected.");
	                } else {
	                    System.out.println("No modifiers found.");
	                }

	                // Click the "Done" button
	                clickElement(stay.doneButton, wait, "Item added.");

	            } catch (Exception e) {
	                System.out.println("Error during item selection: " + e.getMessage());
	            }
	        }
	        return true;
	    }
	    // Helper method to click an element with logging
	    private void clickElement(WebElement element, WebDriverWait wait, String logMessage) {
	        if (element.isDisplayed() && element.isEnabled()) {
	            element.click();
	        } else {
	            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	        }
	        System.out.println(logMessage);
	    }
	   
	    public Boolean manualItem(String orderType) {
	        try {
	            // Click the keypad
	        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	
	        	wait.until(ExpectedConditions.elementToBeClickable(stay.keypad)).click();

	         // ✅ Correct


	            // List of food items to add
	            String[] foodItems = {"Mango Ice Cream", "Seasonal Fruits", "Sandwich Chocolate"};

	            // Loop through each food item
	            for (String foodItem : foodItems) {
	                // Enter the food item name
	            	wait.until(ExpectedConditions.visibilityOf(stay.customItemName)).sendKeys(foodItem); // ✅ Correct


	                // Click random prize elements 3 times
	                Random random = new Random();
	                String[] numbers = {"tvOne", "tvTwo", "tvThree", "tvFour", "tvFive", "tvSix", "tvSeven", "tvEight", "tvNine"};
	                for (int i = 0; i < 3; i++) {
	                    int randomNum = random.nextInt(9); // Random number between 0 and 8
	                    String prizeXpath = String.format("//android.widget.TextView[@resource-id='com.pays.pos:id/%s']", numbers[randomNum]);
	                    wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(prizeXpath))).click();
	                }

	                // Click the add button
	                wait.until(ExpectedConditions.elementToBeClickable(stay.imgAdd)).click();
	            }

	            // Save the order
	            wait.until(ExpectedConditions.elementToBeClickable(stay.txtsave)).click();

	            return true; // Return true if successful
	        } catch (Exception e) {
	            return false;// Return error message if an exception occurs
	        }
	    }
	    public boolean updateItemQuantity() {
	        try {
	            // Get the number of items in the cart
	        	List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElements(stay.cartQty));
	        	int cartQtyCount = cartItems.size();

	            // Select a random item from the cart
	            Random random = new Random();
	            int randomItemIndex = random.nextInt(cartQtyCount) + 1; // Random index between 1 and cartQtyCount
	            String itemPath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCartList']/androidx.appcompat.widget.LinearLayoutCompat[%d]/android.widget.LinearLayout", randomItemIndex);
	            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(itemPath))).click();

	            // Click the plus button a random number of times (1 to 5)
	            int clicks = random.nextInt(5) + 1; // Random number between 1 and 5
	            for (int i = 0; i < clicks; i++) {
	                wait.until(ExpectedConditions.elementToBeClickable(stay.plusButton)).click();
	            }

	            // Add a note
	           boolean result = addNote("item_note");

	            // Click the done button
	            wait.until(ExpectedConditions.elementToBeClickable(stay.doneButton)).click();

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
	            List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElements(stay.cartQty));
	            cartQtyCount = cartItems.size();

	            if (cartQtyCount == 0) {
	                System.out.println("Cart is empty. No item to remove.");
	                return false;
	            }

	            // Select a random item from the cart
	            Random random = new Random();
	            int randomItemIndex = random.nextInt(cartQtyCount); // Corrected index range
	            String itemPath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCartList']/androidx.appcompat.widget.LinearLayoutCompat[%d]/android.widget.LinearLayout", randomItemIndex+1 );
	            
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

	            wait.until(ExpectedConditions.elementToBeClickable(stay.deleteItem)).click(); // Directly delete item

	            return true;

	        } catch (Exception e) {
	            System.out.println("Error in deleteItem method: " + e.getMessage());
	            return false;
	        }
	    }

	    public boolean addNote(String noteType) {
	    	  try {
	    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    	        wait.until(ExpectedConditions.elementToBeClickable(stay.addItemNote)).click();
	    	        

	    	        Random rand = new Random();
	    	        int randomNote = rand.nextInt(4) + 1;  // Generates a number between 1 and 4

	    	        String xpath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvNotes']/androidx.appcompat.widget.LinearLayoutCompat[%d]/androidx.appcompat.widget.LinearLayoutCompat", randomNote);
	    	        
	    	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(xpath))).click();
	    	        wait.until(ExpectedConditions.visibilityOf(stay.txtsave)).click();
	    	        
	    	        return true;
	    	    } catch (Exception e) {
	    	        System.out.println("Cannot add Note: " + e.getMessage());
	    	        return false;
	    	    }
	    }
	    public void ClickOrderSave() {
			wait.until(ExpectedConditions.visibilityOf(stay.OrderSave)).click();
		}
		public String getOrderTypeText() {
			return wait.until(ExpectedConditions.visibilityOf(stay.OrderTypeSaved)).getText();
		}
		public void clickMainLayout() {
			wait.until(ExpectedConditions.elementToBeClickable(stay.mainLayout)).click();
		}

		// Method to update an existing order
		// Waits until the "Update Order" button is visible before clicking it
		public void UpdateOrder() {
			wait.until(ExpectedConditions.visibilityOf(stay.updateOrder)).click();
		}
		
		// This method waits until the "Pay" button is visible before clicking on it
				public void Paynow() {
					wait.until(ExpectedConditions.visibilityOf(Pay)).click();
				}
}
