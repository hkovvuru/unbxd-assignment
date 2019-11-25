package monoprice_demo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestDemo {

//	public static void main(String[] args) {
//		
//
//	}

	//SendMailWithAttachments sms= new SendMailWithAttachments();
	WebDriver driver;

	@BeforeSuite
	public void browser_setup() {
		
		SendMailWithAttachments sms= new SendMailWithAttachments();

		String path = System.getProperty("user.dir");
		System.out.println(path);

		System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
		driver = new ChromeDriver();
		driver.get("https://www.monoprice.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void Test() {
		System.out.println("Hello");
	}

}
