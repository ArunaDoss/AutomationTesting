package webelements;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.Base_Class;

public class HomepageElements extends Base_Class{
	
	@FindBy(xpath = "//span[text()=\"What's New\"]")
	private WebElement whatsNew;

	@FindBy(xpath = "//span[text()=\"Women\"]")
	private WebElement women;

	@FindBy(xpath = "//span[text()=\"Men\"]")
	private WebElement men;

	public WebElement men() {
		return men;
	}

	@FindBy(xpath = "//a[contains(@href,\"men/tops-men.html\")]")
	private WebElement topsMen;

	public WebElement topsMen() {
		return topsMen;
	}

	@FindBy(xpath = "//a[@id=\"ui-id-19\"]")
	private WebElement menJacket;

	public WebElement menJacket() {
		return menJacket;

	}

	public WebElement selectColor(String color) {
		String first = "//div[@aria-label=\"";//"//div[@aria-label=\Black\"
		String second = "\"]";
		WebElement selectColor = driver.findElement(By.xpath(first + color + second));
		System.out.println(first + color + second);
		return selectColor;
	}

	public WebElement selectSize(String size) {

		String first = "//div[@aria-label=\"";
		String second = "\"]";
		WebElement selectSize = driver.findElement(By.xpath(first + size + second));
		System.out.println(first + size + second);
		return selectSize;
	}

//	

	@FindBy(xpath = "//div[@aria-label=\"Black\"]")
	private WebElement blackColor;

	public WebElement blackColor() {
		return blackColor;
	}

	@FindBy(xpath = "//div[@aria-label=\"XL\"]")
	private WebElement xL;

	public WebElement xL() {
		return xL;
	}

	@FindBy(xpath = "//button[@title=\"Add to Cart\"]")
	private WebElement addToCart;

	public WebElement addToCart() {
		return addToCart;
	}

	@FindBy(xpath = "//span[text()=\"Gear\"]")
	private WebElement gear;

	@FindBy(xpath = "//span[text()=\"Training\"]")
	private WebElement training;

	@FindBy(xpath = "//span[text()=\"Sale\"]")
	private WebElement sale;

	@FindBy(xpath = "//input[@id=\"search\"]")
	private WebElement search;

	@FindBy(xpath = "//a[@data-bind=\"scope: 'minicart_content'\"]")
	private WebElement cart;

	@FindBy(xpath = "//span[@class=\"counter-number\"]")
	private WebElement cartCount;

	public WebElement cartCount() {
		return cartCount;
	}

	@FindBy(css = "#maincontent > div.page.messages > div:nth-child(2) > div > div > div")
	private WebElement messageBox;

	public WebElement messageBox() {
		return messageBox;
	}

	@FindBy(xpath = "//span[text()=\"Shop New Yoga\"]")
	private WebElement shopNewYoga;

	@FindBy(xpath = "//span[text()=\"Shop Pants\"]")
	private WebElement shopPants;

	@FindBy(xpath = "//span[text()=\"Shop Tees\"]")
	private WebElement tees;

	public WebElement search() {
		return search;
	}

	public WebElement shopNewYoga() {
		return shopNewYoga;
	}

	@FindBy(xpath = "//a[text()=\"shopping cart\"]")
	private WebElement shoppingCart;

	public WebElement shoppingCart() {
		return shoppingCart;
	}

	@FindBy(xpath = "//*[@id=\"shopping-cart-table\"]/tbody/tr[2]/td/div/a[2]")
	private WebElement removeItem;
	
	public WebElement removeItem() {
		return removeItem;
	}
	
	@FindBy(xpath = "//div[@class=\"loading-mask\"]")
	private WebElement cartLoader;
	
	public WebElement cartLoader() {
		return cartLoader;
	}
	
	public int getCartCount() {
	    int cartCount = 0;
	    
	    // Wait for the cart loader to disappear
	    //explictWait(Duration.ofSeconds(15)).until(ExpectedConditions.invisibilityOf(cartLoader()));
	    
	   // fluentWait(Duration.ofSeconds(15),Duration.ofMillis(1)).until(ExpectedConditions.invisibilityOf(cartLoader()));
	    
	    // Get the cart count as a string
	    String s = cartCount().getText().trim();  // Use trim() to remove any leading/trailing spaces
	    
	    System.out.println("Cart count is: " + s);
	    
	    // Check if the string is empty or null
	    if (s == null || s.isEmpty()) {
	        cartCount = 0;  // If the string is empty or null, set cart count to 0
	    } else {
	        try {
	            cartCount = Integer.parseInt(s);  // Try to parse the string to an integer
	        } catch (NumberFormatException e) {
	            System.out.println("Failed to parse cart count, setting cart count to 0.");
	            cartCount = 0;  // If parsing fails, set cart count to 0
	        }
	    }
	    
	    return cartCount;
	}
	
	@FindBy(css="body > div.page-wrapper > div.horizontal.loaded > div > div.ea-content")
	private WebElement adSpan;
	
	public WebElement adSpan() {
		return adSpan;
	}
	



	
	
}

