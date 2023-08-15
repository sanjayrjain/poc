package com.ecom.tests;

import static com.ecom.base.BaseFactory.catHash;

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
public class CheckoutwithLogin extends CoreBase {
    private static WebDriver driver;
    public String keyWord = "chinos";

    public CheckoutwithLogin() {
        super(getDriver());
    }


    @Test
    public void Checkflow_login() {
    	
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
       checkoutPage.Loginincheckout(catHash.get("new_username"),catHash.get("new_password"));
       reportVP(INFO,"Logging to the user profile");
       checkoutPage.getPriceinorderPage();
       checkoutPage.pricecheckinOrderpage();
       reportVP(INFO,"Price check in order page");
       verifyPageContentForText("SHIPPING ADDRESS","Order placing");
       verifyPageContentForText("BILLING ADDRESS","Order placing");
       verifyPageContentForText("CONTACT INFORMATION","Order placing");
       verifyPageContentForText("SHIPPING METHOD","Order placing");
       checkoutPage.clickCheckout();
       reportVP(INFO,"Checkout button is clicked");
       verifyPageContentForText("PAYMENT","Order placing");
       checkoutPage.clickonbag();
       cartPage.deleteCartItems();
    	}
    	
    	catch(Exception e) {
    		e.printStackTrace();
    		reportVP(FAIL, "Exception occured. " + e.getStackTrace());
    	}
    	
    	

}
}