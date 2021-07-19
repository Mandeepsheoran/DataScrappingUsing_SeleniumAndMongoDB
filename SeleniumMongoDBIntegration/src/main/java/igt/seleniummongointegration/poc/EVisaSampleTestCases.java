package igt.seleniummongointegration.poc;



import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(MongoClientListener.class)
public class EVisaSampleTestCases {
	
	WebDriver driver;
	
	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		//ChromeOptions co = new ChromeOptions();
		//co.addArguments("--headless");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://evisa.rop.gov.om");
	}
	
	@Test(priority=1,description="Test to check contact status")
	public void checkContactUs() {
		Assert.assertTrue(driver.findElement(By.linkText("Contact Us")).isDisplayed());
	}
	
	@Test(priority=3,description="Login Button link check")
	public void loginButtonDisplay() {
		Assert.assertTrue(driver.findElement(By.linkText("Loginn")).isDisplayed());
	}
	
	@Test(priority=2,description="Home page title for eVisa")
	public void eVisaHomePageTitle() {
		Assert.assertEquals(driver.getTitle(), "Home - Evisa");
	}
	
	@Test(priority=5,description="Liferay screen-Check Application status")
	public void trackAppWithStatus() {
		Assert.assertTrue(driver.findElement(By.linkText("Track Your Application")).isDisplayed());
	}
	
	@Test(priority=4,description="Footer link check")
	public void loginFooterLink() {
		Assert.assertTrue(driver.findElement(By.linkText("Sitemap")).isDisplayed());
	}
	
	public void tearDown() {
		driver.quit();
	}

}
