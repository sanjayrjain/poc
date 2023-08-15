package com.ecom.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecom.base.CoreBase;
import com.ecom.base.ScriptHelper;
import com.ecom.base.SuiteListener;
import com.ecom.pages.Address;
import com.ecom.pages.HomePage;
import com.ecom.util.LoadConfigFile;

@Listeners({SuiteListener.class})
public class AddressCases  extends CoreBase {
	
	static WebDriver driver;
	
	public AddressCases() {
		super(getDriver());
		//This initElements method will create all WebElements
	}

	 @Test
	    public void AddressCases() {
		 try {
		 
		 driver = ScriptHelper.getDriver();
         HomePage landingPage = new HomePage(driver);
         landingPage.clickLoginbutton();
         reportVP("INFO", "Login icon is clicked");
         Address addressPage = new Address(driver);
         addressPage.Login(LoadConfigFile.getValue("login_username"),LoadConfigFile.getValue("login_password"));
         reportVP(INFO,"Logged in to user profile");
         addressPage.addnewAddress();
         reportVP(INFO,"Adding a new address");
         addressPage.editAddress("address test");
         reportVP(INFO, "Editing the address");
         addressPage.deleteAddress();
         reportVP(INFO,"Deleting an address");
		 }
		 catch(Exception e) {
			 
			 e.printStackTrace();
	    		reportVP(FAIL, "Exception occured. " + e.getStackTrace());
		 }
		 
	 }
	 }

