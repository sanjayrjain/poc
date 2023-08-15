package com.ecom.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecom.base.CoreBase;
import com.ecom.base.ScriptHelper;
import com.ecom.base.SuiteListener;
import com.ecom.pages.Category;
import com.ecom.pages.HomePage;

import static com.ecom.util.commonfunctions.verifyCurrentURLRequest;

@Listeners({SuiteListener.class})
public class CategoryCases extends CoreBase{
	 static WebDriver driver;

public CategoryCases() {
    super(getDriver());
	
	
}
	
@Test
public void Categorycheck() {
	try {
	
	 driver = ScriptHelper.getDriver();
     HomePage landingPage = new HomePage(driver);
     landingPage.navigatetomensubcategory();
     reportVP(INFO,"Subcategory is clicked");
     Category categoryPage=new Category(driver);
     categoryPage.clickRefineby();
     reportVP(INFO,"Click refine by option");
     verifyCurrentURLRequest("insulated","category");
     categoryPage.sortBy();
     reportVP(INFO,"Sort by option is clicked");
     verifyCurrentURLRequest("sortby","Category");
     categoryPage.lefthandNavigation();
     reportVP(INFO,"Left hand navigation successful");
    String gender= categoryPage.getgenderName();
     verifyCurrentURLRequest(gender,"category");
     
	}
	catch(Exception e)
	{
		 e.printStackTrace();
         reportVP(FAIL, "Exception occured. " + e.getStackTrace());
	}
     
     

	
	
	
	
	
}
}