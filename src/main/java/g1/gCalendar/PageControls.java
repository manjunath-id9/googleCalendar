package g1.gCalendar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageControls {
	
//	private static WebElement element = null;
	
	private WebDriver driver;
	
	@FindBy(name="q")
	WebElement searchText;
	
	@FindBy(xpath="//*[@id='rso']/div[1]/div/div/div/div/h3/a")
	WebElement calendarLink;
	
	@FindBy(css="input[type='email']")
	WebElement emailField;
	
	@FindBy(xpath="//*[@id='identifierNext']/content/span")
	WebElement gotoPasswordPage;
	
	@FindBy(xpath="//*[@id='password']/div[1]/div/div[1]/input")
	WebElement passwordField;
	
	@FindBy(xpath="//*[@id='passwordNext']/content/span")
	WebElement loginBtn;
	
	@FindBy(className="Ip8zfc")
	WebElement createEventBtn;
	
	@FindBy(xpath="//*[@id='xTiIn']")
	WebElement enterEventName;
	
	@FindBy(xpath="//*[@id='xSaveBu']/content/span")
	WebElement saveEventBtn;
	
	@FindBy(xpath="//*[@id='gb']/div[2]/div[2]/div[3]/div/div/div[3]/div/content/span")
	WebElement settingsBtn;
	
	@FindBy(xpath="//div[contains(text(),'Settings')]")
	WebElement settingsOption;
	
	@FindBy(xpath="//*[@id='YPCqFe']/div/div/div/div[1]/div[3]/div")
	WebElement timeFormatDropdown;
	
	@FindBy(xpath="//*[@id='YPCqFe']/div/div/div[1]/div[2]/div[1]/div/div[1]")
	WebElement startDay;
	
	@FindBy(xpath="//*[@id='gb']/div[2]/div[2]/div[3]/div/div/div[1]/div/content/span")
	WebElement searchEvent;
	
	@FindBy(className="L1Ysrb")
	WebElement searchEventTextBox;
	
	@FindBy(xpath="//*[@id='xDetDlgDelBu']/content/span/span")
	WebElement deleteEventBtn;
	
	
	 public PageControls(WebDriver driver){
	       this.driver=driver;

	       //Initialise Elements
	       PageFactory.initElements(driver, this);
	   }
	
	public void  txtSearch(String search)
	{
		searchText.sendKeys(search);
	}

	public  WebElement clickCalendarLink(WebDriver driver)
	{
		return calendarLink;
	}
	
	public  WebElement enterUserName()
	{
		return emailField;
	}
	
	public  WebElement gotoPasswordPage()
	{
		return gotoPasswordPage;
	}
	
	public  WebElement enterPassword()
	{
		return passwordField;
	}
	
	public  WebElement clickLoginBtn()
	{
		return loginBtn;
	}
	
	public void createEvent()
	{
		createEventBtn.click();
	}
	
	
	
	
}
