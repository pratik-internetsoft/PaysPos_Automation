package Pays.posfinaltesting;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pays.posfinaltesting.actions.LoginClass;
import Pays.posfinaltesting.actions.PasscodeClass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.NoSuchElementException;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Login Managment") // High-level feature or module
@Feature("Login And Passcode") // Feature under test
@Story("Login ") 
public class LoginPasscodeTest extends BaseTest {

    LoginClass login;
    @BeforeMethod
    public void Login_setup() {
    	login = new LoginClass(driver);
    }

    @Test
    @Description("Test login functionality with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("Login Feature")
    @Story("Valid login test")
    public void LogintoApp() throws MalformedURLException, URISyntaxException, InterruptedException {  
        if (login.isLoggedIn()) {
            System.out.println("Already logged in");
            return;
        }

        try {
            login.enterEmail("automation@yopmail.com");
            login.enterPassword("654321");
            login.clickLogin();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

   
        } catch (Exception e) {
            Assert.fail("Login attempt failed due to an exception: " + e.getMessage());
        }
    }

    @Test
    @Description("PassCode functionality with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("Pass Feature")
    @Story("Valid login test")
    public void Passcode() {
        if (login.isLoggedIn()) {
            System.out.println("Already logged in");
            return;
        }

        try {
            PasscodeClass code = new PasscodeClass(driver);
            code.pass();
  
        } catch (Exception e) {
            Assert.fail("Passcode login attempt failed: " + e.getMessage());
        }
    }
}
 