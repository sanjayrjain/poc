package com.ecom.tests;

import static com.ecom.util.commonfunctions.verifyPageContentForText;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecom.base.CoreBase;
import com.ecom.base.ScriptHelper;
import com.ecom.base.SuiteListener;
import com.ecom.pages.Address;
import com.ecom.pages.Cart;
import com.ecom.pages.HomePage;
import com.ecom.pages.Search;
import com.ecom.pages.WishList;
import com.ecom.util.LoadConfigFile;

@Listeners({SuiteListener.class})
public class WishListCases extends CoreBase{
	
	static WebDriver driver;
	public String keyWord="chinos";
	public WishListCases() {
		super(getDriver());
		//This initElements method will create all WebElements
	}

	 @Test
	 public void wishList()
	 {
		 
		 driver = ScriptHelper.getDriver();
         HomePage landingPage = new HomePage(driver);
         landingPage.clickLoginbutton();
         reportVP("INFO", "Login icon is clicked");
         Address addressPage = new Address(driver);
         addressPage.Login(LoadConfigFile.getValue("login_username"),LoadConfigFile.getValue("login_password"));
         reportVP(INFO,"Logged in to user profile");
         landingPage.clicklogo();
		 reportVP(INFO,"Eddie bauer logo is clicked");
		 landingPage.clicksearch();
		 reportVP(INFO,"Search button is clicked");
     	Search searchPage=new Search(driver);
     	searchPage.enterSearchtext(keyWord);
     	reportVP(INFO,"Search text is entered");
     	Cart cartPage=new Cart(driver);
     	cartPage.clickfirstProduct();
     	reportVP(INFO,"Clicking the first product");
     	verifyPageContentForText(keyWord,"Cart");
     	cartPage.clickSize();
     	reportVP("INFO","Size is chosen");
     	cartPage.clickaddtoCart();
     	reportVP(INFO,"Add to cart button is clicked");
     	cartPage.clickCheckout();
    	reportVP(INFO,"Checkout button is clicked");
    	WishList wishlistPage=new WishList(driver);
    	wishlistPage.clickmovetowishList();
    	reportVP(INFO, "Clicking move to wish list link");
    	wishlistPage.editWishList();
    	reportVP(INFO,"Editing the wishlist");
    	wishlistPage.deleteWishList();
    	reportVP(INFO,"Deleting the wishlist");
    	landingPage.clicklogo();
    	reportVP(INFO,"Clicking the eddie bauer logo");
    	 landingPage.clickLoginbutton();
         reportVP("INFO", "Login icon is clicked");
         wishlistPage.confirmdelete();
         reportVP(INFO,"wishlist deleted successfully");
         
    	
		 
	 }

}
