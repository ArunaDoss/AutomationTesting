package testcase;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base_Class;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import webelements.HomepageElements;

public class TestClass extends Base_Class {

	HomepageElements homePage;
	// HomepageElements is the class name, homePage is the object name, new is the
	// keyword for object creation and the HomepageElements() is the Constructor



	@Test(priority = 0)
	@Description("Verifying the Title")
	public void verifyTitle() {
		Assert.assertEquals(driver.getTitle(), "Home Page");

	}

	@Test(priority = 1)
	@Description("Checking whether the user can type and delete in the search bar")
	public void searchBarEditing() {
		HomepageElements homePage = PageFactory.initElements(driver, HomepageElements.class);
		homePage.search().sendKeys("women");
		Allure.addAttachment("Entering value into searchbox", "women");
		assertEquals(homePage.search().getAttribute("value"), "women");
		getScreenshots();
		homePage.search().clear();

	}
	
	@Test(priority = 2)
	@Description("Checking whether the user can type and delete in the search bar")
	public void searchFunctionality() {
		HomepageElements homePage = PageFactory.initElements(driver, HomepageElements.class);
		homePage.search().sendKeys("man");
		Allure.addAttachment("Entering value into searchbox", "man");
		homePage.search().sendKeys(Keys.ENTER);
		Assert.assertTrue(driver.getCurrentUrl().contains("man"));
		getScreenshots();
		

	}

	@Test(priority = 3, enabled = true)
	@Description("Checking if there is any broken images")
	public void checkingBrokenImages() {

		List<WebElement> imageLink = driver.findElements(By.tagName("img"));
		for (WebElement images : imageLink) {
			System.out.println(images.getAttribute("src"));
			if (images != null) {
				String src = images.getAttribute("src");
				System.out.println("Checking image URL: " + src);

				// Check if the src attribute is not null or empty
				if (src != null && !src.isEmpty()) {
					try {
						// Create a URL object from the src attribute
						URL url = new URL(src);

						// Open a connection to the URL
						HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

						// Set the request method to "HEAD" to fetch only the headers
						httpURLConnection.setRequestMethod("HEAD");

						// Connect to the URL
						httpURLConnection.connect();

						// Get the response code
						int responseCode = httpURLConnection.getResponseCode();

						// If the response code is 400 or greater, the image is considered broken
						if (responseCode >= 400) {

							String brokenImageMessage = "Broken image: " + src + " - Response code: " + responseCode;
							System.out.println(brokenImageMessage);
							Allure.addAttachment("Broken image found", brokenImageMessage);
							softAssert().fail(brokenImageMessage);

						} else {
							String ImageMessage = "Good image: " + src + " - Response code: " + responseCode;
							System.out.println(ImageMessage);

						}

					} catch (IOException e) {
						// Handle any exceptions that occur during the connection process
						System.out.println("Error checking image: " + src);
						e.printStackTrace();
						softAssert().fail(src);
					}
				} else {
					// If the src attribute is null or empty, log this information
					System.out.println("Image source is null or empty.");
					softAssert().fail("Image source is null or empty.");
				}

			}
			softAssert().assertAll();
		}
	}

	@Test(priority = 4,enabled = false)
	@Description("Checking if there are any broken links")
	public void checkingBrokenLinks() {

		List<WebElement> links = driver.findElements(By.tagName("a"));
		for (WebElement linksCheck : links) {
			String href = linksCheck.getAttribute("href");

			// Check if the href attribute is not null and not empty
			if (href != null && !href.isEmpty()) {
				System.out.println(href);

				Allure.addAttachment("Checking for Broken URL: ", href);

				try {
					// Create a URL object from the href attribute
					URL url = new URL(href);

					// Open a connection to the URL
					HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

					// Set the request method to "HEAD" to fetch only the headers
					httpURLConnection.setRequestMethod("HEAD");

					// Connect to the URL
					httpURLConnection.connect();

					// Get the response code
					int responseCode = httpURLConnection.getResponseCode();

					// If the response code is 400 or greater, the link is considered broken
					if (responseCode >= 400) {
						String brokenLinkMessage = "Broken Link: " + href + " - Response code: " + responseCode;
						System.out.println(brokenLinkMessage);
						Allure.addAttachment("Broken Link found", brokenLinkMessage);
						softAssert().fail(brokenLinkMessage);

					} else {
						String validLinkMessage = "Valid Link: " + href + " - Response code: " + responseCode;
						System.out.println(validLinkMessage);
					}

				} catch (IOException e) {
					// Handle any exceptions that occur during the connection process
					System.out.println("Error checking Link: " + href);
					e.printStackTrace();
					softAssert().fail("Error checking Link: " + href);
				}
			} else {
				// Log if the href is null or empty
				System.out.println("Link source is null or empty.");
				softAssert().fail("Link source is null or empty.");
			}

			// Make sure to execute the assertions after each iteration
			softAssert().assertAll();
		}
	}

