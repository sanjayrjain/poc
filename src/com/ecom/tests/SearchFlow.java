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

public class SearchFlow extends CoreBase {

	static WebDriver driver;

	String productID = "8615";
	String productName = "Eddie Bauer Break Point Flip Flop";
	String brandName = "Katabatic";
	String productType = "men shirt";

	public SearchFlow() {
		super(getDriver());

	}

	@Test
	public void Search() {
		try {
			driver = ScriptHelper.getDriver();
			HomePage landingPage = new HomePage(driver);
			landingPage.clicksearch();
			reportVP(INFO, "Search icon is clicked");
			Search searchPage = new Search(driver);
			searchPage.enterSearchtext(productID);
			reportVP(INFO, "Search text is entered");
			verifyPageContentForText(productID, "Search");

			landingPage.clicksearch();
			reportVP(INFO, "Search icon is clicked");
			searchPage.enterSearchtext(productName);
			reportVP(INFO, "Search text is entered");
			verifyPageContentForText(productName, "Search");

			landingPage.clicksearch();
			reportVP(INFO, "Search icon is clicked");
			searchPage.enterSearchtext(brandName);
			reportVP(INFO, "Search text is entered");
			verifyPageContentForText(brandName, "Search");

			landingPage.clicksearch();
			reportVP(INFO, "Search icon is clicked");
			searchPage.enterSearchtext(productType);
			reportVP(INFO, "Search text is entered");
			verifyPageContentForText(productType, "Search");

		} catch (Exception e) {
			e.printStackTrace();
			reportVP(FAIL, "Exception occured. " + e.getStackTrace());
		}

	}
}
