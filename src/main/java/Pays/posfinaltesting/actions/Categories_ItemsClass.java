package Pays.posfinaltesting.actions;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Categories_ItemsClass {
	private AppiumDriver driver;
	private AndroidDriver drive;
	private WebDriverWait wait;

	// Constructor
	public Categories_ItemsClass(AppiumDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.drive = (AndroidDriver) driver;// Explicit wait of 10 sec
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.pays.pos:id/imgDrawer")
	private WebElement MenuBurgerButton; // Menu burger button to open the navigation drawer

	@AndroidFindBy(id = "com.pays.pos:id/linearInventory")
	private WebElement MenuButton; // Menu button to access inventory options

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.pays.pos:id/txt_title' and @text='Categories']")
	private WebElement CategoriesButton; // Button to navigate to the "Categories" section

	@AndroidFindBy(id = "com.pays.pos:id/txtCreatecatagory")
	private WebElement createCategory; // Button to create a new category

	@AndroidFindBy(id = "com.pays.pos:id/etCategoryName")
	private WebElement AddCategoryName; // Input field to enter a new category name

	@AndroidFindBy(id = "com.pays.pos:id/txtSave")
	private WebElement SaveButton; // Button to save the new category

	@AndroidFindBy(id = "com.pays.pos:id/txtCreateItem")
	private WebElement AddItem; // Button to add a new item

	@AndroidFindBy(id = "com.pays.pos:id/txtDone")
	private WebElement ItemDone; // Button to confirm item addition

	@AndroidFindBy(id = "com.pays.pos:id/etItemPrice")
	private WebElement ItemPrice; // Input field to set item price

	@AndroidFindBy(id = "com.pays.pos:id/etStock")
	private WebElement Quantity; // Input field to enter item quantity

	@AndroidFindBy(id = "com.pays.pos:id/tvPayNow")
	private WebElement Pay; // Button to proceed with payment
	
	@AndroidFindBy(id="com.pays.pos:id/txtcreatemodifieer")
	private WebElement CreateModifier;
	
	@AndroidFindBy(id="android:id/text1")
	private WebElement SelectModifier;

	
	// Method to click on the Menu Burger button after scrolling to "Sell Card"
	public void MenuBurgerClick() throws InterruptedException {
		Thread.sleep(10); // Adding a short delay before interacting with the menu

		// Scroll to locate "Sell Card" category before proceeding
		WebElement sellCard = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector().className(\"androidx.recyclerview.widget.RecyclerView\"))"
						+ ".scrollIntoView(new UiSelector().resourceId(\"com.pays.pos:id/txtCategoryName\").text(\"Sell Card\"))")));

		// Click on the Menu Burger button to open the menu
		wait.until(ExpectedConditions.elementToBeClickable(MenuBurgerButton)).click();
	}

	// Method to click on the Menu button (Inventory section)
	public void MenuButtonClick() {
		wait.until(ExpectedConditions.elementToBeClickable(MenuButton)).click(); // Clicks on the Inventory Menu button
	}

	// Method to navigate to the "Categories" section in the menu
	public void ClickCategories() {
		WebElement categoriesButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy
				.xpath("//android.widget.TextView[@resource-id='com.pays.pos:id/txt_title' and @text='Categories']")));
		categoriesButton.click(); // Clicks on the "Categories" option
	}

	// Method to initiate category creation
	public void CreateCategory() {
		WebElement createcat = wait.until(ExpectedConditions.elementToBeClickable(createCategory));
		createcat.click(); // Clicks on the "Create Category" button
	}

	// Method to enter the category name in the input field
	public void SetcategoryName(String CategoryName) {
		WebElement categoryName = wait.until(ExpectedConditions.visibilityOf(AddCategoryName));
		categoryName.sendKeys(CategoryName); // Sets the category name
	}

	// Method to save the created category
	public void SaveCategory() {
		// Wait for the save button to be clickable and click it
		wait.until(ExpectedConditions.elementToBeClickable(SaveButton)).click();

		// Wait for the pop-up message to appear
		WebElement popUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy
				.xpath("//android.widget.FrameLayout[@resource-id='android:id/custom']/android.widget.LinearLayout")));

		// Assert that the pop-up message is displayed
		Assert.assertTrue(popUpMessage.isDisplayed(), "Pop-up message is not displayed.");

		// Retrieve and validate the success message
		String categorycreatedmsg = wait
				.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/tvMessage"))).getText();
		Assert.assertTrue(categorycreatedmsg.contains("Category was successfully created."), "Category not Created.");

		// Click on the OK button after category creation
		WebElement okButton = wait
				.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/tvSave") // Ensure this
																											// ID
																											// matches
																											// the
																											// actual OK
																											// button
				));
		okButton.click();
	}

	// Method to scroll to a specific category by name
	public void scrollToElement(String categoryName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Scroll to the element containing the category name
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
				AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
						+ ".scrollIntoView(new UiSelector().text(\"" + categoryName + "\"))")));

		// Assert that the category is visible
		Assert.assertTrue(element.isDisplayed(), "The element with text '" + categoryName + "' was not found!");
	}

	// Method to click on the "Items" section from the menu
	public void ClickItems() {
		WebElement categoriesButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy
				.xpath("//android.widget.TextView[@resource-id='com.pays.pos:id/txt_title' and @text='Items']")));
		categoriesButton.click(); // Clicks on the "Items" section
	}

	// Method to add a new item
	public void AddItem() {
		WebElement createcat = wait.until(ExpectedConditions.elementToBeClickable(AddItem));
		createcat.click(); // Clicks on the "Add Item" button
	}

	// Method to set the item name and select its category
	public void SetItem(String ItemName) {
		// Wait for the item name field to be visible and enter the item name
		WebElement itemNameField = wait.until(ExpectedConditions
				.presenceOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@text='Item Name*']")));
		itemNameField.sendKeys(ItemName);

		// Click on the category selection icon
		WebElement categoryIcon = wait
				.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.pays.pos:id/ivCategory")));
		categoryIcon.click();
	}

	// Method to set item information including category selection, price, and
	// quantity
	public void SetItemInfo(String CatName) {
		// Scroll to find the category by name
		WebElement italianElement = scrollToText(CatName);

		try {
			// Locate the radio button relative to the category name
			WebElement leftElement = driver.findElement(
					AppiumBy.xpath("//android.widget.TextView[@text= '" + CatName + "']/preceding-sibling::*[1]"));
			leftElement.click(); // Click on the radio button
			System.out.println("Radio button next to '" + CatName + "' clicked successfully.");
		} catch (NoSuchElementException e) {
			System.out.println("No radio button found next to '" + CatName + "'.");
		}

		// Click the "Done" button after selecting the category
		wait.until(ExpectedConditions.elementToBeClickable(ItemDone)).click();

		// Scroll to the "Quantity" field before entering details
		WebElement Price = scrollToText("Quantity");

		// Enter item price
		wait.until(ExpectedConditions.elementToBeClickable(ItemPrice)).sendKeys("6.45");

		// Enter item quantity
		wait.until(ExpectedConditions.elementToBeClickable(Quantity)).sendKeys("1");
		
		WebElement scroll = scrollToText("Description");
		wait.until(ExpectedConditions.elementToBeClickable(SelectModifier)).click();
		wait.until(ExpectedConditions.elementToBeClickable(
			    AppiumBy.xpath("//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='Shape']")
			)).click();

			
		// Click save to finalize item creation
		wait.until(ExpectedConditions.elementToBeClickable(SaveButton)).click();

		// Wait for pop-up confirmation message
		WebElement popUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy
				.xpath("//android.widget.FrameLayout[@resource-id='android:id/custom']/android.widget.LinearLayout")));

		// Verify that the pop-up message is displayed
		Assert.assertTrue(popUpMessage.isDisplayed(), "Pop-up message is not displayed.");

		// Retrieve and validate success message
		String categorycreatedmsg = wait
				.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/tvMessage"))).getText();
		Assert.assertTrue(categorycreatedmsg.contains("Item was successfully created."), "Item not Created.");

		// Click on OK button to close the pop-up
		WebElement okButton = wait
				.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/tvSave") // Ensure this
																											// ID
																											// matches
																											// the
																											// actual OK
																											// button
				));
		okButton.click();
	}

	// Method to scroll to an element containing specific text
	public WebElement scrollToText(String text) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(
				AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
						+ ".scrollIntoView(new UiSelector().text(\"" + text + "\"))")));
	}

	// Method to navigate to the home section
	public void selectHome() {
		wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/txtHome"))).click();
	}

	// Method to select a category by scrolling horizontally through a RecyclerView
	public void selectCategory(String categoryName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Locate the RecyclerView containing categories
		WebElement recyclerView = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy
				.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCategory']")));

		boolean isCategoryFound = false;
		int maxScrolls = 5; // Limit the number of scrolls to prevent infinite loops
		int scrollCount = 0;

		while (!isCategoryFound && scrollCount < maxScrolls) {
			// Retrieve all category elements inside the RecyclerView
			List<WebElement> categories = driver.findElements(AppiumBy.xpath(
					"//androidx.recyclerview.widget.RecyclerView[@resource-id='com.pays.pos:id/rvCategory']//android.widget.TextView"));

			// Loop through the categories to find the desired one
			for (WebElement category : categories) {
				if (category.getText().equalsIgnoreCase(categoryName)) {
					category.click();
					System.out.println("Category '" + categoryName + "' selected.");
					isCategoryFound = true;
					break;
				}
			}

			// If category is not found, scroll horizontally and retry
			if (!isCategoryFound) {
				scrollHorizontally(recyclerView);
				scrollCount++;
				System.out.println("Scrolling... Attempt: " + scrollCount);
			}
		}

		// Log if the category was not found even after scrolling
		if (!isCategoryFound) {
			System.out.println("Category '" + categoryName + "' not found after scrolling.");
		}
	}

	// ✅ Method to scroll horizontally in a RecyclerView
	private void scrollHorizontally(WebElement recyclerView) {
		// Calculate start and end X-coordinates for horizontal swipe
		int startX = recyclerView.getLocation().getX() + (int) (recyclerView.getSize().getWidth() * 0.8);
		int endX = recyclerView.getLocation().getX() + (int) (recyclerView.getSize().getWidth() * 0.2);
		int y = recyclerView.getLocation().getY() + (recyclerView.getSize().getHeight() / 2); // Y remains constant

		// Create a pointer input for touch actions
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence swipe = new Sequence(finger, 1);

		// Define swipe action sequence
		swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, y)); // Move to
																												// start
																												// position
		swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg())); // Touch down
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), endX, y)); // Swipe
																													// to
																													// end
																													// position
		swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg())); // Release touch

		// Perform the swipe action
		driver.perform(Collections.singletonList(swipe));
		System.out.println("Scrolled horizontally.");
	}

	// ✅ Method to randomly select an item from the category
	public void selectItem() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// Wait until the item list is visible
			List<WebElement> itemList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					AppiumBy.xpath("//android.widget.LinearLayout[@resource-id='com.pays.pos:id/linear_item']")));

			// Check if items exist
			if (itemList.isEmpty()) {
				System.out.println("No items found in category.");
				return;
			}

			// Randomly select an item
			Random random = new Random();
			int itemIndex = random.nextInt(itemList.size());
			WebElement randomItem = itemList.get(itemIndex);
			randomItem.click(); // Click the randomly selected item
			System.out.println("Random item selected: " + (itemIndex + 1));

			// Call method to update modifiers for the selected item
			selectRandomModifiers();

		} catch (Exception e) {
			System.out.println("Error selecting a random item: " + e.getMessage());
		}
	}

	// ✅ Method to randomly update item modifiers
	public boolean selectRandomModifiers() {
	    Random random = new Random();

	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // Wait for modifiers to be visible
	        List<WebElement> modifierList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
	                AppiumBy.xpath("(//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.pays.pos:id/llMain'])")));

	        if (modifierList.isEmpty()) {
	            System.out.println("No modifiers found.");
	            return false;
	        }

	        int numModifiersToSelect = 1; // Select 1 or 2 modifiers randomly
	        System.out.println("Selecting " + numModifiersToSelect + " modifiers.");

	        for (int i = 0; i < numModifiersToSelect; i++) {
	            int modifierIndex = random.nextInt(modifierList.size());
	            WebElement modifier = modifierList.get(modifierIndex);

	            wait.until(ExpectedConditions.elementToBeClickable(modifier)).click();
	            System.out.println("Modifier selected: " + modifier.getText());
	        }

	        // Click the "Done" button after selecting modifiers
	        WebElement doneButton = wait.until(ExpectedConditions.elementToBeClickable(
	                AppiumBy.xpath("//android.widget.TextView[@resource-id='com.pays.pos:id/txtDone']")));
	        doneButton.click();
	        System.out.println("Done button clicked after selecting modifiers.");

	        return true;

	    } catch (Exception e) {
	        System.out.println("Error selecting modifiers: " + e.getMessage());
	        return false;
	    }
	}

	

	// ✅ Method to select a modifier if present
	private void selectModifiersIfPresent() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		try {
			// ✅ Wait until modifier elements are visible
			List<WebElement> modifierElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(AppiumBy
					.xpath("//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.pays.pos:id/llMain']")));

			if (!modifierElements.isEmpty()) {
				modifierElements.get(0).click(); // Select the first modifier if available
				System.out.println("Modifier selected.");
			}

			// ✅ Click the "Done" button if visible
			WebElement doneButton = wait.until(ExpectedConditions.presenceOfElementLocated(
					AppiumBy.xpath("//android.widget.TextView[@resource-id='com.pays.pos:id/txtDone']")));
			doneButton.click();
			System.out.println("Done button clicked.");

		} catch (Exception e) {
			System.out.println("Error while selecting modifier: " + e.getMessage());
		}
	}

	// ✅ Method to click the "Pay" button when it becomes visible
	public void Paynow() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement payButton = wait.until(ExpectedConditions.visibilityOf(Pay));
			payButton.click();
			System.out.println("Pay button clicked.");

		} catch (Exception e) {
			System.out.println("Error while clicking Pay button: " + e.getMessage());
		}
	}

	public void backtohome() {
		wait.until(ExpectedConditions.visibilityOf(SaveButton)).click();
	}
	
	
	public void ClickModifiers()
	{
		WebElement ModifiersButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy
				.xpath("//android.widget.TextView[@resource-id='com.pays.pos:id/txt_title' and @text='Modifiers']")));
		ModifiersButton.click(); 
	}
	
	public void CreateModifiers() {
		WebElement createmod = wait.until(ExpectedConditions.elementToBeClickable(CreateModifier));
		createmod.click(); 

	}
	
	public void SetModifiersInfo(String ModifierInfo,String size) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Wait for Modifier Set Name field and enter text
	    WebElement modifierNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        AppiumBy.xpath("//android.widget.EditText[@text='Modifier Set Name *']")));
	    modifierNameField.sendKeys(ModifierInfo);

	    // Click on modifier edit field to open keyboard
	    WebElement editText = wait.until(ExpectedConditions.elementToBeClickable(
	        AppiumBy.id("com.pays.pos:id/edtModifier")));
	    editText.click();

	    // Add a short delay to ensure the keyboard stays open
	    Thread.sleep(500);

	    // Enter keys one by one
	    drive.pressKey(new KeyEvent(AndroidKey.H));
	  

	    // Wait for edit name field and enter size
	    WebElement editMod = wait.until(ExpectedConditions.elementToBeClickable(
	        AppiumBy.id("com.pays.pos:id/edtName")));
	    editMod.clear();
	    editMod.sendKeys(size);

	    // Hide keyboard after typing
	    drive.hideKeyboard();
	    // Click save to finalize item creation
			wait.until(ExpectedConditions.elementToBeClickable(SaveButton)).click();

			// Wait for pop-up confirmation message
			WebElement popUpMessage = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy
					.xpath("//android.widget.FrameLayout[@resource-id='android:id/custom']/android.widget.LinearLayout")));

			// Verify that the pop-up message is displayed
			Assert.assertTrue(popUpMessage.isDisplayed(), "Pop-up message is not displayed.");

			// Retrieve and validate success message
			String categorycreatedmsg = wait
					.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/tvMessage"))).getText();
			Assert.assertTrue(categorycreatedmsg.contains("Modifier Set was successfully created."), "Modifier not Created.");

			// Click on OK button to close the pop-up
			WebElement okButton = wait
					.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.pays.pos:id/tvSave") // Ensure this
																												// ID
																												// matches
																												// the
																												// actual OK
																												// button
					));
			okButton.click();


//		WebElement modifierField1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
//		    AppiumBy.xpath("//android.widget.EditText[@resource-id='com.pays.pos:id/edtModifier' and @text='Modifier']")
//		));
//		modifierField1.sendKeys("hi");


	}
}