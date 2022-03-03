package XISHE_Tests.XISHE_Assignment2;

import java.awt.List;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Tests {

	public ExtentReports extent;
	public ExtentTest logger;
	public WebDriver driver;
	LandingPage land = new LandingPage(driver);
	String totalresults;
	LinkedList<Object> results = new LinkedList<Object>();
	LinkedList<Object> resultsafterrefresh = new LinkedList<Object>();

	@BeforeTest
	public void BeforeTests() {
		extent = new ExtentReports(".\\ExtentReport.html", true);
		logger = extent.startTest("Automation Scenarios for ADP Website", "Test Name");
	}

	@BeforeMethod
	public void BeforeMethods() {
		System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@Test
	public void TestCase1() {

		logger.log(LogStatus.INFO, "Starting 'FirstTest' testcase ...");
		// Go to URL
		driver.get("https://www.tamm.abudhabi/ar-AE/");

		// Change Language
		driver.findElement(land.language).click();

		// Search for "Abudhabi Police"
		driver.findElement(land.searchbox).sendKeys("Abudhabi Police");
		driver.findElement(land.searchbox).sendKeys(Keys.ENTER);

		// Get the count
		totalresults = driver.findElement(land.totalresults).getText().replace("Showing 1-10 of", "");
		System.out.println(totalresults);

		// Store first 5 elements to a list and print the list
		for (int i = 1; i <= 5; i++) {
			results.add(driver
					.findElement(By.xpath("(//h4[contains(@class,'ui-lib-link__heading ui-lib-bold')])[" + i + "]"))
					.getText());
		}
		System.out.println(results);

		// Refresh the page and check the count after each refresh
		for (int i = 1; i <= 5; i++) {
			driver.navigate().refresh();
			Assert.assertTrue(driver.findElement(land.totalresults).getText().contains("Showing 1-10 of 603 results"));
			resultsafterrefresh.add(driver
					.findElement(By.xpath("(//h4[contains(@class,'ui-lib-link__heading ui-lib-bold')])[" + i + "]"))
					.getText());

		}
		Assert.assertEquals(resultsafterrefresh, results);
		logger.log(LogStatus.INFO, "Finishing 'FirstTest' testcase ...");

	}

	@Test
	public void TestCase2() throws InterruptedException {

		logger.log(LogStatus.INFO, "Starting 'SecondTest' testcase ...");
		// Go to URL
		driver.get("https://www.tamm.abudhabi");

		// Click Burger menu button
		driver.findElement(land.burgetbutton).click();

		// Click "Abu Dhabi Govt Entities" link
		driver.findElement(land.abudubaientitieslnk).click();

		// Search for ADP
		driver.findElement(land.abudubaisearchbox).sendKeys("ADP");
		driver.findElement(land.abudubaisearchbox).sendKeys(Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver,100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(land.adudubailnk));

		// Go through the results making sure they have A,D,P
		int numberofitems = driver.findElements(land.abudubairesult).size();
		for (int i = 0; i < numberofitems; i++) {
			Assert.assertTrue(driver.findElement(land.abudubairesult).getText().contains("A"));
			Assert.assertTrue(driver.findElement(land.abudubairesult).getText().contains("D"));
			Assert.assertTrue(driver.findElement(land.abudubairesult).getText().contains("P"));
		}

		// Click "Abu Dhabi Police " link
		driver.findElement(land.adudubailnk).click();

		// Get number of results
		wait.until(ExpectedConditions.visibilityOfElementLocated(land.abudubaitotalresults));
		System.out.println(driver.findElement(land.abudubaitotalresults).getText());
		Assert.assertTrue(driver.findElement(land.abudubaitotalresults).getText().contains("72 results"));
		logger.log(LogStatus.INFO, "Finishing 'SecondTest' testcase ...");
	}

	@AfterMethod
	public void TearDownTest(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, " Test case FAILED");
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, " Test Case PASSED");
		}

		driver.quit();
	}

	@AfterTest
	public void ExitExtentReport() throws Exception {
		extent.flush();
	}
}
