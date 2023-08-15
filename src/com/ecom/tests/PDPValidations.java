package com.ecom.tests;

import static com.ecom.util.commonfunctions.verifyPageContentForText;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecom.base.CoreBase;
import com.ecom.base.ScriptHelper;
import com.ecom.base.SuiteListener;
import com.ecom.pages.Cart;
import com.ecom.pages.HomePage;
import com.ecom.pages.PDP;
import com.ecom.pages.Search;


@Listeners({SuiteListener.class})
public class PDPValidations extends CoreBase {
	
	  private static WebDriver driver;
	  public String keyWord = "chinos";
	  
	  public PDPValidations() {
		  
		  super(getDriver());
	  }
	  
	  
	  @Test
	    public void validation_PDP() {
	    	
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
	     	verifyPageContentForText("chinos","PDP validations");
	     	PDP pdpPage = new PDP(driver);
	     	pdpPage.verifyProductID();
	     	reportVP(INFO,"Validation for product ID");
	     	pdpPage.verifyPrice();
	     	reportVP(INFO,"Validation for price");
	     	pdpPage.priceValdations();
	     	reportVP(INFO,"Validating the price as per the country");
	     	pdpPage.verifyReviews();
	     	reportVP(INFO,"Valdation for reviews");
	     	pdpPage.verifyColor();
	     	reportVP(INFO,"validation for price");
	     	pdpPage.verifySize();
	     	reportVP(INFO,"Validation for size");
	     	verifyPageContentForText("Add to Bag","PDP Validations");
	     	verifyPageContentForText("Product Description","PDP validations");
	     	verifyPageContentForText("Features","PDP validations");

	     	
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    		reportVP(FAIL, "Exception occured. " + e.getStackTrace());
	    	}


}
}