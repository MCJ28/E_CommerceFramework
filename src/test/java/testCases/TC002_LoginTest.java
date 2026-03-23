package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.homePage;
import pageObjects.loginPage;
import pageObjects.myAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass
{
	@Test(groups = {"Regression", "Master"})
	public void testLogin()
	{
		logger.info("**** Starting TC002_LoginTest ****"); 
		
		try
		{
			// Step 1: Navigate to Login
			
			homePage hp = new homePage(driver); 
			
			hp.myAccount_btn();
			logger.info("Clicked on My Account");
			
			hp.login_btn();
			logger.info("Clicked on Login link");
			
			
			
			// Step 2: Perform Login Actions
			
			loginPage lp = new loginPage(driver);
			
			logger.info("Entering login credentials...");
			
			lp.setEmail(p.getProperty("Email"));
			lp.setPassword(p.getProperty("Password"));
			
			lp.login_button();
			logger.info("Clicked login button");
			
			
			
			
			// Step 3: Validation on My Account Page
			
			myAccountPage account = new myAccountPage(driver); 
			
			boolean targetPageExists = account.isMyAccountPageExists(); 
			
			
			logger.info("Validating login success message...");  
			
			if(targetPageExists)
			{
				logger.info("Login Test Passed!");   
				Assert.assertTrue(true);
			}
			
			else
			{
				logger.error("Login Test Failed. 'My Account' page did not display.");
				Assert.fail("Login validation failed.");
			}
		}
		
		catch(Exception e)
		{
			logger.error("An unexpected error occurred during Login Test: " + e.getMessage());
			Assert.fail("Test failed due to an exception: " + e.getMessage());
		}
		
		logger.info("**** Finished TC002_LoginTest ****");
	}
}
