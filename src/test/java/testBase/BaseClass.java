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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass 
{
	public static WebDriver driver;
	public Logger logger; 
	public Properties p; 
	
	@Parameters({"os", "browser"})
	@BeforeClass(groups = {"Sanity", "Regression", "Master", "DataDriven"}) 
	public void setUp(String operatingSystem, String browserName) throws IOException
	{
		// Loading config.properties file
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file); 
				
		// Initializing the logger for the current class
		logger = LogManager.getLogger(this.getClass());
		
		logger.info("**** Starting Browser Setup ****");  
		
		// Check execution environment (Local vs Remote)
		String executionEnv = p.getProperty("execution_env", "local"); 
		
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
				capabilities.setPlatform(Platform.LINUX); 
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
			
			
			
			// --- NEW HEADLESS LOGIC ---
			// Check if we are running inside GitHub Actions
			boolean isCI = System.getenv("GITHUB_ACTIONS") != null;
			
			switch(browserName.toLowerCase())
			{
				case "chrome" : 
					ChromeOptions chromeOptions = new ChromeOptions();
					if(isCI) chromeOptions.addArguments("--headless=new"); // Runs invisibly in CI
					driver=new ChromeDriver(chromeOptions); 
					break; 
				case "edge" : 
					EdgeOptions edgeOptions = new EdgeOptions();
					if(isCI) edgeOptions.addArguments("--headless=new");
					driver=new EdgeDriver(edgeOptions); 
					break;
				case "firefox" : 
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					if(isCI) firefoxOptions.addArguments("--headless");
					driver=new FirefoxDriver(firefoxOptions); 
					break;
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
		String generatedString = RandomStringUtils.randomAlphabetic(5).toLowerCase();
		return generatedString.substring(0, 1).toUpperCase() + generatedString.substring(1); 
	}
	
	public String randomPassword() 
	{
		String upper = RandomStringUtils.randomAlphabetic(2).toUpperCase();
		String lower = RandomStringUtils.randomAlphabetic(3).toLowerCase();
		String num = RandomStringUtils.randomNumeric(3);
		String special = RandomStringUtils.random(2, "!@#$%^&*");
		
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