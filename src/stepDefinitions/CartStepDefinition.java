package stepDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.ecom.Listeners.ExtentReportListeners;

import Utility.WebUIUtilty;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CartStepDefinition extends  ExtentReportListeners {

	static  WebDriver driver;		
	String url="https://stg03.ecom.com/acc-login";
	public String prodName;
	
	String browser="browserstack";	
	String sUrl;
	WebUIUtilty utility = new WebUIUtilty();
	public static Properties prop;
	String loginMail="test20@ecom.com";
	
	@Before
	public void beforeSuite(){
		startResult();
		suiteTest =startTestModule("ecom Mobile Regression Testing", "Verify cart functionality");
	}
	
	@After
	public void afterSuite(){
		endResult();
		driver.quit();
	}
	
	
	@Given("Guest\\/Registered user is in home page")
	public void guest_Registered_user_is_in_home_page() {
		
		try {
			prop = new Properties();
			prop.load(new FileInputStream(new File("./GlobalSettings.properties")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sUrl = prop.getProperty("url");

		try {
			System.out.println("Browser is :"+browser); 
			driver = WebUIUtilty.launchBrowser(browser);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get(sUrl);
		reportStep(suiteTest ,"Page Loaded Successfully", "PASS",false);
		//driver.navigate().refresh();
		WebUIUtilty.timeOUt(5);
		WebElement policyPopUp =driver.findElement(By.xpath("//div[@class='close_btn_thick']"));
		if(policyPopUp.isDisplayed()) {
			policyPopUp.click();
		}else {
			System.out.println("Privacy policy message is not displayed on Home Page");
			reportStep(suiteTest ,"Privacy policy message is not displayed on Home Page", "INFO",false);
		}
	}

	@When("searched for anyproduct and item selected")
	public void searched_for_anyproduct_and_item_selected() {
		try {
		//WebUIUtilty.timeOUt(2);
		utility.explicitWait("//div[@class='icon_container']");
	    driver.findElement(By.xpath("//div[@class='icon_container']")).click();
		Actions searchProduct = new Actions(driver);
		searchProduct.sendKeys("chinos", Keys.ENTER).build().perform();
		reportStep(suiteTest ,"Searching for a product", "PASS",false);
		System.out.println("Searching for a product");
		reportStep(suiteTest ,"entered search key", "PASS",false);
		utility.explicitWait("(//img[@role='presentation'])[1]");
		driver.findElement(By.xpath("(//img[@role='presentation'])[1]")).click();
		reportStep(suiteTest ,"clicking the product", "PASS",false);
		utility.explicitWait("(//div[@class='img_holder'])[1]");
		driver.findElement(By.xpath("(//div[@class='img_holder'])[1]")).click();
		WebUIUtilty.timeOUt(2);
		reportStep(suiteTest ,"choosing the color", "PASS",false);
		utility.explicitWait("(//div[@class='size_option_wrapper'])[1]");
		WebUIUtilty.timeOUt(2);
		driver.findElement(By.xpath("(//div[@class='size_option_wrapper'])[1]")).click();
		reportStep(suiteTest ,"choosing the size", "PASS",false);
		prodName=driver.findElement(By.xpath("//*[@class='title']")).getText();
			}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'search a product' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("click on add to bag button")
	public void click_on_add_to_bag_button() {
		try {
		utility.explicitWait("//div[text()='Add to Bag']");
		driver.findElement(By.xpath("//div[text()='Add to Bag']")).click();
		reportStep(suiteTest ,"user clicked on Add to Bag", "PASS",false);
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to add to cart' case  "+e.getMessage(), "FAIL",false);
		}

		}


	@When("popup window displayed click on checkout")
	public void popup_window_displayed_click_on_checkout() {
		try {
		utility.explicitWait("//*[text()='CHECKOUT']");
		driver.findElement(By.xpath("//*[text()='CHECKOUT']")).click();

	   ////
		reportStep(suiteTest ,"user clicked on checkout", "PASS",false);
	   System.out.println("user clicked on checkout");
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'checkout button not clickable' case  "+e.getMessage(), "FAIL",false);
		}

		}


	@Then("cart page displayed")
	public void cart_page_displayed() {
		try {
		utility.explicitWait("//p[contains(@class,'styles__StyledPageTitle')]");
	   driver.findElement(By.xpath("//p[contains(@class,'styles__StyledPageTitle')]")).isDisplayed();
	   reportStep(suiteTest ," Card Page is Displayed", "PASS",false);
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'cart page not displayed' case  "+e.getMessage(), "FAIL",false);
		}

		}


	@Then("check display of items")
	public void check_display_of_items() {
		try {
		utility.explicitWait("//*[contains(@aria-label,'items in cart')]");
		driver.findElement(By.xpath("//*[contains(@aria-label,'items in cart')]")).click();
		utility.explicitWait("//*[@class='name']");
	   boolean selectedProduct = driver.findElement(By.xpath("//*[@class='name']")).isDisplayed();
	   if(selectedProduct) {
		   System.out.println("product is displayed in cart page");
		   reportStep(suiteTest ," product is displayed in cart page", "PASS",false);
	   }else {
		   System.out.println("Product is not displayed in cart page");
		   reportStep(suiteTest ," Product is not displayed in cart page", "FAIL",false);
	   }driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'cart details page' case  "+e.getMessage(), "FAIL",false);
		}

		}

	
	@Then("click on Mini cart from global header")
	public void click_on_Mini_cart_from_global_header() {
		try {
		driver.navigate().refresh();
		
		WebUIUtilty.scrollPageUP(1);
		utility.explicitWait("(//div[@class='icon_container']/following::a)[1]/div");
	    driver.findElement(By.xpath("(//div[@class='icon_container']/following::a)[1]/div")).click();
	    reportStep(suiteTest ,"mini cart is clicked", "PASS",false);
	   // driver.findElement(By.xpath("(//div[@class='img_holder'])[1]")).click();
	    
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'mini cart not clickable' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("User should be navigated to Shopping cart page")
	public void user_should_be_navigated_to_Shopping_cart_page() {
		try {
		utility.explicitWait("//*[contains(@class,'styles__StyledCartBadge')]");
	   driver.findElement(By.xpath("//*[contains(@class,'styles__StyledCartBadge')]")).isDisplayed();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'shopping cart page' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("check display of product block in shopping cart page.")
	public void check_display_of_product_block_in_shopping_cart_page() {
		try {
		utility.explicitWait("(//div[contains(@class,'styles__ItemWrapper')])[1]");
		boolean productDisplay = driver.findElement(By.xpath("(//div[contains(@class,'styles__ItemWrapper')])[1]")).isDisplayed();
		if(productDisplay) {
		   System.out.println("product block is displayed in shopping cart page");
		   reportStep(suiteTest ,"product block is displayed in shopping cart page", "PASS",false);
	   }else {
		   System.out.println("Product block is not displayed in shopping cart page");
		   reportStep(suiteTest ,"product block is not displayed in shopping cart page", "FAIL",false);
	   }
		driver.close();
	
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'shopping cart page' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@When("User search and select any product contains list and sale price")
	public void user_search_and_select_any_product_contains_list_and_sale_price() {
		try {
		utility.explicitWait("//div[@class='icon_container']");
		driver.findElement(By.xpath("//div[@class='icon_container']")).click();
		Actions searchProduct = new Actions(driver);
		searchProduct.sendKeys("Insulated Pants", Keys.ENTER).build().perform();
		System.out.println("Searching for a product");
		reportStep(suiteTest ,"searching a product", "PASS",false);
	
		utility.explicitWait("//*[contains(@class,'styles__StyledImage')]");
		driver.findElement(By.xpath("//*[contains(@class,'styles__StyledImage')]")).click();
		System.out.println("Product is clicked");
		reportStep(suiteTest ,"first product is clicked", "PASS",false);
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'search a product' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@Then("User clicks on add to bag button")
	public void user_clicks_on_add_to_bag_button() {
		try {
		utility.explicitWait("(//div[@class='img_holder'])[4]");
		driver.findElement(By.xpath("(//div[@class='img_holder'])[4]")).click();
		reportStep(suiteTest ,"color is chosen", "PASS",false);
		System.out.println("color is chosen");
		utility.explicitWait("//*[@class='size_option_wrapper']");
		driver.findElement(By.xpath("//*[@class='size_option_wrapper']")).click();
		reportStep(suiteTest ,"size is chosen", "PASS",false);
		System.out.println("size is chosen");
		utility.explicitWait("//div[text()='Add to Bag']");
		driver.findElement(By.xpath("//div[text()='Add to Bag']")).click();
		reportStep(suiteTest ,"add to bag  is clicked", "PASS",false);
		System.out.println("add to bag is clicked");
		utility.explicitWait("//*[text()='CHECKOUT']");
		driver.findElement(By.xpath("//*[text()='CHECKOUT']")).click();
		reportStep(suiteTest ,"checkout is clicked", "PASS",false);
	
//		driver.findElement(By.xpath("//*[@class='size_option_wrapper']")).click();
//		WebUIUtilty.timeOUt(2);
//		driver.findElement(By.xpath("//button[contains(@class,'add_to_cart')]")).click();
		System.out.println("Product is added into bag");
		}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to add to cart' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("User clicks on Mini cart from global header")
	public void user_clicks_on_Mini_cart_from_global_header() {
		driver.navigate().refresh();
		utility.explicitWait("//*[contains(@class,'styles__StyledCartBadge')]");
	    driver.findElement(By.xpath("//*[contains(@class,'styles__StyledCartBadge')]")).isDisplayed();
		}
	
	@When("^User clicks cart button$")
	public void user_clicks_cart_button() {
		try {
		driver.findElement(By.xpath("//*[contains(@class,'styles__StyledCartBadge')]")).click();
		
		reportStep(suiteTest ,"my bag is clicked", "PASS",false);
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to navigate to cart page' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("User should be navigated to shopping cart page")
	public void user_should_be_navigated_to_shopping_cart_page() {
		try {
		utility.explicitWait("//*[contains(@class,'styles__StyledCartBadge')]");
		driver.findElement(By.xpath("//*[contains(@class,'styles__StyledCartBadge')]")).click();
		reportStep(suiteTest ,"my bag is clicked", "PASS",false);
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'my bag button' case  "+e.getMessage(), "FAIL",false);
		}

		}


	@Then("User checks display of sale and list price in cart page")
	public void user_checks_display_of_sale_and_list_price_in_cart_page() {
		try {
		utility.explicitWait("//span[@class='final']");
//		WebElement listPrice = driver.findElement(By.xpath("//span[@class='crossout']"));
//		 System.out.println(listPrice.getText());
//		 WebUIUtilty.timeOUt(2);
		 WebElement finalPrice = driver.findElement(By.xpath("//span[@class='final']"));
		 System.out.println(finalPrice.getText());
		 reportStep(suiteTest ,"final price is displayed", "PASS",false);
		 driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'cart page' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@When("User searchs for any product and select")
	public void user_searchs_for_any_product_and_select() {
		try {
		utility.explicitWait("//div[@class='icon_container']");
		driver.findElement(By.xpath("//div[@class='icon_container']")).click();
		Actions searchProduct = new Actions(driver);
		searchProduct.sendKeys("shirt", Keys.ENTER).build().perform();
		System.out.println("Searching for a product");
		reportStep(suiteTest ,"searching for a product", "PASS",false);
	utility.explicitWait("//*[contains(@class,'styles__StyledImage')]");
		driver.findElement(By.xpath("//*[contains(@class,'styles__StyledImage')]")).click();
		reportStep(suiteTest ,"clicked the first product", "PASS",false);
		//*[@class='size_option_wrapper']
		
		
		
		}
		
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'add to cart page' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("User clicks on addto bag button")
	public void user_clicks_on_addto_bag_button() {
		try {
		
		utility.explicitWait("//div[text()='Add to Bag']");
		driver.findElement(By.xpath("//div[text()='Add to Bag']")).click();
		reportStep(suiteTest ,"add to bag is clicked", "PASS",false);
	utility.explicitWait("//*[text()='CHECKOUT']");
		driver.findElement(By.xpath("//*[text()='CHECKOUT']")).click();
		reportStep(suiteTest ,"checkout is clicked", "PASS",false);
	//	
//		driver.findElement(By.xpath("//*[@class='size_option_wrapper']")).click();
//		WebUIUtilty.timeOUt(2);
//		driver.findElement(By.xpath("//button[contains(@class,'add_to_cart')]")).click();
		System.out.println("Product is added into bag");
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to add to cart' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("User checks displayed Order Summary section in cart page.")
	public void user_checks_displayed_Order_Summary_section_in_cart_page() {
		try {
		utility.explicitWait("//div[@class='benefits_text']");
		WebElement rewards = driver.findElement(By.xpath("//div[@class='benefits_text']"));
		System.out.println(rewards.getText());
utility.explicitWait("//span[@class='summary_title']");
		boolean productTitle = driver.findElement(By.xpath("//span[@class='summary_title']")).isDisplayed();
		if(productTitle) {
			System.out.println("Product summary title is displayed");
			reportStep(suiteTest ,"Product summary title is displayed", "PASS",false);
		}else {
			System.out.println("Product summary title is not displayed");
			reportStep(suiteTest ,"Product summary title is not displayed", "FAIL",false);
		}
		utility.explicitWait("//input[@name='promoCode']");
		boolean promoCode= driver.findElement(By.xpath("//input[@name='promoCode']")).isDisplayed();
		if(promoCode) {
			System.out.println("PromoCode is displayed");
			reportStep(suiteTest ,"PromoCode is displayed", "PASS",false);
		}else {
			System.out.println("PromoCode is not displayed");
			reportStep(suiteTest ,"PromoCode is NOT displayed", "FAIL",false);
		}
		utility.explicitWait("//div[text()='Checkout now']");
		boolean checkOut =driver.findElement(By.xpath("//div[text()='Checkout now']")).isDisplayed();
		if(checkOut) {
			System.out.println("CheckOut button is displayed");
			reportStep(suiteTest ,"CheckOut button is displayed", "PASS",false);
		}else {
			System.out.println("CheckOut button is not displayed");
			reportStep(suiteTest ,"CheckOut button is not displayed", "FAIL",false);
		}
	   utility.explicitWait("//div[@class='helpText']");
		boolean helptext = driver.findElement(By.xpath("//div[@class='helpText']")).isDisplayed();
		if(helptext) {
			System.out.println("Chat with us and customer care no is displayed");
			reportStep(suiteTest ,"Chat with us and customer care no is displayed", "PASS",false);
		}else {
			System.out.println("Chat with us and customer care no is not displayed");
			reportStep(suiteTest ,"Chat with us and customer care no is not displayed", "FAIL",false);
		}
		driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'order summary page' case  "+e.getMessage(), "FAIL",false);
		}

		}


	@Then("User checks the display of Selected for you section.")
	public void user_checks_the_display_of_Selected_for_you_section() {
		try {
		utility.explicitWait("(//h3[@class='name'])[1]");
	    boolean productName = driver.findElement(By.xpath("(//h3[@class='name'])[1]")).isDisplayed();
	    if(productName) {
	    	System.out.println("Product name is displayed in My Bag");
	    	reportStep(suiteTest ,"Product name is displayed in My Bag", "PASS",false);
	    }else {
	    	System.out.println("Product name is not displayed in My Bag");
	    	reportStep(suiteTest ,"Product name is not displayed in My Bag", "FAIL",false);
	    }
	  utility.explicitWait("(//div[contains(@class,'styles__ItemPictureContainer')])[1]");
	    boolean productImage = driver.findElement(By.xpath("(//div[contains(@class,'styles__ItemPictureContainer')])[1]")).isDisplayed();
	    if(productImage) {
	    	System.out.println("Product image is displayed in My Bag");
	    	reportStep(suiteTest ,"Product image is displayed in My Bag", "PASS",false);
	    }else {
	    	System.out.println("Product image is not displayed in My Bag");
	    	reportStep(suiteTest ,"Product image is not displayed in My Bag", "FAIL",false);
	    }
	  utility.explicitWait("//select[@name='qty']");
	    boolean prodQty = driver.findElement(By.xpath("//select[@name='qty']")).isDisplayed();
	    if(prodQty) {
	    	System.out.println("Product qty is displayed in My Bag");
	    	reportStep(suiteTest ,"Product qty is displayed in My Bag", "PASS",false);
	    }else {
	    	System.out.println("Product qty is not displayed in My Bag");
	    	reportStep(suiteTest ,"Product qty is not displayed in My Bag", "FAIL",false);
	    }
utility.explicitWait("//div[@class='price']");
	    boolean prodPrice = driver.findElement(By.xpath("//div[@class='price']")).isDisplayed();
	    if(prodPrice) {
	    	System.out.println("Product price is displayed in My Bag");
	    	reportStep(suiteTest ,"Product price is displayed in My Bag", "PASS",false);
	    }else {
	    	System.out.println("Product price is not displayed in My Bag");
	    	reportStep(suiteTest ,"Product price is not displayed in My Bag", "FAIL",false);
	    }
	   utility.explicitWait("//div[@class='btn_wrapper']");
	    boolean donateButton = driver.findElement(By.xpath("//div[@class='btn_wrapper']")).isDisplayed();
	    if(donateButton) {
	    	System.out.println("Donate button is displayed");
	    	reportStep(suiteTest ,"Donate button is displayed", "PASS",false);
	    }else {
	    	System.out.println("Donate button is not displayed");
	    	reportStep(suiteTest ,"Donate button is not displayed", "FAIL",false);
	    }driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'cart details page' case  "+e.getMessage(), "FAIL",false);
		}

		}

		
	@Then("User checks the display of Save for later section")
	public void user_checks_the_display_of_Save_for_later_section() {
		try {
	    System.out.println("Save for later section isnot  displayed in shopping cart section");
	    reportStep(suiteTest ,"Save for later section is not displayed", "FAIL",false);
	driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'save for later is not displayed' case  "+e.getMessage(), "FAIL",false);
		}

		}

	
	@Then("User checks the display of {string} section.")
	public void user_checks_the_display_of_section(String string) {
		try {
	    System.out.println("People also purchased is not displayed ");
	    reportStep(suiteTest ,"people also purchased is not displayed", "FAIL",false);
		driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'people also purchased is not displayed' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("User clicks on edit link")
	public void user_clicks_on_edit_link() {
		try {
		utility.explicitWait("//span[text()='Edit']");
	   driver.findElement(By.xpath("//span[text()='Edit']")).click();
	   reportStep(suiteTest ,"edit button is clicked", "PASS",false);
	}
		
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'edit button not clickable' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("Edit item popup should be displayed with elements")
	public void edit_item_popup_should_be_displayed_with_elements() {
		try {
	utility.explicitWait("//h1[text()='Edit Item']");
		boolean editTitle =  driver.findElement(By.xpath("//h1[text()='Edit Item']")).isDisplayed();
		if(editTitle) {
			  System.out.println("Edit Item title is on edit item popUp window");
			  reportStep(suiteTest ,"Edit Item title is on edit item popUp window", "PASS",false);
		  }else {
			  System.out.println("Edit Item title is not on edit item popUp window");
			  reportStep(suiteTest ,"Edit Item title is not on edit item popUp window", "FAIL",false);
		  }
	 utility.explicitWait("//h3[@class='title']");
	  boolean prodName = driver.findElement(By.xpath("//h3[@class='title']")).isDisplayed();
	  if(prodName) {
		  System.out.println("Product name is on edit item popUp window");
		  reportStep(suiteTest ,"Product name is on edit item popUp window", "PASS",false);
	  }else {
		  System.out.println("Product name is not on edit item popUp window");
		  reportStep(suiteTest ,"Product name is not on edit item popUp window", "FAIL",false);
	  }
	utility.explicitWait("//div[@class='image_container']");
	  boolean prodImage = driver.findElement(By.xpath("//div[@class='image_container']")).isDisplayed();
	  if(prodImage) {
		  System.out.println("Product image is on edit item popUp window");
		  reportStep(suiteTest ,"Product image is on edit item popUp window", "PASS",false);
	  }else {
		  System.out.println("Product image is not on edit item popUp window");
		  reportStep(suiteTest ,"Product image is not on edit item popUp window", "FAIL",false);
	  }utility.explicitWait("//div[@class='img_holder']");
	  boolean colorSwatch = driver.findElement(By.xpath("//div[@class='img_holder']")).isDisplayed();
	  if(prodImage) {
		  System.out.println("Color swatch is displayed on edit item popUp window");
		  reportStep(suiteTest ,"Color swatch is displayed on edit item popUp window", "PASS",false);
	  }else {
		  System.out.println("Color swatch is not displayed on edit item popUp window");
		  reportStep(suiteTest ,"Color swatch is not displayed on edit item popUp window", "FAIL",false);
	  }utility.explicitWait("//span[@id='product-sizes']");
	  boolean sizeTabs = driver.findElement(By.xpath("//span[@id='product-sizes']")).isDisplayed();
	  if(sizeTabs) {
		  System.out.println("Product size tabs are on edit item popUp window");
		  reportStep(suiteTest ,"Product size tabs are on edit item popUp window", "PASS",false);
	  }else {
		  System.out.println("Product size tabs are not on edit item popUp window");
		  reportStep(suiteTest ,"Product size tabs are not on edit item popUp window", "FAIL",false);
	  }utility.explicitWait("//div[text()='Update Bag']");
	  boolean updateBag = driver.findElement(By.xpath("//div[text()='Update Bag']")).isDisplayed();
	  if(updateBag) {
		  System.out.println("Update bag button is on edit item popUp window");
		  reportStep(suiteTest ,"Update bag button is on edit item popUp window", "PASS",false);
	  }else {
		  System.out.println("Update bag button is not on edit item popUp window");
		  reportStep(suiteTest ,"Update bag button is not on edit item popUp window", "FAIL",false);
	  }utility.explicitWait("(//span[@class='close_icon_bag'])[2]");
	 boolean closeIcon = driver.findElement(By.xpath("(//span[@class='close_icon_bag'])[2]")).isDisplayed();
	 if(closeIcon) {
		  System.out.println("Close icon bag is on edit item popUp window");
		  reportStep(suiteTest ,"Close icon bag is on edit item popUp window", "PASS",false);
	  }else {
		  System.out.println("Close icon bag is not on edit item popUp window");
		  reportStep(suiteTest ,"Close icon bag is not on edit item popUp window", "FAIL",false);
	  }
	
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'edit cart page' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@Then("User edit any option from edit pop up")
	public void user_edit_any_option_from_edit_pop_up() {
		try {
		utility.explicitWait("(//div[@class='img_holder'])[1]");
		driver.findElement(By.xpath("(//div[@class='img_holder'])[1]")).click();
		reportStep(suiteTest ,"clicking edit", "PASS",false);
		driver.findElement(By.xpath("(//*[@class='size_option_wrapper'])[1]")).click();
		reportStep(suiteTest ,"updated color", "PASS",false);
		driver.findElement(By.xpath("(//*[@class='size_option_wrapper'])[2]")).click();
		reportStep(suiteTest ,"updated size", "PASS",false);
		driver.findElement(By.xpath("//div[text()='Update Bag']")).click();
		reportStep(suiteTest ,"clicked update button", "PASS",false);
		

	    }
		
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'edit button in pop up' case  "+e.getMessage(), "FAIL",false);
		}

		}


	@Then("User checks display of shopping cart page after editing the product")
	public void user_checks_display_of_shopping_cart_page_after_editing_the_product() {
		try {
		utility.explicitWait("//span[contains(text(),'Color:')]");
		boolean prodColor= driver.findElement(By.xpath("//span[contains(text(),'Color:')]")).isDisplayed();
	    if(prodColor) {
	    	System.out.println("Product color is changed");
	    	reportStep(suiteTest ,"Product color is changed", "PASS",false);
	    }else {
	    	System.out.println("Product color is not changed");
	    	reportStep(suiteTest ,"Product color is not changed", "FAIL",false);
	    }
		driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'shopping cart' case  "+e.getMessage(), "FAIL",false);
		}

		}
	
	@Then("User clicks on edit link to increase or decrease the product quantity")
	public void user_clicks_on_edit_link_to_increase_or_decrease_the_product_quantity() {
		try {
		utility.explicitWait("//span[text()='Edit']"); 
		driver.findElement(By.xpath("//span[text()='Edit']")).click();
		   System.out.println(" increae and decrease sign to change product quantity  is not displayed");
		   reportStep(suiteTest ,"increae and decrease sign to change product quantity  is not displayed", "FAIL",false);
		   driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'edit link' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@Then("User clicks on delete icon button")
	public void user_clicks_on_delete_icon_button() {
		try {
		utility.explicitWait("//span[@class='close_icon_bag']");
	driver.findElement(By.xpath("//span[@class='close_icon_bag']")).click();  
	System.out.println("User clicked on delete icon button in shopping cart");
	reportStep(suiteTest ,"User clicked on delete icon button in shopping cart", "PASS",false);
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'delete icon not clickable' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("product should be removed from shopping cart")
	public void product_should_be_removed_from_shopping_cart() {
		try {
		utility.explicitWait("//div[@class='cart_item_list']");
	    boolean emptybag = driver.findElement(By.xpath("//div[@class='cart_item_list']")).isDisplayed();
		if(emptybag) {
			System.out.println("Your bag is currently empty");
			reportStep(suiteTest ,"Your bag is currently empty", "PASS",false);
		}else {
			System.out.println("Your bag is not empty");
			reportStep(suiteTest ,"Your bag is not empty", "FAIL",false);
		}
		driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to remove product from cart' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@Then("User enters the amount in Amount field and click on Donate button")
	public void User_enters_the_amount_in_Amount_field_and_click_on_Donate_button() {
		try {
		utility.explicitWait("//div[text()='Give Back']");
		System.out.println("Enter amount for donation field is not displayed");
		WebElement element = driver.findElement(By.xpath("//div[text()='Give Back']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		utility.explicitWait("//div[@aria-describedby='Donate ,$1.00']");
		driver.findElement(By.xpath("//div[@aria-describedby='Donate ,$1.00']")).click();
		reportStep(suiteTest ,"donate now is clicked", "PASS",false);
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'donate now option has errors' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@Then("User checks the donation amount added to shopping cart")
	public void User_checks_the_donation_amount_added_to_shopping_cart() {
		try {
	 utility.explicitWait("(//div[@class='inline'])[3]");
		boolean donationAmt = driver.findElement(By.xpath("(//div[@class='inline'])[3]")).isDisplayed();
		if(donationAmt) {
			System.out.println("Donation amount is added in shopping cart");
			reportStep(suiteTest ,"Donation amount is added in shopping cart", "PASS",false);
		}else {
			System.out.println("Donation amount is not added in shopping cart");
			reportStep(suiteTest ,"Donation amount is not added in shopping cart", "FAIL",false);
		}
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'donated amount not displayed' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("User verify that donation amount is added as separate line item")
	public void User_verify_that_donation_amount_is_added_as_separate_line_item() {
		try {
		utility.explicitWait("(//div[@class='inline'])[3]");
		List<WebElement> donationline = driver.findElements(By.xpath("(//div[@class='inline'])[3]"));
		for(WebElement element:donationline) {
			System.out.println(element.getText());
		}
		reportStep(suiteTest ,"donation amount added as a seperate line item", "PASS",false);
		driver.close();
	
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'donation amount not added as a seperate line item' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@Then("User clicks on remove button in donation section")
	public void user_clicks_on_remove_button_in_donation_section() {
		try {
		utility.explicitWait("//div[text()='Remove donation']");
		driver.findElement(By.xpath("//div[text()='Remove donation']")).click();   
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'clicking remove button in donation not working' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("User verify if donation amount is removed")
	public void user_verify_if_donation_amount_is_removed() {
		try {
	utility.explicitWait("//div[@aria-describedby='Donate ,$1.00']"); 
	boolean donationButton = driver.findElement(By.xpath("//div[@aria-describedby='Donate ,$1.00']")).isDisplayed();
	if(donationButton) {
		System.out.println("Remove donation button is removed");
		reportStep(suiteTest ,"Remove donation button is removed", "PASS",false);
	}else {
		System.out.println("Remove donation button is not removed");
		reportStep(suiteTest ,"Remove donation button is not removed", "PASS",false);
	}driver.close();
	
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'donation amount is not removed' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@Then("User selects an item from Saved for later section")
	public void user_selects_an_item_from_Saved_for_later_section() {
		try {
		utility.explicitWait("(//h3[@class='name'])[1]");
		driver.findElement(By.xpath("(//h3[@class='name'])[1]")).isDisplayed();
		reportStep(suiteTest ,"save for later is displayed", "PASS",false);
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'save for later section is not working' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Then("User clicks on Remove button")
	public void user_clicks_on_Remove_button() {
		try {
	utility.explicitWait("//span[@class='close_icon_bag']");
		driver.findElement(By.xpath("//span[@class='close_icon_bag']")).click(); 
		reportStep(suiteTest ,"clicked remove button", "PASS",false);
		System.out.println("User clicked on remove button");
		driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to click remove button' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@Then("User clicks on edit link to increase or decrease the quantity.")
	public void user_clicks_on_edit_link_to_increase_or_decrease_the_quantity() {
		try {
		utility.explicitWait("//span[text()='Edit']");
		   driver.findElement(By.xpath("//span[text()='Edit']")).click();
		 
		   System.out.println("Increase and decrease qty options are not on edit pop up window");
		   reportStep(suiteTest ,"Increase and decrease qty options are not on edit pop up window", "FAIL",false);
		   driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to click edit link' case  "+e.getMessage(), "FAIL",false);
		}

		}
	//

	
	@When("^searched for any product and item selected$")
	public void searched_for_any_product_and_item_selected() {
		try {
		utility.explicitWait("//div[@class='icon_container']");
		driver.findElement(By.xpath("//div[@class='icon_container']")).click();
		reportStep(suiteTest ,"search icon is clicked", "PASS",false);
		Actions ad = new Actions(driver);
		ad.sendKeys(driver.findElement(By.id("search")),("chinos")).build().perform();
		ad.sendKeys(driver.findElement(By.id("search")),(Keys.ENTER)).build().perform();
		reportStep(suiteTest ,"search text is entered", "PASS",false);
		utility.explicitWait("(//img[@role='presentation'])[1]");
		driver.findElement(By.xpath("(//img[@role='presentation'])[1]")).click();
		reportStep(suiteTest ,"clicking the first product", "PASS",false);
		utility.explicitWait("(//div[@class='img_holder'])[1]");
		driver.findElement(By.xpath("(//div[@class='img_holder'])[1]")).click();
		reportStep(suiteTest ,"choosing the color", "PASS",false);
		////*[@class='img_holder']
		utility.explicitWait("(//div[@class='size_option_wrapper'])[1]");
		driver.findElement(By.xpath("(//div[@class='size_option_wrapper'])[1]")).click();
		reportStep(suiteTest ,"choosing the size", "PASS",false);
		utility.explicitWait("//div[text()='Add to Bag']");
		driver.findElement(By.xpath("//div[text()='Add to Bag']")).click();
		reportStep(suiteTest ,"clicking add to bag button", "PASS",false);
		
		
	}
		
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'searching a product' case  "+e.getMessage(), "FAIL",false);
		}

		}

	
	@When("^searched for an product and item selected$")
	public void searched_for_an_product_and_item_selected() {
		try {
	utility.explicitWait("//div[@class='icon_container']");
		driver.findElement(By.xpath("//div[@class='icon_container']")).click();
		reportStep(suiteTest ,"search icon is clicked", "PASS",false);
		Actions ad = new Actions(driver);
		ad.sendKeys(driver.findElement(By.id("search")),("shirt")).build().perform();
		ad.sendKeys(driver.findElement(By.id("search")),(Keys.ENTER)).build().perform();
		reportStep(suiteTest ,"search text is entered", "PASS",false);
utility.explicitWait("(//img[@role='presentation'])[3]");
		driver.findElement(By.xpath("(//img[@role='presentation'])[3]")).click();
		reportStep(suiteTest ,"clicked the product", "PASS",false);
		utility.explicitWait("(//div[@class='img_holder'])[1]");
		driver.findElement(By.xpath("(//div[@class='img_holder'])[1]")).click();
		reportStep(suiteTest ,"choosing the color", "PASS",false);
		utility.explicitWait("//*[@aria-labelledby='product-clearance-colors']/div");
		driver.findElement(By.xpath("//*[@aria-labelledby='product-clearance-colors']/div")).click();
		utility.explicitWait("(//div[@class='size_option_wrapper'])[4]");
		driver.findElement(By.xpath("(//div[@class='size_option_wrapper'])[4]")).click();
		reportStep(suiteTest ,"choosing the size", "PASS",false);
	utility.explicitWait("//div[text()='Add to Bag']");
		driver.findElement(By.xpath("//div[text()='Add to Bag']")).click();
		reportStep(suiteTest ,"add to bag is clicked", "PASS",false);
		
		}
	
	catch (Exception e) {
		reportStep(suiteTest ,"Failed in  'searching a product' case  "+e.getMessage(), "FAIL",false);
	}

	}
	
	@When("^searched for product and item selected$")
public void searched_for_product_and_item_selected() {
		try {
		utility.explicitWait("//div[@class='icon_container']");
		driver.findElement(By.xpath("//div[@class='icon_container']")).click();
		Actions ad = new Actions(driver);
		ad.sendKeys(driver.findElement(By.id("search")),("shirt")).build().perform();
		ad.sendKeys(driver.findElement(By.id("search")),(Keys.ENTER)).build().perform();
		reportStep(suiteTest ,"search text is entered", "PASS",false);
utility.explicitWait("(//img[@role='presentation'])[3]");
		driver.findElement(By.xpath("(//img[@role='presentation'])[3]")).click();
		reportStep(suiteTest ,"clicked the product", "PASS",false);
		utility.explicitWait("(//div[@class='img_holder'])[1]");
		driver.findElement(By.xpath("(//div[@class='img_holder'])[1]")).click();
		reportStep(suiteTest ,"choosing the color", "PASS",false);
		utility.explicitWait("//*[@aria-labelledby='product-clearance-colors']/div");
		driver.findElement(By.xpath("//*[@aria-labelledby='product-clearance-colors']/div")).click();
		utility.explicitWait("(//div[@class='size_option_wrapper'])[4]");
		driver.findElement(By.xpath("(//div[@class='size_option_wrapper'])[4]")).click();
		reportStep(suiteTest ,"choosing the size", "PASS",false);
	utility.explicitWait("//div[text()='Add to Bag']");
		driver.findElement(By.xpath("//div[text()='Add to Bag']")).click();
		reportStep(suiteTest ,"add to bag is clicked", "PASS",false);
		
		utility.explicitWait("//*[text()='CHECKOUT']");
		driver.findElement(By.xpath("//*[text()='CHECKOUT']")).click();
		reportStep(suiteTest ,"checkout is clicked", "PASS",false);
		}
		
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'searching a product' case  "+e.getMessage(), "FAIL",false);
		}

		}
		
	@Then("^ship this item and store pickup should be displayed$")
	public void ship_this_item_and_store_pickup_should_be_displayed() {
		try {
		System.out.println("Ship this item and store pickup feature is not on page.");
		reportStep(suiteTest ,"Ship this item and store pickup feature is not on page.", "FAIL",false);
//		WebUIUtilty.timeOUt(2);
//		//driver.findElement(By.xpath("(//div[@class='icon_container']/following::a)[1]/div")).click();
//		//WebUIUtilty.timeOUt(2);
//		boolean shipthisitem=driver.findElement(By.xpath("//*[text()='Ship this item']")).isDisplayed();
//		if(shipthisitem) {
//			
//			System.out.println("Ship this item is displayed");
//		}
//		else {
//			System.out.println("Ship this item is not displayed");
//		}
//		
//		boolean storepickup=driver.findElement(By.xpath("//*[text()='Store pickup - Free ']")).isDisplayed();
//if(storepickup) {
//			
//			System.out.println("Store pick up is displayed");
//		}
//		else {
//			System.out.println("Store pick up is not displayed");
//		}
driver.close();
		
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'store pick up is not displayed' case  "+e.getMessage(), "FAIL",false);
		}

		}

	
	
//	@Then("^user clicks checkout now button$")
//	public void user_clicks_checkout_now_button() {
////		WebUIUtilty.timeOUt(2);
////		driver.findElement(By.xpath("(//div[@class='icon_container']/following::a)[1]/div")).click();
//		WebUIUtilty.timeOUt(5);
//		driver.findElement(By.xpath("//*[text()='Checkout now']")).click();
//		
//	}
	
	@Then("^the sign in link should be displayed when clicked should navigate to login page$")
	public void the_sign_in_link_should_be_displayed_when_clicked_should_navigate_to_login_page() {
		try {
			utility.explicitWait("(//*[text()='Sign In'])[1]");
		
		driver.findElement(By.xpath("(//*[text()='Sign In'])[1]")).click();
		utility.explicitWait("//input[@id='email']");
		boolean text =driver.findElement(By.xpath("//input[@id='email']")).isDisplayed();
		if(text) {
			System.out.println("User navigated to login page");
			reportStep(suiteTest ,"User navigated to login page", "PASS",false);
		}
		else {
			System.out.println("Unable to navigate to login page");
			reportStep(suiteTest ,"User not navigated to login page", "FAIL",false);
		}
		driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'sign in link is not displayed' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@Then("^the applied invalid promotions should throw an error message$")
	public void the_applied_invalid_promotions_should_throw_an_error_message() {
		try {
			utility.explicitWait("//*[contains(@class,'styles__CartContainer')]");
			driver.findElement(By.xpath("//*[contains(@class,'styles__CartContainer')]")).click();
			//driver.navigate().refresh();
			utility.scrollPageDown(3);
			utility.explicitWait("//input[@placeholder='Enter promo code (optional)']");
	
		Actions ad = new Actions(driver);
		ad.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Enter promo code (optional)']")),("invalid")).build().perform();
		driver.findElement(By.xpath("//input[@placeholder='Enter promo code (optional)']")).sendKeys(Keys.TAB);
		reportStep(suiteTest ,"invalid promo code entered", "PASS",false);
		driver.findElement(By.xpath("//*[text()='Apply']")).click();
		boolean chk=driver.findElement(By.xpath("//*[contains(@class,'styles__StyledError')]")).isDisplayed();
		if(chk) {
			reportStep(suiteTest ,"invalid promo code not accepted", "PASS",false);
		}
		else {
			reportStep(suiteTest ,"invalid promo accepted", "FAIL",false);
		}
		
		
		driver.close();
		
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to apply promo code' case  "+e.getMessage(), "FAIL",false);
		}

		}
				
	@Then("^the applied promotions should be applied$")
	public void the_applied_promotions_should_be_applied() {
		
		Actions ad = new Actions(driver);
		ad.sendKeys(driver.findElement(By.id("promoCode")),"").build().perform();
		driver.findElement(By.xpath("//*[text()='Apply']")).click();
		
		driver.close();
		
		
	}
	
	@Then("^the applied promotions should be applied and can be removed by user$")
	public void the_applied_promotions_should_be_applied_and_can_be_removed_by_user() {
	
		Actions ad = new Actions(driver);
		ad.sendKeys(driver.findElement(By.id("promoCode")),"").build().perform();
		WebUIUtilty.timeOUt(2);
		driver.findElement(By.xpath("//*[text()='Apply']")).click();
		
		driver.close();
		
	}
	
	@When("^the user adds multiple product$")
	public void the_user_adds_multiple_product() {
		try {
			driver.navigate().refresh();
			
			utility.explicitWait("//div[@class='icon_container']");
			driver.findElement(By.xpath("//div[@class='icon_container']")).click();
			Actions ad = new Actions(driver);
			ad.sendKeys(driver.findElement(By.id("search")),("shirt")).build().perform();
			ad.sendKeys(driver.findElement(By.id("search")),(Keys.ENTER)).build().perform();
			reportStep(suiteTest ,"search text is entered", "PASS",false);
	utility.explicitWait("(//img[@role='presentation'])[3]");
			driver.findElement(By.xpath("(//img[@role='presentation'])[3]")).click();
			reportStep(suiteTest ,"clicked the product", "PASS",false);
			utility.explicitWait("(//div[@class='img_holder'])[1]");
			driver.findElement(By.xpath("(//div[@class='img_holder'])[1]")).click();
			reportStep(suiteTest ,"choosing the color", "PASS",false);
			utility.explicitWait("//*[@aria-labelledby='product-clearance-colors']/div");
			driver.findElement(By.xpath("//*[@aria-labelledby='product-clearance-colors']/div")).click();
			utility.explicitWait("(//div[@class='size_option_wrapper'])[4]");
			driver.findElement(By.xpath("(//div[@class='size_option_wrapper'])[4]")).click();
			reportStep(suiteTest ,"choosing the size", "PASS",false);
		utility.explicitWait("//div[text()='Add to Bag']");
			driver.findElement(By.xpath("//div[text()='Add to Bag']")).click();
			reportStep(suiteTest ,"add to bag is clicked", "PASS",false);
			
			utility.explicitWait("//*[text()='CHECKOUT']");
			driver.findElement(By.xpath("//*[text()='CHECKOUT']")).click();
			reportStep(suiteTest ,"checkout is clicked", "PASS",false);

		
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to add product twice' case  "+e.getMessage(), "FAIL",false);
		}

		}
	@When("^the user tries to add the same product$")
	public void the_user_tries_to_add_the_same_product() {
		try {
			utility.explicitWait("//*[@class='left logo']");
			driver.findElement(By.xpath("//*[@class='left logo']")).click();
			utility.explicitWait("//div[@class='icon_container']");
			driver.findElement(By.xpath("//div[@class='icon_container']")).click();
			Actions ad = new Actions(driver);
			ad.sendKeys(driver.findElement(By.id("search")),("shirt")).build().perform();
			ad.sendKeys(driver.findElement(By.id("search")),(Keys.ENTER)).build().perform();
			reportStep(suiteTest ,"search text is entered", "PASS",false);
	utility.explicitWait("(//img[@role='presentation'])[3]");
			driver.findElement(By.xpath("(//img[@role='presentation'])[3]")).click();
			reportStep(suiteTest ,"clicked the product", "PASS",false);
			utility.explicitWait("(//div[@class='img_holder'])[1]");
			driver.findElement(By.xpath("(//div[@class='img_holder'])[1]")).click();
			reportStep(suiteTest ,"choosing the color", "PASS",false);
			utility.explicitWait("//*[@aria-labelledby='product-clearance-colors']/div");
			driver.findElement(By.xpath("//*[@aria-labelledby='product-clearance-colors']/div")).click();
			utility.explicitWait("(//div[@class='size_option_wrapper'])[4]");
			driver.findElement(By.xpath("(//div[@class='size_option_wrapper'])[4]")).click();
			reportStep(suiteTest ,"choosing the size", "PASS",false);
		utility.explicitWait("//div[text()='Add to Bag']");
			driver.findElement(By.xpath("//div[text()='Add to Bag']")).click();
			reportStep(suiteTest ,"add to bag is clicked", "PASS",false);
			
			utility.explicitWait("//*[text()='CHECKOUT']");
			driver.findElement(By.xpath("//*[text()='CHECKOUT']")).click();
			reportStep(suiteTest ,"checkout is clicked", "PASS",false);
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to add same product twice' case  "+e.getMessage(), "FAIL",false);
		}

		}
	
	
	@Then("^the product should get added properly$")
	public void the_product_should_get_added_properly() {
		try {
			utility.explicitWait("//*[contains(@class,'styles__CartContainer')]");
		driver.findElement(By.xpath("//*[contains(@class,'styles__CartContainer')]")).click();
		utility.explicitWait("//*[@class='summary_items']");
		String text=driver.findElement(By.xpath("//*[@class='summary_items']")).getText();
		if(!text.contains("1")) {
			System.out.println("Multiple products added to cart");
			reportStep(suiteTest ,"Multiple products added to cart", "PASS",false);
		}
		else {
			
			System.out.println("Unable to add multiple products");
			reportStep(suiteTest ,"Unable to add multiple product", "FAIL",false);
		}
		driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'unable to add product twice' case  "+e.getMessage(), "FAIL",false);
		}

		}
	
	
	@Then("^the same product must get added$")
	public void the_same_product_must_get_added() {
		try {
		utility.explicitWait("//*[contains(@class,'styles__StyledCartBadge')]");
		driver.findElement(By.xpath("//*[contains(@class,'styles__StyledCartBadge')]")).click();
		reportStep(suiteTest ,"bag is clicked", "PASS",false);
		utility.explicitWait("//*[@class='summary_items']");
		String text=driver.findElement(By.xpath("//*[@class='summary_items']")).getText();
		if(text.contains("2")) {
			System.out.println("Same products are added to cart");
			reportStep(suiteTest ,"same product is added twice", "PASS",false);
		}
		else {
			
			System.out.println("Unable to add products");
			reportStep(suiteTest ,"unable to add the same product twice", "PASS",false);
		}
		driver.close();
	}
		
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'issue in bag summary' case  "+e.getMessage(), "FAIL",false);
		}

		}	
	
	@When("^popup window displayed click oncheckout$")
	public void popup_window_displayed_click_oncheckout() {
		try {
utility.explicitWait("//*[text()='CHECKOUT']");
		driver.findElement(By.xpath("//*[text()='CHECKOUT']")).click();
		reportStep(suiteTest ,"checkout button is clicked", "PASS",false);
	}
	
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'checkout button not clickable' case  "+e.getMessage(), "FAIL",false);
		}

		}	
	
	
	@Then("^verify the order total in cart page$")
	public void verify_the_order_total_in_cart_page() {
		try {
		
	utility.explicitWait("//span[text()='Total']/following-sibling::span");
		boolean total=driver.findElement(By.xpath("//span[text()='Total']/following-sibling::span")).isDisplayed();
		if(total) {
			
			System.out.println("Total amount is displayed");
			reportStep(suiteTest ,"Total amount is displayed", "PASS",false);
		}
		
		else {
			
			System.out.println("Total amount is not displayed");
			reportStep(suiteTest ,"Total amount is not displayed", "FAIL",false);
		}
		
		driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'order total is not displayed' case  "+e.getMessage(), "FAIL",false);
		}

		}

	
	@Then("^scroll the page and check the display of recommendation section$")
	public void scroll_the_page_and_check_the_display_of_recommendation_section() {
		try {
		utility.explicitWait("(//div[@class='icon_container']/following::a)[1]/div");
		driver.findElement(By.xpath("(//div[@class='icon_container']/following::a)[1]/div")).click();
		utility.explicitWait("//div[@class='nav_btn left_btn']");
		boolean recommendations = driver.findElement(By.xpath("//div[@class='nav_btn left_btn']")).isDisplayed();
		
		if(recommendations) {
			System.out.println("Recommendations section is displayed properly");
			reportStep(suiteTest ,"Recommendations section is displayed properly", "PASS",false);
		}
		else {
			System.out.println("Recommendations section is not displayed properly");
			reportStep(suiteTest ,"Recommendations section is not displayed properly", "FAIL",false);
		}
		
		driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'recommendations are not displayed' case  "+e.getMessage(), "FAIL",false);
		}

		}
	
	@Then("^the giftbox should get added properly$")
	public void the_giftbox_should_get_added_properly() {
		try {
		utility.explicitWait("//*[contains(@class,'styles__StyledCartBadge')]");
		driver.findElement(By.xpath("//*[contains(@class,'styles__StyledCartBadge')]")).click();
		utility.explicitWait("//*[text()='Add a gift box']");
		driver.findElement(By.xpath("//*[text()='Add a gift box']")).click();
		reportStep(suiteTest ,"add a gift box is clicked", "PASS",false);
		driver.findElement(By.xpath("//input[@placeholder='Recipient']")).sendKeys("arjun@eb.com");
		driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys("arjun");
		driver.findElement(By.xpath("//input[@placeholder='Message']")).sendKeys("gift!!");
		reportStep(suiteTest ,"recipient details are entered", "PASS",false);
		driver.findElement(By.xpath("//input[@placeholder='Message']")).sendKeys(Keys.TAB);
		Thread.sleep(5000);
		utility.explicitWait("//*[text()='Add Gift Box']");
		driver.findElement(By.xpath("//*[text()='Add Gift Box']")).click();
		reportStep(suiteTest ,"add gift box is clicked", "PASS",false);
		utility.explicitWait("//*[text()='Edit gift box']");
		boolean giftBox=driver.findElement(By.xpath("//*[text()='Edit gift box']")).isDisplayed();
		if(giftBox) {
			
			System.out.println("Gift box got added successfully");
			reportStep(suiteTest ,"Gift box got added successfully", "PASS",false);
		}
		else {
			System.out.println("Unable to add the gift box");
			reportStep(suiteTest ,"Unable to add the gift box", "FAIL",false);
		}
		
		driver.close();
		
		
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  '' case  "+e.getMessage(), "FAIL",false);
		}

		}
	
	@Then("upon adding a giftbox and editing the quantity to two, gift box option should be disabled")
	public void uponadding_a_giftbox_and_editing_the_quantity_to_two_gift_box_option_should_be_disabled()  {
		try {

			utility.explicitWait("//*[contains(@class,'styles__StyledCartBadge')]");
			driver.findElement(By.xpath("//*[contains(@class,'styles__StyledCartBadge')]")).click();
		utility.explicitWait("//*[text()='Add a gift box']");
		driver.findElement(By.xpath("//*[text()='Add a gift box']")).click();
		reportStep(suiteTest ,"add a gift box is clicked", "PASS",false);
		driver.findElement(By.xpath("//input[@placeholder='Recipient']")).sendKeys("arjun@eb.com");
		driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys("arjun");
		driver.findElement(By.xpath("//input[@placeholder='Message']")).sendKeys("gift!!");
		reportStep(suiteTest ,"entered recipient details", "PASS",false);
		driver.findElement(By.xpath("//input[@placeholder='Message']")).sendKeys(Keys.TAB);
		utility.explicitWait("//*[text()='Add Gift Box']");
		driver.findElement(By.xpath("//*[text()='Add Gift Box']")).click();
		reportStep(suiteTest ,"add gift box is clicked", "PASS",false);
		//driver.navigate().refresh();
		WebUIUtilty.scrollPageUP(1);
		utility.explicitWait("//span[text()='Edit gift box']");
	 driver.findElement(By.xpath("//span[text()='Edit gift box']")).click();
	 boolean chk=driver.findElement(By.xpath("//*[@class='qty_field']")).isDisplayed();
		

if(chk)
 {
	
	System.out.println("Gift box count cannot be updated");
	reportStep(suiteTest ,"Gift box count cannot be updated", "PASS",false);
}
else {
	System.out.println("Gift box is  editable");
	reportStep(suiteTest ,"Gift box is  editable", "FAIL",false);
}

driver.close();


	}
	
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'editing the quantity in gift box' case  "+e.getMessage(), "FAIL",false);
		}

		}
	
	
	
@When("^User is in home page as a registered user$")	
public void user_is_in_home_page_as_a_registered_user() {
	
	try {
		prop = new Properties();
		prop.load(new FileInputStream(new File("./GlobalSettings.properties")));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	sUrl = prop.getProperty("url");

	try {
		System.out.println("Browser is :"+browser); 
		driver = WebUIUtilty.launchBrowser(browser);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.get(sUrl);
	//driver.navigate().refresh();
	
	WebUIUtilty.timeOUt(10);
	WebElement policyPopUp =driver.findElement(By.xpath("//div[@class='close_btn_thick']"));
	if(policyPopUp.isDisplayed()) {
		policyPopUp.click();
	}else {
		System.out.println("Privacy policy message is not displayed on Home Page");
	}
	
	reportStep(suiteTest ,"Page loaded successfully", "PASS",false);
	utility.takeSnap();
	try {
	utility.explicitWait("(//input[@id='email'])[1]");
	Actions userId = new Actions(driver);
	userId.sendKeys(driver.findElement(By.xpath("(//input[@id='email'])[1]")),loginMail).build().perform();
	driver.findElement(By.xpath("//div[text()='Continue']")).click();
	utility.explicitWait("//input[@name='password']");
	
	userId.sendKeys(driver.findElement(By.xpath("//input[@name='password']")),"Tester1$").build().perform();
	utility.takeSnap();
     driver.findElement(By.xpath("//div[text()='Sign In']")).click();
     reportStep(suiteTest ,"Logged in successfully", "PASS",false);
			}
catch(Exception e) {
	reportStep(suiteTest ,"Registered user unable to login' case  "+e.getMessage(), "FAIL",false);	
}
}


	

	
	
	@Then("^the product must be added to cart for the registered user$")
	public void the_product_must_be_added_to_cart_for_the_registered_user() {
		try {
		utility.explicitWait("//span[text()='Total']/following-sibling::span");
		//driver.findElement(By.xpath("(//div[@class='icon_container']/following::a)[1]/div")).click();
//		WebUIUtilty.timeOUt(2);
	boolean total=driver.findElement(By.xpath("//span[text()='Total']/following-sibling::span")).isDisplayed();
		if(total) {
			
			System.out.println("product added to bag");
			reportStep(suiteTest ,"product added to bag", "PASS",false);
		}
//		
		else {
			
			System.out.println("Unable to add to bag");
			reportStep(suiteTest ,"product not added to bag", "FAIL",false);
		}
//		
		driver.close();
	}
		catch (Exception e) {
			reportStep(suiteTest ,"Failed in  'product not added to cart' case  "+e.getMessage(), "FAIL",false);
		}

		}

	@Override
	public long takeSnap(){
		
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE) , new File("./Reports/images/"+number+".jpg"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return number;
	}
	
	
	
	
}