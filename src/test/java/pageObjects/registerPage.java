package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class registerPage 
{
	WebDriver driver;
	
	public registerPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this); 
	}
	
	
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement first_name;
	
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement last_name;
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement email;
	
	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement telephone;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement password;
	
	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement confirm_password;
	
	@FindBy(xpath="//input[@name='agree']")
	WebElement privacy_policy;
	
	@FindBy(xpath="//input[@value='Continue']")
	WebElement continue_btn;
	
	@FindBy(xpath="//h1[contains(text(),'Your Account')]")
	WebElement register_account_success_page;
	
	
	
	public void set_first_name(String FirstName)
	{
		first_name.sendKeys(FirstName);
	}
	
	public void set_last_name(String LastName)
	{
		last_name.sendKeys(LastName);
	}
	
	public void set_email(String Email)
	{
		email.sendKeys(Email);
	}	
	
	public void set_telephone(String Telephone)
	{
		telephone.sendKeys(Telephone);
	}
	
	public void set_password(String Password)
	{
		password.sendKeys(Password);
	}
	
	public void set_confirm_password(String Confirm_Password)
	{
		confirm_password.sendKeys(Confirm_Password);
	}
	
	public void privacy_policy_btn()
	{
		// solution 1
		privacy_policy.click();
		
		// solution 2
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("arguments[0].click();", privacy_policy);

		// solution 3
		// Actions act = new Actions(driver);
		// act.click(privacy_policy).build().perform();

		// solution 4
		// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// WebElement element = wait.until(ExpectedConditions.elementToBeClickable(privacy_policy));
		// element.click();
	}
	
	public void Continue_btn()
	{
		//solution 1
		continue_btn.click();  
		
		//solution 2
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("arguments[0].click();", continue_btn); 
		
		//solution 3
		//Actions act = new Actions(driver);
		//act.click(continue_btn).build().perform(); 
		
		//solution 4
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		//WebElement element = wait.until(ExpectedConditions.elementToBeClickable(continue_btn));
		//element.click();
	}
	
	public String account_success_page()
	{	
		try
		{
			return register_account_success_page.getText(); 
		}
		
		catch(Exception e)
		{ 
			return e.getMessage();    
		}
	}
}
