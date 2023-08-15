package com.ecom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ecom.base.CoreBase;
import com.ecom.base.ScriptHelper;

public class Search extends CoreBase {

    WebDriver driver; 
    

    
    @FindBy(id="search")
    WebElement text_searchText;
    
    @FindBy(xpath="//*[@id='Men-menu']")
    WebElement link_men;
    
    @FindBy(xpath="//*[contains(@id,'tile_')]")
    WebElement link_firstProduct;
    
    @FindBy(xpath="//*[@class='title']")
    WebElement text_title;
    
    @FindBy(xpath="//*[@class='style_number']/span")
    WebElement text_number;
    
    public  Search(WebDriver driver)
    {
    	  this.driver = getDriver();
          //This initElements method will create all WebElements
          PageFactory.initElements(driver, this);
         // Assert.assertTrue(super.isPageLoaded());
    }
    
   
    
    public void enterSearchtext(String text)
    {
    	 ScriptHelper.getDriver().findElement(By.xpath("//*[@id='search']")).sendKeys(new CharSequence[]{text, Keys.ENTER});
    }
    
    public void clickMen() {
    	
    	clickAnElement(link_men);
    }
    
    public void clickfirstProduct() {
    	
    	clickAnElement(link_firstProduct);
    }
    
    public String getTitle() {
    	
    	String title=text_title.getText();
		
    	return title;
    }

    public String getproductID() {
    	
    	String id=text_number.getText();
    	String productID=id.substring(8, 12);
    	System.out.println("Product ID "+ productID);
    	return productID;
    }
    
    
    
    
}
