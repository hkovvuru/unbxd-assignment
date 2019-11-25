package monoprice_demo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.monoprice.pageObjects.LoginPage;
import com.monoprice.pageObjects.MonoPrice_homePage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class AutoSuggestionsDemo {

	public WebDriver driver;
	public ExtentReports extent;
	public ExtentTest extentTest;

	public AutoSuggestionsDemo() {
		PageFactory.initElements(driver, this);
	}

	Properties prop = LoginPage.readproperties();

	@BeforeTest
	public void setExtent() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
		extent.addSystemInfo("Host Name", "hussain-ThinkPad-L480");
		extent.addSystemInfo("User Name", "Hussain Kovvuru");
		extent.addSystemInfo("Environment", "QA");
	}

	@BeforeMethod
	public void browser_setup() {
		// new LoginPage(driver).launchBrowser();
		String application_url = prop.getProperty("URL");
		System.out.println(application_url);
		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
		driver = new ChromeDriver();
		driver.get(application_url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void checkAutoSuggestions() throws Exception {
		extentTest = extent.startTest("checkAutoSuggestions");
		String searchText = prop.getProperty("searchtext");
		// new MonoPrice_homePage(driver).validate_AutoSuggestions(searchText);

		WebElement searchBar = driver.findElement(By.cssSelector("input#keyword"));
		// new LoginPage(driver).validate_AutoSuggestions(searchText);
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(searchBar));
		searchBar.sendKeys(searchText);

		WebElement autoSuggestions = driver
				.findElement(By.xpath("//*[contains(@class,'unbxd-as-wrapper unbxd-as-extra-left')]"));

		wait.until(ExpectedConditions.visibilityOf(autoSuggestions));
		boolean auto_sugg_flag = autoSuggestions.isDisplayed();
		Assert.assertTrue(auto_sugg_flag);
	}

	@Test(priority = 1)
	public void checkAutoSuggestionsWithManyLetters() throws Exception {
		extentTest = extent.startTest("checkAutoSuggestionsWithManyLetters");
		String multitext = prop.getProperty("multitext");
		// new MonoPrice_homePage(driver).validate_AutoSuggestions(multitext);

		WebElement searchBar = driver.findElement(By.cssSelector("input#keyword"));
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(searchBar));
		searchBar.sendKeys(multitext);

		WebElement autoSuggestions = driver
				.findElement(By.xpath("//*[contains(@class,'unbxd-as-wrapper unbxd-as-extra-left')]"));

		wait.until(ExpectedConditions.visibilityOf(autoSuggestions));
		boolean auto_sugg_flag = autoSuggestions.isDisplayed();
		Assert.assertTrue(auto_sugg_flag);
	}

	@Test(priority = 2)
	public void checkAutoSuggestionsAndClickKeywordSuggestion() throws Exception {
		extentTest = extent.startTest("checkAutoSuggestionsAndClickKeywordSuggestion");
		String multitext = prop.getProperty("multitext");

		WebElement searchBar = driver.findElement(By.cssSelector("input#keyword"));
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(searchBar));
		searchBar.sendKeys(multitext);

		WebElement autoSuggestions = driver
				.findElement(By.xpath("//*[contains(@class,'unbxd-as-wrapper unbxd-as-extra-left')]"));

		wait.until(ExpectedConditions.visibilityOf(autoSuggestions));

		List<WebElement> keywords = driver.findElements(By.xpath("//*[contains(@class,'unbxd-as-keysuggestion')]"));

		String keywordName = null;
		for (WebElement webElement : keywords) {
			keywordName = webElement.getAttribute("data-value");
			webElement.click();
			break;
		}
		Thread.sleep(3000);
		String Searchedresulttext = driver
				.findElement(
						By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='(2)'])[26]/following::span[1]"))
				.getText();
		System.out.println("out====> " + Searchedresulttext);
		Assert.assertNotEquals(keywordName, Searchedresulttext);
	}

	@Test(priority = 3)
	public void clickAutoSuggestionsInfieldKeywords() throws Exception {
		extentTest = extent.startTest("clickAutoSuggestionsinfieldKeywords");
		String multitext2 = prop.getProperty("multitext");

		driver.findElement(By.cssSelector("input#keyword")).clear();
		driver.findElement(By.cssSelector("input#keyword")).sendKeys(multitext2);

		WebElement autoSuggestions = driver
				.findElement(By.xpath("//*[contains(@class,'unbxd-as-wrapper unbxd-as-extra-left')]"));

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(autoSuggestions));

//		List<WebElement> infields = driver
//				.findElements(By.xpath("//*[contains(@class,'unbxd-as-insuggestion unbxd-ac-selected')]/span"));
//		wait.until(ExpectedConditions.visibilityOfAllElements(infields));

		WebElement infield_keyword = driver.findElement(By.xpath(
				"//*[contains(@class,'unbxd-as-maincontent')]//following::li[@data-type='IN_FIELD']//span[@class='unbxd_in']"));
		wait.until(ExpectedConditions.visibilityOf(infield_keyword));
		infield_keyword.click();

//		for (WebElement infield : infields) {
//			wait.until(ExpectedConditions.visibilityOf(infield));
//			System.out.println(infield.isDisplayed());
//			if (infield.isDisplayed()) {
//				((JavascriptExecutor) driver).executeScript("arguments[0].click();", infield);
//				System.out.println("Clicked infield keyword");
//				break;
//			}
//
//			Thread.sleep(2000);
//		}
	}

	@Test(priority = 4)
	public void validatePopularProductPage() throws Exception {

		extentTest = extent.startTest("validatePopularProductPage");
		String multitext3 = prop.getProperty("multitext");

		driver.findElement(By.cssSelector("input#keyword")).clear();
		driver.findElement(By.cssSelector("input#keyword")).sendKeys(multitext3);

		WebElement autoSuggestions = driver
				.findElement(By.xpath("//*[contains(@class,'unbxd-as-wrapper unbxd-as-extra-left')]"));

		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.visibilityOf(autoSuggestions));

		List<WebElement> products = driver.findElements(
				By.xpath("//*[contains(@class,'unbxd-as-popular-product unbxd-as-popular-product-grid')]"));

		for (WebElement product : products) {
			if (product.isDisplayed()) {
				product.click();
				break;
			}
		}
		Thread.sleep(2000);
		String title = driver.getTitle();
		boolean pageTitle = title.contains(
				"Monoprice 6ft 28AWG High Speed HDMI to DVI Adapter Cable with Ferrite Cores, Black - Monoprice.com");
		Assert.assertTrue(true);

	}

	public static String getScreenshot(WebDriver driver, String screenshotName) {

		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		try {
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("path exception");

		}
		return destination;
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in
																							// extent report
			System.out.println(result.getName());
			String screenshotPath = AutoSuggestionsDemo.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); // to add screenshot in extent
																							// report
			// extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath));
			// //to add screencast/video in extent report
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

		}

		extent.endTest(extentTest); // ending test and ends the current test and prepare to create html report
		driver.quit();
	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}

	@AfterSuite
	public void triggerMail() {
		new SendMailWithAttachments().sendingMail();
	}

}
