package com.monoprice.pageObjects;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import monoprice_demo.AutoSuggestionsDemo;

public class MonoPrice_homePage {

	WebDriver driver;
	
	
	@FindBy(css = "input#keyword")
	private WebElement input_search_box;

	@FindBy(xpath = "//*[contains(@class,'unbxd-as-wrapper unbxd-as-extra-left')]")
	private WebElement auto_suggestions;

	@FindBy(xpath = "//*[contains(@class,'unbxd-as-insuggestion unbxd-ac-selected')]")
	private List<WebElement> infield_keywords;
	

	public MonoPrice_homePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}



	Properties prop = LoginPage.readproperties();
	WebDriverWait wait = new WebDriverWait(driver, 40);

	public void validate_AutoSuggestions(String searchtext) throws InterruptedException {
		
		wait.until(ExpectedConditions.elementToBeClickable(input_search_box));
		
		driver.findElement(By.cssSelector("input#keyword")).clear();
		driver.findElement(By.cssSelector("input#keyword")).sendKeys(searchtext);
		
		wait.until(ExpectedConditions.visibilityOf(auto_suggestions));
		boolean auto_sugg_flag = auto_suggestions.isDisplayed();
		Assert.assertTrue(auto_sugg_flag);
	}

	public void clickOnInfieldsKeywords(String searchtextforAutosuggestions) {

		
		input_search_box.clear();
		input_search_box.sendKeys(searchtextforAutosuggestions);
		wait.until(ExpectedConditions.visibilityOf(auto_suggestions));
		
		wait.until(ExpectedConditions.visibilityOfAllElements(infield_keywords));
		driver.findElement(By.xpath("//*[contains(@data-type,'IN_FIELD')][@class='unbxd-as-insuggestion unbxd-ac-selected']")).click();

//		for (WebElement infield : infield_keywords) {
//			wait.until(ExpectedConditions.visibilityOf(infield));
//			
//			if (infield.isDisplayed()) {
//				Actions action = new Actions(driver);
//				action.moveToElement(infield).build().perform();
//				infield.click();
//				System.out.println("Clicked infield keyword");
//				break;
//			}
//		}
	}

}
