package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass 
{
	public static WebDriver driver;
	public Logger logger; // Added Logger variable
	public Properties p; // Properties variable
	
	@Parameters({"os", "browser"})
	@BeforeClass(groups = {"Sanity", "Regression", "Master", "DataDriven"}) 
	public void setUp(String operatingSystem, String browserName) throws IOException
	{
		// Loading config.properties file
		FileReader file = new FileReader("./src/test/resources/config.properties"); 	// . is the Project Location
		p = new Properties();
		p.load(file); 
				
				
		// Initializing the logger for the current class
		logger = LogManager.getLogger(this.getClass());
		
		logger.info("**** Starting Browser Setup ****");  
		
		
		
		// NEW LOGIC: Check execution environment (Local vs Remote)
				
		String executionEnv = p.getProperty("execution_env", "local"); // Defaults to local if missing
		
		if(executionEnv.equalsIgnoreCase("remote"))
		{
			logger.info("Executing on Remote Environment (Docker/Grid)..."); 
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			
			// 1. Determine OS for Remote Execution
			
			if(operatingSystem.equalsIgnoreCase("windows")) 
			{
				capabilities.setPlatform(Platform.WIN11); 
			} 
			
			else if (operatingSystem.equalsIgnoreCase("mac")) 
			{
				capabilities.setPlatform(Platform.MAC);
			} 
			
			else if (operatingSystem.equalsIgnoreCase("linux")) 
			{
				capabilities.setPlatform(Platform.LINUX); // Docker containers run on Linux
			} 
			
			else 
			{
				logger.error("No matching OS identified for Remote Execution");
				return;
			}
			
			
			// 2. Determine Browser for Remote Execution
			
			switch(browserName.toLowerCase()) 
			{
				case "chrome": capabilities.setBrowserName("chrome"); break;
				case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
				case "firefox": capabilities.setBrowserName("firefox"); break;
				default: logger.error("No matching browser identified for Remote Execution"); return; 
			}
			
			
			// 3. Initialize RemoteWebDriver
			
			driver = new RemoteWebDriver(new URL(p.getProperty("gridURL")), capabilities);
		}
		
		else if(executionEnv.equalsIgnoreCase("local"))
		{
			logger.info("Executing on Local Environment...");
			
			// Local execution logic
			
			switch(browserName.toLowerCase())
			{
				case "chrome" : driver=new ChromeDriver(); break; 
				case "edge" : driver=new EdgeDriver(); break;
				case "firefox" : driver=new FirefoxDriver(); break;
				default: logger.error("Invalid browser name provided!"); return; 
			} 
		}
		
		else 
		{
			logger.error("Invalid execution_env parameter in config.properties! Use 'local' or 'remote'.");
			return;
		}
		
		// standard setup

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  
		
		driver.get(p.getProperty("appURL")); 
		logger.info("Launched " + browserName + " successfully."); 
	}
	
	
	
	public String randomName() 
	{
		// Generates a 5-letter string entirely in lowercase
		String generatedString = RandomStringUtils.randomAlphabetic(5).toLowerCase();
		
		// Capitalizes the first letter and attaches the rest
		return generatedString.substring(0, 1).toUpperCase() + generatedString.substring(1); 
	}
	
	public String randomPassword() 
	{
		// Generates specific types to GUARANTEE they are present in the final password
		String upper = RandomStringUtils.randomAlphabetic(2).toUpperCase();
		String lower = RandomStringUtils.randomAlphabetic(3).toLowerCase();
		String num = RandomStringUtils.randomNumeric(3);
		String special = RandomStringUtils.random(2, "!@#$%^&*");
		
		// Combines them into a single 10-character string
		return upper + lower + num + special; 
	}

	public String randomString() 
	{
		return RandomStringUtils.randomAlphabetic(5);
	}
	
	public String randomNumeric() 
	{
		return RandomStringUtils.randomNumeric(10);
	}
	
	
	
	public String captureScreen(String tname) throws IOException 
	{
	    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	    
	    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
	    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
	    
	    // Path matches your 'screenshots' folder in the project structure
	    String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
	    File targetFile = new File(targetFilePath);
	    
	    sourceFile.renameTo(targetFile);
	    return targetFilePath;
	}
	
	
	@AfterClass(groups = {"Sanity", "Regression", "Master", "DataDriven"})
	public void tearDown()
	{
		logger.info("**** Closing Browser ****");
		driver.quit();
	}
}
