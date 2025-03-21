package Pays.posfinaltesting;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pays.posfinaltesting.actions.LoginClass;
import Pays.posfinaltesting.actions.PaxClass;
import io.qameta.allure.Description;

public class PaxTest extends BaseTest{
	@BeforeMethod
	public void Login(ITestResult result) throws MalformedURLException, URISyntaxException, InterruptedException {
		String testName = result.getMethod().getMethodName();
	
	    // Execute login logic only for specific test cases
	    if (testName.matches("Pax_ConnecttoPax")) {
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
	@Description("Test to verify Pax machine connection")
	public void Pax_ConnecttoPax() throws InterruptedException, TimeoutException {
	    PaxClass pax = new PaxClass(driver); // Initialize Pax class for connection
	    pax.MenuBurgerclick(); // Click the menu burger button
	    pax.HardwareButtonClick(); // Click the hardware button
	    pax.CreditcardClick(); // Click the credit card machine option
	    pax.Paxconnection(); // Connect to Pax machine
	}
}
