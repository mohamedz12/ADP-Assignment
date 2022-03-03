package XISHE_Tests.XISHE_Assignment2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LandingPage {
	

	    WebDriver driver;

	    By language = By.xpath(("//a[@href='/en']"));

	    By searchbox = By.xpath(("(//input[@placeholder='What are you looking for?'])[2]"));

	    By totalresults =By.xpath("//p[contains(@class,'totalResultsText')]");

	    By searchresult = By.xpath("//h4[contains(@class,'ui-lib-link__heading ui-lib-bold')]");
	    
	    By burgetbutton= By.xpath("//a[contains(@class,'TPN-header__action-burger-btn')]");
	    
	    By abudubaientitieslnk=By.xpath("//a[@href='/en/abu-dhabi-government-entities']");
	    
	    By abudubaisearchbox=By.xpath("//input[@placeholder='Search here ...']");
	    
	    By adudubailnk=By.xpath("//a[contains(.,' Abu Dhabi Police ')]");
	    
	    By abudubaitotalresults =By.xpath("//div[contains(@class,'results-count')]");
	    
	    By abudubairesult=By.xpath("//h2[contains(@class,'TPN-adge-card__Title')]/a");

	    public LandingPage(WebDriver driver){

	        this.driver = driver;

	    }

 }

