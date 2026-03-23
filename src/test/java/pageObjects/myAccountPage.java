package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class myAccountPage 
{
	public WebDriver driver;
	
	public myAccountPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//h2[text()='My Account']")
	WebElement myAccount_btn;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Logout']")
	WebElement logout_btn; 
	
	
	
	// NEW METHOD: Returns true if heading exists, false if it doesn't
	public boolean isMyAccountPageExists()
	{
		try
		{
			// 1. Temporarily reduce implicit wait to 2 seconds for this check
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2)); 
			
			// 2. Check if element is displayed
			boolean exists = myAccount_btn.isDisplayed(); 
			
			// 3. Restore implicit wait back to 10 seconds
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			return exists;
		}
		
		catch(Exception e)
		{
			// If element is not found, an exception is thrown. Restore wait and return false.
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			return false; 
		}
	}
	
	
	public void logout()
	{
		logout_btn.click();
	}
}
