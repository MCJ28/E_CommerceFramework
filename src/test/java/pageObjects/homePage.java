package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class homePage 
{
	WebDriver driver;
	
	public homePage(WebDriver driver) 
	{
		this.driver=driver;
		PageFactory.initElements(driver, this); 
	}	
	
	
	@FindBy (xpath="//span[@class='caret']")
	WebElement dropdown_myAccount;
	
	@FindBy (xpath="//a[text()='Register']")
	WebElement btn_register;
	
	@FindBy (xpath="//a[text()='Login']")
	WebElement btn_login;
	
	
	
	public void myAccount_btn()
	{
		dropdown_myAccount.click(); 
	}	
	
	public void register_btn()
	{
		btn_register.click(); 
	}
	
	public void login_btn()
	{
		btn_login.click();  
	}
	
}
