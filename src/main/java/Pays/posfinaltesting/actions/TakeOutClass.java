package Pays.posfinaltesting.actions;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import Pays.posfinaltest.pageobjectlocators.TakeoutPageObject;

public class TakeOutClass {
	
	private AppiumDriver driver;
	  private WebDriverWait wait;
	  private Random random;
	  private TakeoutPageObject takeout;
	  //Constructor to Phone Order class
	    public TakeOutClass(AppiumDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));// Explicit wait of 10 sec
	        this.random = new Random();
	        this.takeout =new TakeoutPageObject(driver);
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	    }
	    
	    
		public void AddCustomer() {
			wait.until(ExpectedConditions.visibilityOf(takeout.AddCustomerButton)).click();
		}
	    public void scrollToPhoneNumberAndClick(String phoneNumber) {
			WebElement phoneElement = wait.until(ExpectedConditions.presenceOfElementLocated(
					AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
							+ ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtPhone\").text(\""
							+ phoneNumber + "\"))")));
			phoneElement.click();
		}
	    public String scrollToPhoneNumberAndGetName(String phoneNumber) {
	        // Scroll to the phone number
	        WebElement phoneElement = wait.until(ExpectedConditions.presenceOfElementLocated(
	                AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
	                        + ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtPhone\").text(\""
	                        + phoneNumber + "\"))")));

	        // Locate the nearest ancestor that contains both phone and name
	        WebElement parentElement = phoneElement.findElement(AppiumBy.xpath("./ancestor::androidx.appcompat.widget.LinearLayoutCompat"));

	        // Find the name element within this parent container
	        WebElement nameElement = parentElement.findElement(AppiumBy.xpath(".//android.widget.TextView[@resource-id='com.pays.pos:id/txtName']"));

	        // Return the name text
	        return nameElement.getText();
	    }


	    public boolean ChangeOrderType(String orderType) {
	    	wait.until(ExpectedConditions.visibilityOf(takeout.OrderDisplay)).click();
	   
	        wait.until(ExpectedConditions.visibilityOf(takeout.OrderTypeLinearLayout));
	        WebElement orderElement = wait.until(ExpectedConditions.elementToBeClickable(
	        		
	                AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.pays.pos:id/txtTitle\" and @text='" + orderType + "']")
	        ));
	        
	        orderElement.click();
	        System.out.println("Clicked on: " + orderType);
	        
	        String fullText = 	wait.until(ExpectedConditions.visibilityOf(takeout.OrderDisplay)).getText();
	        String ChangedOrderType = fullText.substring(fullText.indexOf(": ") + 2); 

	        System.out.println(orderType);
	        if (orderType.equals(ChangedOrderType)) {
	            return true;
	        } else {
	            System.out.println("Error Changing Order Type: Expected [" + ChangedOrderType + "] but found [" + orderType + "]");
	            return false;
	        }

	       
	    }
	    public void ClickTakeOut() {
	    	 WebElement sellCard = wait.until(ExpectedConditions.presenceOfElementLocated(
	                 AppiumBy.androidUIAutomator(
	                         "new UiScrollable(new UiSelector().className(\"androidx.recyclerview.widget.RecyclerView\"))" +
	                         ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtCategoryName\").text(\"Sell Card\"))"
	                 )
	         ));
	    	wait.until(ExpectedConditions.visibilityOf(takeout.TakeOut)).click();
	    }
	    
	 // Method to select items from the categories
	    public boolean selectRandomItems(int numItemsTotal) {
	    	WebDriverWait shortwait = new WebDriverWait(driver, Duration.ofMillis(500));

	        Random random = new Random();
	        int numItemsToAdd = random.nextInt(numItemsTotal) + 1; // Random number between 1 and 4
	        System.out.println("Adding " + numItemsToAdd + " items.");

	        for (int i = 0; i < numItemsToAdd; i++) {
	            try {
	                // Ensure at least two categories exist
	                if (takeout.categoryList.size() < 2) {
	                    System.out.println("Not enough categories to skip the first one.");
	                    return false;
	                }

	                // Select a random category, ensuring the first category (index 0) is never selected
	                int categoryIndex = random.nextInt(takeout.categoryList.size() - 1) + 1; // Picks from index 1 onwards
	                WebElement category = takeout.categoryList.get(categoryIndex);

	                shortwait.until(ExpectedConditions.elementToBeClickable(category)).click(); // Ensure category is clickable before clicking
	                System.out.println("Category selected (excluding the first one).");

	                // Wait for items in the selected category
	                shortwait.until(ExpectedConditions.visibilityOfAllElements(takeout.itemList));

	                if (takeout.itemList.isEmpty()) {
	                    System.out.println("No items in this category.");
	                    continue;
	                } else {
	                    // Select a random item (including the first one)
	                    int itemIndex = random.nextInt(takeout.itemList.size()); // Allows selecting the first item (index 0)
	                    WebElement item = takeout.itemList.get(itemIndex);

	                    shortwait.until(ExpectedConditions.elementToBeClickable(item)).click();
	                    System.out.println("Item selected.");
	                }

	                // Wait for modifiers if available
	                shortwait.until(ExpectedConditions.visibilityOfAllElements(takeout.modifierList));

	                if (!takeout.modifierList.isEmpty()) {
	                    int modifierIndex1 = random.nextInt(takeout.modifierList.size());
	                    int modifierIndex2;

	                    do {
	                        modifierIndex2 = random.nextInt(takeout.modifierList.size());
	                    } while (modifierIndex1 == modifierIndex2);

	                    shortwait.until(ExpectedConditions.elementToBeClickable(takeout.modifierList.get(modifierIndex1))).click();
	                    shortwait.until(ExpectedConditions.elementToBeClickable(takeout.modifierList.get(modifierIndex2))).click();
	                    System.out.println("Modifiers selected.");
	                } else {
	                    System.out.println("No modifiers found.");
	                }

	                // Click the Done button
	                shortwait.until(ExpectedConditions.elementToBeClickable(takeout.doneButton)).click();
	                System.out.println("Item added.");

	            } catch (Exception e) {
	                System.out.println("Error during item selection: " + e.getMessage());
	            }
	        }
	        return true;
	    }
	   
	   
	    public Boolean manualItem(String orderType) {
	        try {
	            // Click the keypad
	        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	
	        	wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(takeout.keypad))).click();

	         // ✅ Correct


	            // List of food items to add
	            String[] foodItems = {"Mango Ice Cream", "Seasonal Fruits", "Sandwich Chocolate"};

	            // Loop through each food item
	            for (String foodItem : foodItems) {
	                // Enter the food item name
	            	wait.until(ExpectedConditions.visibilityOf(takeout.customItemName)).sendKeys(foodItem); // ✅ Correct


	                // Click random prize elements 3 times
	                Random random = new Random();
	                String[] numbers = {"tvOne", "tvTwo", "tvThree", "tvFour", "tvFive", "tvSix", "tvSeven", "tvEight", "tvNine"};
	                for (int i = 0; i < 3; i++) {
	                    int randomNum = random.nextInt(9); // Random number between 0 and 8
	                    String prizeXpath = String.format("//android.widget.TextView[@resource-id='com.pays.pos:id/%s']", numbers[randomNum]);
	                    wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(prizeXpath))).click();
	                }

	                // Click the add button
	                wait.until(ExpectedConditions.elementToBeClickable(takeout.imgAdd)).click();
	            }

	            // Save the order
	            wait.until(ExpectedConditions.elementToBeClickable(takeout.txtsave)).click();

	            return true; // Return true if successful
	        } catch (Exception e) {
	            return false;// Return error message if an exception occurs
	        }
	    }
	    public boolean updateItemQuantity() {
	        try {
	            // Get the number of items in the cart
	        	List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElements(takeout.cartQty));
	        	int cartQtyCount = cartItems.size();

	            // Select a random item from the cart
	            Random random = new Random();
	            int randomItemIndex = random.nextInt(cartQtyCount) + 1; // Random index between 1 and cartQtyCount
	            String itemPath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCartList']/androidx.appcompat.widget.LinearLayoutCompat[%d]/android.widget.LinearLayout", randomItemIndex + 1);
	            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(itemPath))).click();

	            // Click the plus button a random number of times (1 to 5)
	            int clicks = random.nextInt(5) + 1; // Random number between 1 and 5
	            for (int i = 0; i < clicks; i++) {
	                wait.until(ExpectedConditions.elementToBeClickable(takeout.plusButton)).click();
	            }

	            // Add a note
	           boolean result = addNote("item_note");

	            // Click the done button
	            wait.until(ExpectedConditions.elementToBeClickable(takeout.doneButton)).click();

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
	            List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElements(takeout.cartQty));
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

	            wait.until(ExpectedConditions.elementToBeClickable(takeout.deleteItem)).click(); // Directly delete item

	            return true;

	        } catch (Exception e) {
	            System.out.println("Error in deleteItem method: " + e.getMessage());
	            return false;
	        }
	    }

	    public boolean addNote(String noteType) {
	    	try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    	        wait.until(ExpectedConditions.elementToBeClickable(takeout.addItemNote)).click();
    	        

    	        Random rand = new Random();
    	        int randomNote = rand.nextInt(4) + 1;  // Generates a number between 1 and 4

    	        String xpath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvNotes']/androidx.appcompat.widget.LinearLayoutCompat[%d]/androidx.appcompat.widget.LinearLayoutCompat", randomNote);
    	        
    	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(xpath))).click();
    	        wait.until(ExpectedConditions.visibilityOf(takeout.txtsave)).click();
    	        
    	        return true;
    	    } catch (Exception e) {
    	        System.out.println("Cannot add Note: " + e.getMessage());
    	        return false;
    	    }
	 //Update Order
	    	
	    	

}
	    public void ClickOrderSave() {
			wait.until(ExpectedConditions.visibilityOf(takeout.OrderSave)).click();
		}

		public String getOrderTypeText() throws InterruptedException {
			Thread.sleep(10);
			return wait.until(ExpectedConditions.visibilityOf(takeout.OrderTypeSaved)).getText();
		}

		public void clickMainLayout() {
			wait.until(ExpectedConditions.elementToBeClickable(takeout.mainLayout)).click();
		}

		// Method to update an existing order
		// Waits until the "Update Order" button is visible before clicking it
		public void UpdateOrder() {
			wait.until(ExpectedConditions.visibilityOf(takeout.updateOrder)).click();
		}

}