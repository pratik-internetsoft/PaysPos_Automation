package Pays.posfinaltesting;


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pays.posfinaltesting.actions.Categories_ItemsClass;
import Pays.posfinaltesting.actions.LoginClass;
import Pays.posfinaltesting.actions.TransactionClass;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;


public class CategoriesandItemsTest extends BaseTest {
    
    private Categories_ItemsClass category;
    private Categories_ItemsClass items;
    private TransactionClass tc;
    private Categories_ItemsClass modifiers;
    

 
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        category = new Categories_ItemsClass(driver);
        items = new Categories_ItemsClass(driver);
        tc = new TransactionClass(driver); 
        modifiers = new Categories_ItemsClass(driver);
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws InterruptedException {
        String testName = result.getMethod().getMethodName();

        // Run @AfterMethod only after "Items_Transaction" executes
        if (testName.equals("Items_Transaction")) {
            category.MenuBurgerClick();
            category.backtohome();
        }
    }

    @Test(groups = {"login"})
    public void Login() throws MalformedURLException, URISyntaxException, InterruptedException {
        LoginClass lg = new LoginClass(driver);
        lg.LogintoApp();
        lg.Passcode();
    }

    // ---------------------- CATEGORIES TEST CASES ----------------------

    @Test(groups = {"categories"}, dependsOnMethods = "Login")
    public void Categories_Select_Categories() throws InterruptedException {
        category.MenuBurgerClick();
        category.MenuButtonClick();
        category.ClickCategories();
    }

    @Test(groups = {"categories"}, dependsOnMethods = "Categories_Select_Categories")
    public void Categories_CreateCategoriesClick() {
        category.CreateCategory();
    }

    @Test(groups = {"categories"}, dependsOnMethods = "Categories_CreateCategoriesClick")
    public void Categories_SetCategoryname() {
        category.SetcategoryName("French");
    }

    @Test(groups = {"categories"}, dependsOnMethods = "Categories_SetCategoryname")
    public void Categories_SaveCategory() {
        category.SaveCategory();
    }

    @Test(groups = {"categories"}, dependsOnMethods = "Categories_SaveCategory")
    public void Categories_CheckIfCategoryIsAdded() {
        category.scrollToElement("French");
    }

    // ---------------------- MODIFIERS TEST CASES ----------------------

    @Test(dependsOnMethods = "Categories_CheckIfCategoryIsAdded")
    public void Modifiers_ClickModifiers() throws InterruptedException {
    	
        modifiers.ClickModifiers();

    }
    
    @Test(dependsOnMethods = "Modifiers_ClickModifiers")
    public void Modifiers_ClickCreate() {
    	modifiers.CreateModifiers();
    }
    // Define Pointer Input for Touch Actions

    @Test(dependsOnMethods ="Modifiers_ClickCreate")
    public void Modifiers_SetModifiersInfo() throws InterruptedException {
        modifiers.SetModifiersInfo("Shape","thin");
        
   
    }
    // ---------------------- ITEMS TEST CASES ---------------------
    

    @Test(groups = {"items"}, dependsOnMethods = "Modifiers_SetModifiersInfo")
    public void Items_AddItems() {
        items.ClickItems();  
        items.AddItem();     
    }

    @Test(groups = {"items"}, dependsOnMethods = "Items_AddItems")
    public void Items_SetItem() {
        items.SetItem("Pineapple");      
        items.SetItemInfo("French"); 
    }

    @Test(groups = {"items"}, dependsOnMethods = "Items_SetItem")
    public void Items_Home() {
       items.selectHome();         
        items.selectCategory("French"); 
        items.selectItem();        
    }

    @Test(groups = {"items"}, dependsOnMethods = "Items_Home")
    public void Items_SelectPay() {
        items.Paynow(); 
    }

    // ---------------------- TRANSACTION TEST CASE ----------------------

    @Test(groups = {"transaction"}, dependsOnMethods = "Items_SelectPay")
    public void Items_Transaction() {
        boolean transaction = tc.validateFinancialDetails(); 
        Assert.assertTrue(transaction, "Financial Details are incorrect");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        boolean result = tc.performCashTransaction();
        assert result : "Function did not return true!";
    }

}