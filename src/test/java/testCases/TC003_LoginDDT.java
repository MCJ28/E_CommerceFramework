package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.homePage;
import pageObjects.loginPage;
import pageObjects.myAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass 
{
    // Link the DataProvider class and specific method
	
    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = {"Master", "Datadriven"}) 
    public void verify_loginDDT(String email, String pwd, String expectedResult) 
    {
        logger.info("**** Starting TC_003_LoginDDT ****"); 
        

        try 
        {
            // Step 1: Navigation
        	
            homePage hp = new homePage(driver);
            
            hp.myAccount_btn();
            logger.info("Clicked on My Account menu");  
            
            hp.login_btn();
            logger.info("Clicked on Login link");

            
            // Step 2: Login Action            
            
            loginPage lp = new loginPage(driver);
            
            logger.info("Entering login credentials...");
            
            lp.setEmail(email); 	//From Excel
            lp.setPassword(pwd); 	//From Excel
            
            lp.login_button();
            logger.info("Clicked on Login button");

            
            // Step 3: Verification
            
            myAccountPage macc = new myAccountPage(driver);
            
            boolean targetPageExists = macc.isMyAccountPageExists();
            
            logger.info("Is 'My Account' page displayed? : " + targetPageExists);

            
            /*
             * VALIDATION LOGIC:
             * Data is Valid -> Login successful -> Pass (and logout)
             * Data is Valid -> Login failed -> Fail
             * Data is Invalid -> Login successful -> Fail (it's a bug! and logout)
             * Data is Invalid -> Login failed -> Pass
             */
            
            
            if (expectedResult.equalsIgnoreCase("Valid")) 
            {
                logger.info("Validating positive scenario (Expected: Valid)...");
                
                if (targetPageExists) 
                {
                    logger.info("Login successful with valid data. Test Passed.");
                    macc.logout(); 
                    logger.info("Logged out successfully to reset for next iteration.");
                    Assert.assertTrue(true); 
                } 
                
                else 
                {
                    logger.error("Login failed with valid data! Test Failed.");
                    Assert.assertTrue(false);
                }
            }

            
            if (expectedResult.equalsIgnoreCase("Invalid")) 
            {
                logger.info("Validating negative scenario (Expected: Invalid)...");
                
                if (targetPageExists) 
                {
                    logger.error("Login successful with INVALID data! This is a bug. Test Failed.");
                    macc.logout(); 
                    logger.info("Logged out to reset for next iteration.");
                    Assert.assertTrue(false);  
                } 
                
                else 
                {
                    logger.info("Login correctly rejected invalid data. Test Passed.");
                    Assert.assertTrue(true);
                }
            }

        } 
        
        catch (Exception e) 
        {
            logger.error("Test iteration failed due to exception: " + e.getMessage());
            Assert.fail("Exception occurred: " + e.getMessage());
        }
        
        logger.info("**** Finished TC_003_LoginDDT ****");
    }
}