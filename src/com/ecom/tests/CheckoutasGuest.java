package com.ecom.tests;

import static com.ecom.util.commonfunctions.verifyPageContentForText;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecom.base.CoreBase;
import com.ecom.base.ScriptHelper;
import com.ecom.base.SuiteListener;
import com.ecom.pages.Cart;
import com.ecom.pages.Checkout;
import com.ecom.pages.HomePage;
import com.ecom.pages.Search;


@Listeners({SuiteListener.class})
public class CheckoutasGuest extends CoreBase {
    private static WebDriver driver;
    public String keyWord = "chinos";

    public CheckoutasGuest() {
        super(getDriver());
    }


    @Test
    public void Checkflow_guest() {
    	
    	try {
    	
    	 driver = ScriptHelper.getDriver();
         HomePage landingPage = new HomePage(driver);
         landingPage.clicksearch();
         reportVP("INFO", "Search icon is clicked");
     	Search searchPage=new Search(driver);
     	searchPage.enterSearchtext(keyWord);
     	reportVP(INFO,"Search text is entered");
     	Cart cartPage=new Cart(driver);
     	cartPage.clickfirstProduct();
     	reportVP(INFO,"Clicking the first product");
     	verifyPageContentForText(keyWord,"Cart");
     	cartPage.clickSize();
     	reportVP("INFO","Size is chosen");
     	cartPage.productIDfromPDP();
     	cartPage.clickaddtoCart();
     	reportVP(INFO,"Add to cart button is clicked");
      	cartPage.clickCheckout();
    	reportVP(INFO,"Checkout button is clicked");
    	Checkout checkoutPage=new Checkout(driver);
    	checkoutPage.productIDCheckoutPage();
    	checkoutPage.productIDcomparison();
    	reportVP(INFO,"Product ID comparison");
    	checkoutPage.getPrice();
    	checkoutPage.pricecheckincheckOut();
    	reportVP(INFO,"Checking whether the price is in USD or CAD");
    	checkoutPage.proccedtocheckoutClick();
    	reportVP(INFO,"Proceed to checkout button is clicked");
    	checkoutPage.clickGuestcheckout();
    	reportVP(INFO,"Clicking the guest checkout button");
    	verifyPageContentForText("SHIPPING","Order placing - Guest flow");
    	checkoutPage.enterAddressforGuestCheckout();
    	reportVP(INFO,"Address is entered");
    	checkoutPage.enterEmailandPhone();
    	reportVP(INFO,"Email and phone number are entered");
    	checkoutPage.clickguestorderButton();
    	reportVP(INFO,"Place order button is clicked");
    	verifyPageContentForText("PAYMENT","Order placing");
    	
    	
    	}
    	
    	catch(Exception e) {
    		e.printStackTrace();
    		reportVP(FAIL, "Exception occured. " + e.getStackTrace());
    	}
    }
}