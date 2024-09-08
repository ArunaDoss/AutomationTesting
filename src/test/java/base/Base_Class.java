package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.io.FileInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Allure;



public class Base_Class {

	public static WebDriver driver;

	

	@BeforeSuite
	public void startBrowser() {
		openBrowser("edge");
		driver.manage().window().maximize();
		driver.get("https://magento.softwaretestingboard.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
	}

	@AfterSuite
	public void endBrowser() {
		driver.close();
	}

	public WebDriver openBrowser(String browser) {
		switch (browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			throw new WebDriverException("Invalid Browser: " + browser);
		}
		return driver;
	}

	public static void getScreenshots() {

		String timestamp = new SimpleDateFormat("MM_dd__hh_mm_ss").format(new Date());
		TakesScreenshot sourcefile = ((TakesScreenshot) driver);
		File srcFile = sourcefile.getScreenshotAs(OutputType.FILE);
		File destFile = new File(".//Screenshots//" + timestamp + ".png");
		try {
			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Allure.addAttachment("Screenshot",
					new FileInputStream(System.getProperty("user.dir") + "\\Screenshots\\" + timestamp + ".png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public SoftAssert softAssert() { //SoftAssert - Execution will not be stopped until verifying all the conditions  but
		//in normal assert if a condition fails the execution stops and throws a error msg.

		SoftAssert softAssert = new SoftAssert();
		return softAssert;

	}

	public Actions actions() { // Action classs helps us to mouse-over the webelements

		Actions actions = new Actions(driver); //Actions-class name, actions-objects, 
		//new- key word is for object creation and Actions is a constructor () -word within bracket is a parameter 
		return actions;

	}
	
	public WebDriverWait explictWait(Duration duration) {
		
		WebDriverWait explictWait = new WebDriverWait(driver,duration );
		return explictWait;
	}
	

	public FluentWait<WebDriver> fluentWait(Duration duration, Duration pollingTime) {
		// Define FluentWait with WebDriver as the generic type
		FluentWait<WebDriver> fluentWait = new FluentWait<>(driver).withTimeout(duration).pollingEvery(pollingTime);
		return fluentWait;
	}

}
