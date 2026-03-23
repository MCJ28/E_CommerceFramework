package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.homePage;
import pageObjects.registerPage;
import testBase.BaseClass;

public class TC001_RegisterTest extends BaseClass 
{
	@Test(groups = {"Sanity", "Master"}) 
	public void testRegister() throws InterruptedException
	{
		logger.info("**** Starting TC001_RegisterTest ****"); 
		
		try
		{
			homePage hp = new homePage(driver); 
			
			hp.myAccount_btn();
			logger.info("Clicked on My Account button");
			
			hp.register_btn();
			logger.info("Clicked on Register link");
			
			registerPage rp = new registerPage(driver); 
			
			logger.info("Providing customer details...");
			
			String complexPassword = randomPassword(); 
			
			rp.set_first_name(randomName());
			rp.set_last_name(randomName());
			rp.set_email(randomString() + "@gmail.com");
			rp.set_telephone(randomNumeric());
			rp.set_password(complexPassword);
			rp.set_confirm_password(complexPassword);  
			rp.privacy_policy_btn();
			logger.info("Accepted Privacy Policy");
			
			rp.Continue_btn();
			logger.info("Clicked on Continue button");
			
			String text = rp.account_success_page();
			
			logger.info("Validating expected message...");
			
			if(text.equals("Your Account Has Been Created!")) 
			{
				logger.info("Registration Test Passed!");
				Assert.assertTrue(true);
			} 
			
			else 
			{
				logger.error("Registration Test Failed.");
				logger.debug("Actual text found: " + text); 
				Assert.fail("Confirmation message does not match.");
			}
		}
		
		catch(Exception e)
		{
			logger.error("Test Case Failed due to exception: " + e.getMessage());
			Assert.fail();
		}
		
		logger.info("**** Finished TC001_RegisterTest ****");
	}		
}
