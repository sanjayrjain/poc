package com.ecom.tests;
import com.ecom.pages.HomePage;
import com.ecom.pages.Search;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.ecom.base.CoreBase;
import com.ecom.base.ScriptHelper;
import com.ecom.base.SuiteListener;
import static com.ecom.util.commonfunctions.verifyPageContentForText;

@Listeners({SuiteListener.class})

public class SearchNew extends CoreBase {

    static WebDriver driver;
    
 
    
    public SearchNew() {
        super(getDriver());
    	
    	
    }
    
    @Test
    public void Search() {
    	try {
    		 driver = ScriptHelper.getDriver();
    		Search searchPage=new Search(driver);
    		searchPage.clickMen();
    		searchPage.clickfirstProduct();
    		String title=searchPage.getTitle();
    		System.out.println("Product title is"+ title);
    	HomePage landingPage= new HomePage(driver);
    	landingPage.clicksearch();
    	reportVP(INFO,"Search icon is clicked");
    	
    	searchPage.enterSearchtext(title);
    	reportVP(INFO,"Search text is entered");
    	verifyPageContentForText(title,"Search");
    	searchPage.clickMen();
    		searchPage.clickfirstProduct();
    	String productID=searchPage.getproductID();
    	System.out.println("Product ID is"+ productID);
    	landingPage.clicksearch();
    	reportVP(INFO,"Search icon is clicked");
    	
    	searchPage.enterSearchtext(productID);
    	reportVP(INFO,"Search text is entered");
    	verifyPageContentForText(productID,"Search");
    	 landingPage.clicksearch();
    	searchPage.enterSearchtext("Invalid keyword");
    	reportVP(INFO,"Search text is entered");
    
    	
        } catch (Exception e) {
            e.printStackTrace();
            reportVP(FAIL, "Exception occured. " + e.getStackTrace());
        }

    }
    }

