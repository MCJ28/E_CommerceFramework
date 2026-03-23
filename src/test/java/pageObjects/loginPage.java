package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class loginPage 
{
	public WebDriver driver;
	
	public loginPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//input[@name='email']")
	WebElement email;
	
	@FindBy(xpath="//input[@name='password']")
	WebElement password; 
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement login_btn; 
	
	@FindBy(xpath="(//*[text()='My Account'])[4]")
	WebElement login_confirmation_msg;
	
	
	public void setEmail(String Email)
	{
		email.clear();
		email.sendKeys(Email);
	}
	
	public void setPassword(String Password)
	{
		password.clear();
		password.sendKeys(Password); 
	}
	
	public void login_button()
	{
		// solution 1
		login_btn.click();
		
		// solution 2
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("arguments[0].click();", login_btn);

		// solution 3
		// Actions act = new Actions(driver);
		// act.click(login_btn).build().perform();

		// solution 4
		// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// WebElement element = wait.until(ExpectedConditions.elementToBeClickable(login_btn));
		// element.click();
	}
}
