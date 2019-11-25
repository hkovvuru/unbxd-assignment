package com.monoprice.pageObjects;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import monoprice_demo.AutoSuggestionsDemo;

public class LoginPage {

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(css = "input#keyword")
	private WebElement input_search_box;

	@FindBy(xpath = "//*[contains(@class,'unbxd-as-wrapper unbxd-as-extra-left')]")
	private WebElement auto_suggestions;

	@FindBy(xpath = "//*[contains(@class,'unbxd-as-insuggestion unbxd-ac-selected')]")
	private List<WebElement> infield_keywords;
	
	

	public static Properties readproperties() {
		Properties prop = new Properties();
		try {
			String path = System.getProperty("user.dir") + "/src/test/java/com/utils/config.properties";
			prop.load(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public void launchBrowser() {
		String application_url = readproperties().getProperty("URL");
		System.out.println(application_url);
		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
		driver = new ChromeDriver();
		driver.get(application_url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
}
