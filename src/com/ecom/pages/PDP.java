package com.ecom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.ecom.base.CoreBase;

public class PDP extends  CoreBase {

    WebDriver driver; 
    public String prodID, price,color,size;

    @FindBy(xpath="//*[@class='style_number']")
    WebElement text_productID;
    
    @FindBy(xpath="//*[contains(@class,'old_price')]")
    WebElement text_price;
    
    @FindBy(xpath="//*[@class='reviews']")
    WebElement text_reviews;
    
    @FindBy(xpath="(//span[@class='value'])[1]")
    WebElement text_color;
    
    @FindBy(xpath="//*[@id='product-sizes']")
    WebElement text_size;
    
    public  PDP(WebDriver driver)
    {
    	  this.driver = getDriver();
          //This initElements method will create all WebElements
          PageFactory.initElements(driver, this);
          Assert.assertTrue(super.isPageLoaded());
    }
    
  
    public void verifyProductID() {
    	
    	 prodID= getText(text_productID);
    	 if(prodID.isEmpty())
    	 {
    		 reportVP(FAIL,"Product ID is empty");
    	 }
    	 else reportVP(PASS,"Product ID is not empty");
    	
    }
    
    public void verifyPrice() {
    	
    	price=getText(text_price);
    	
    	 if(price.isEmpty())
    	 {
    		 reportVP(FAIL,"Price is empty");
    	 }
    	 else reportVP(PASS,"Price is not empty");
    	
    }
    
    public void priceValdations()
    {
    	if(currentURL.contains("com"))
    		if(price.contains("$"))
    		{
    			reportVP(PASS,"Amount is in USD");
    		}
    		else reportVP(FAIL,"Incorrect price is displayed");

    		else if (currentURL.contains("ca")) {

    		if(price.contains("CAD"))
    		{
    			reportVP(PASS,"Amount is in CAD");
    		}
    		else reportVP(FAIL,"Incorrect price is displayed");

    		}
    }
    
    public void verifyReviews() {
    	
    	boolean flag=isDisplayed(text_reviews);
    	if(flag)
    	{
    		reportVP(PASS,"Reviews are present");
    	}
    	
    	else reportVP(FAIL,"Reviews section is not there");
    	
    	
    }
    
    public void verifyColor() {
    	
    	color=getText(text_color);
    	if(color.isEmpty())
    	{
    		reportVP(FAIL,"Color is empty");
    	}
    	reportVP(PASS,"Color is present");
    	
    }
    
    public void verifySize() {
    	
    	size=getText(text_size);
    	
    	if(size.isEmpty())
    	{
    		reportVP(FAIL,"Size is not displayed");
    	}
    	else reportVP(PASS,"Size is present");
    }

}
