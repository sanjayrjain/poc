package com.ecom.tests;

import static com.ecom.util.commonfunctions.verifyPageContentForText;
import static com.ecom.util.commonfunctions.verifyCurrentURLRequest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecom.base.CoreBase;
import com.ecom.base.ScriptHelper;
import com.ecom.base.SuiteListener;
import com.ecom.pages.Cart;
import com.ecom.pages.HomePage;
import com.ecom.pages.Search;


@Listeners({ SuiteListener.class })
public class AddtoCartScenario extends CoreBase {

	static WebDriver driver;
	public String keyWord = "chinos";

	public AddtoCartScenario() {
		super(getDriver());

	}

	@Test
	public void AddtoCart() {
		try {
			driver = ScriptHelper.getDriver();
			HomePage landingPage = new HomePage(driver);
			landingPage.clicksearch();
			reportVP("INFO", "Search icon is clicked");
			Search searchPage = new Search(driver);
			searchPage.enterSearchtext(keyWord);
			reportVP(INFO, "Search text is entered");
			Cart cartPage = new Cart(driver);
			cartPage.clickfirstProduct();
			reportVP(INFO, "Clicking the first product");
			cartPage.sizefromPLP();
			cartPage.colorfromplp();
			verifyPageContentForText(keyWord, "Cart");
			cartPage.clickSize();
			reportVP("INFO", "Size is chosen");
			cartPage.clickaddtoCart();
			reportVP(INFO, "Add to cart button is clicked");
			verifyPageContentForText(keyWord, "Cart");
			// Mini cart validations
			cartPage.sizefromminiCart();
			cartPage.colorfromminiCart();

			cartPage.sizeComparison();
			reportVP(INFO, "Size comparison is done");
			cartPage.colorComparison();
			reportVP(INFO, "Color comparison is done");
			cartPage.inStockCheck();
			reportVP(INFO, "Checking the stock of the product");
			cartPage.quantityCheck();
			reportVP(INFO, "Quantity check");

			// Clicking checkout button
			cartPage.clickCheckout();
			reportVP(INFO, "Checkout button is clicked");
			verifyCurrentURLRequest("bag", "shopping bag");

		} catch (Exception e) {
			e.printStackTrace();
			reportVP(FAIL, "Exception occured. " + e.getStackTrace());
		}

	}
}
    