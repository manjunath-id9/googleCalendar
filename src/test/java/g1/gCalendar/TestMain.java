package g1.gCalendar;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class TestMain {

	 public static String driverPath = "C:/Users/manjunath.d/BrowserDrivers/";
	 public static WebDriver driver;
	 PageControls pageControls;
	 
	 String eventName = "Test Event1";
	@BeforeTest 
	public void startBrowser() throws InterruptedException
	{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.out.println("launching chrome browser");
		System.setProperty("webdriver.chrome.driver", Constants.driverPath);
		driver = new ChromeDriver(options);
	}
	
	// This method is not used as of now. Can be used incase if other changes needed for chrome browser settings
	public void disableChromeNotificationPopups()
	{
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
	}
	
	
	public void openGoogleCalendar() throws InterruptedException
	{
		driver.manage().window().maximize();
		driver.navigate().to(Constants.url);
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(pageControls.searchText));
		pageControls.txtSearch(Constants.searchCalendar+"\n");
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(pageControls.calendarLink));
		pageControls.calendarLink.click();
	}
	
	
	@Test
	public void startSearch() throws InterruptedException
	{
		pageControls = new PageControls(driver);
		
		// Search and open Google Calendar
		openGoogleCalendar();		
		
		// Login to Gmail account
		loginGmail();
		
		// Change the time format to 1:00 PM and validate same
		validate_changedTimeFormat();	
		
		// Change the dayname to Sunday and validate same
		change_StartingDay();
	}
	
	
	public void change_StartingDay() throws InterruptedException
	{
		Actions actions =  new Actions(driver);
		String startDay = pageControls.startDay.getText();
		System.out.println("Old start day name: "+startDay);
		Assert.assertTrue(startDay.contains("Mon"));
		
		pageControls.settingsBtn.click();
		Thread.sleep(1000);
		pageControls.settingsOption.click();

		Thread.sleep(2000);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		WebElement changeDay = driver.findElement(By.xpath("//*[@id='YPCqFe']/div/div/div/div[5]/div[6]/div/div/div[1]/div[1]/div[3]"));
		je.executeScript("arguments[0].scrollIntoView(true);",changeDay);
		changeDay.click();
		
		Thread.sleep(1000);
		actions.sendKeys(Keys.ARROW_UP).build().perform();
		Thread.sleep(1000);
		actions.sendKeys(Keys.ENTER).build().perform();
		
		driver.navigate().back();
		
		System.out.println("New start day name: "+startDay);
		Assert.assertTrue(startDay.contains("Sun"));
		
		Thread.sleep(1000);
		
		WebElement searchEvent = pageControls.searchEvent;
		if(searchEvent.isDisplayed()){
		searchEvent.click();
		}
		
		WebElement searchBox = driver.findElement(By.className("gb_9e"));
		searchBox.sendKeys(eventName+"\n");
		Thread.sleep(2000);
		WebElement searcResults = pageControls.searchEventTextBox;
		
		if(searcResults.isDisplayed())
		{
			searcResults.click();
			Thread.sleep(1000);
		}
		
		WebElement deleteEvent = pageControls.deleteEventBtn;
		if(deleteEvent.isDisplayed() && deleteEvent.isEnabled()){
			deleteEvent.click();
		}
		
		WebElement validateDeletedEvent = driver.findElement(By.className("ytDhE"));
		Assert.assertEquals("No results found", validateDeletedEvent.getText().toString());
		
		System.out.println("Test Case Passed");
		
	}
	
	
	
	public void validate_changedTimeFormat() throws InterruptedException
	{
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.visibilityOf(pageControls.createEventBtn));
				
				pageControls.createEvent();
				Thread.sleep(1000);
				
				//Enter the event name
				driver.findElement(By.xpath("//*[@id='xTiIn']")).sendKeys(eventName);
				Thread.sleep(500);
				
				//Save event 
				driver.findElement(By.xpath("//*[@id='xSaveBu']/content/span")).click();
				Thread.sleep(1000);
			
				// Goto Settings Page
				WebElement settings = driver.findElement(By.xpath("//*[@id='gb']/div[2]/div[2]/div[3]/div/div/div[3]/div/content/span"));
				settings.click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//div[contains(text(),'Settings')]")).click();
				Thread.sleep(1000);
				
				//click on Time Format dropdown
				WebElement timeFormat = driver.findElement(By.xpath("//*[@id='YPCqFe']/div/div/div/div[1]/div[3]/div"));
				timeFormat.click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//*[@id='YPCqFe']/div/div/div/div[1]/div[3]/div/div/div[2]/div[1]/content")).click();
				
				// goto Previous page
				driver.navigate().back();
				
				WebElement t1 = driver.findElement(By.className("lFe10c "));
				String completeText = t1.getText().toString();
				System.out.println("Old value of t1:"+ completeText);
				
				String time = completeText.substring(completeText.length()-7, completeText.length());
				time = time.replaceAll("\r", "").replaceAll("\n", "");
				
				System.out.println("New value of t1:"+time.toString());
				
				// Assertion for 1:00 PM time format
				Assert.assertTrue(Pattern.compile("[0-9]|[10-12]-[0-9][10-12][am|pm]").matcher(time.toString()).find());
				
				// Assertion for other time format
				//Assert.assertTrue(Pattern.compile("[0-9]|1[0-2]):[0-5][0-9]-([1-9]|1[0-2]):[0-5][0-9][am|pm]").matcher(time.toString()).find());
							
	}
		
	public void loginGmail() throws InterruptedException
	{
		WebDriverWait wait =  new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf((pageControls.emailField)));
		pageControls.enterUserName().sendKeys(Constants.username);
		pageControls.gotoPasswordPage().click();
		Thread.sleep(2000);
		
		wait.until(ExpectedConditions.visibilityOf((pageControls.passwordField)));
		pageControls.enterPassword().sendKeys(Constants.password);
		pageControls.clickLoginBtn().click();
		Thread.sleep(1000);
	}
	

	@AfterTest
	public void closeBrowser()
	{
		//driver.quit();
	}
	
}