	@Test(priority = 5)
	@Description("Check whether user can add items to the cart")
	public void addItemsToCart() throws InterruptedException {
		HomepageElements homePage = PageFactory.initElements(driver, HomepageElements.class);
		actions().moveToElement(homePage.men()).build().perform();
		actions().moveToElement(homePage.topsMen()).build().perform();
		homePage.menJacket().click();
		

		// clicking the first availabel jacket
		driver.findElement(By.xpath("//img[@class=\"product-image-photo\"]")).click();
		((JavascriptExecutor) driver).executeScript(
	            "document.querySelector('body > div.page-wrapper > div.horizontal.loaded > div > div.ea-content').style.display='none';"
	        );
		// checkingBrokenImages();
		homePage.xL().click();
		homePage.blackColor().click();
		
		homePage.addToCart().click();
		Thread.sleep(2000);
		Allure.addAttachment("Log: ", "An Item Added to the cart.");		
		if (homePage.getCartCount() > 0) {
			Allure.addAttachment("Number of Items added to your cart: ", ""+homePage.getCartCount());
			Allure.addAttachment("Status message of cart: ", homePage.messageBox().getText());
			getScreenshots();
		} else {
			softAssert().fail("Items not added to cart");
		}

		// clicking the first availabel jacket

	}

	@Test(priority = 6)
	@Description("Checking whether user can remove the added item from the cart")
	public void removeItemFromCart() {
		HomepageElements homePage = PageFactory.initElements(driver, HomepageElements.class);
		homePage.shoppingCart().click();
		homePage.removeItem().click();
		if (homePage.getCartCount() >0) {
			Allure.addAttachment("Item removed, Number of Items in the cart: ", ""+homePage.getCartCount());
			getScreenshots();
		} else {
			Allure.addAttachment("Item removed, Number of Items in the cart: ", ""+homePage.getCartCount());
			softAssert().fail("Items Not Removed from Cart");
			
		}

	}
	

	@DataProvider(name = "productData")
	public Object[][] getProductData() {
		return new Object[][] { { "XS", "Black" }, { "S", "Blue" }, { "M", "Orange" }, { "L", "Black" },
				{ "XL", "Blue" }};
	}

	@Test(priority = 7)
	@Description(" All Pair Testing - Add items to the cart with different size and color combinations")
	public void allPairTesting() throws InterruptedException {
		HomepageElements homePage = PageFactory.initElements(driver, HomepageElements.class);
		redirecting(homePage);
		Object[][] data = getProductData(); // Calling the DataProvider manually

		for (Object[] row : data) {
			String size = (String) row[0];
			String color = (String) row[1];

			performAddToCartTest(size, color, homePage);
			//homePage.getCartCount();
		}
		fluentWait(Duration.ofSeconds(15),Duration.ofMillis(1)).until(ExpectedConditions.invisibilityOf(homePage.cartLoader()));
		homePage.getCartCount();
	}

	@Step("Redirecting to Men's Jacket Page")
	public void redirecting(HomepageElements homePage) {

		actions().moveToElement(homePage.men()).build().perform();
		actions().moveToElement(homePage.topsMen()).build().perform();
		homePage.menJacket().click();
		driver.findElement(By.xpath("//img[@class=\"product-image-photo\"]")).click();

	}

	@Step("Adding item to cart with Size: {0} and Color: {1}")
	public void performAddToCartTest(String size, String color, HomepageElements homePage) throws InterruptedException {

		// Select size and color
		homePage.selectSize(size).click();
		homePage.selectColor(color).click();
		// Wait until the "Add to Cart" button returns to its original state
		explictWait(Duration.ofSeconds(10))
		.until(ExpectedConditions.textToBePresentInElement(homePage.addToCart(), "Add to Cart"));
		// Click on Add to Cart button
		homePage.addToCart().click();

		// Verify the cart count
		
	//	homePage.getCartCount();

		// Attach details to Allure report
		Allure.addAttachment("Added item to cart", "Size: " + size + ", Color: " + color);
		Allure.addAttachment("Number of Items added to your cart: ", ""+homePage.getCartCount());
		Allure.addAttachment("Status message of cart: ", homePage.messageBox().getText());

		// Capture screenshot
		getScreenshots();
	}


}
