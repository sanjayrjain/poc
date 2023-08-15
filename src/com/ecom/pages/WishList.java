package com.ecom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.ecom.base.CoreBase;

public class WishList extends CoreBase {

    WebDriver driver;  
    
    @FindBy(xpath="//*[@class='move-item-to-wishlist ']")
    WebElement link_movetoWishlist;
    
    @FindBy(xpath="//*[contains(text(),'WISH LIST')]")
    WebElement confirm_wishList;
    
    @FindBy(xpath="//*[@class='wl-item-quantity']")
    WebElement dropdown_editwishlist;
    
    @FindBy(xpath="//*[contains(@data-formid,'edit-quantity')]")
    WebElement text_confirmeditwishlist;
    
    @FindBy(xpath="(//div[@id='sbWishlistHolder']//a[contains(@class,'remove')])[1]")
    WebElement link_deletewishlist;
    
    @FindBy(xpath="//*[@src='/static/img/btn-ok.png']")
    WebElement button_confirmdelete;
    
    @FindBy(xpath="//*[@aria-label='Select this link to visit your Wish List']")
    WebElement link_wishlistinmyaccounts;
    
    @FindBy(xpath="//*[@id='wishListCountHolder']")
    WebElement text_emptywishList;
    
    public  WishList(WebDriver driver)
    {
    	  this.driver = getDriver();
          //This initElements method will create all WebElements
          PageFactory.initElements(driver, this);
    }
    
    public void clickmovetowishList()
    {
    	clickAnElement(link_movetoWishlist);
    	
    	if(isDisplayed(confirm_wishList))
    	{
    		reportVP(PASS,"Item moved to wish list successfully");
    		
    	}
    	else reportVP(FAIL,"Unable to move the item to wishList");
    }
    
    public void editWishList() {
    	explicitWaitVisibilityOfElement(dropdown_editwishlist, 30000);
    	selectFromDropDown(dropdown_editwishlist,"2");
    	
    String text=getText(text_confirmeditwishlist);
    	{
    		if(text.contains("2"))
    		{
    			reportVP(PASS,"Edited successfully");
    		}
    		
    		else reportVP("FAIL","Unable to edit");
    	}
    	
    }
    public void deleteWishList() {
        explicitWaitVisibilityOfElement(link_deletewishlist, 30);
        clickAnElement(link_deletewishlist);
        explicitWaitVisibilityOfElement(button_confirmdelete,20);
        clickAnElement(button_confirmdelete);
       }
    
    public void confirmdelete() {
    	
    	clickAnElement(link_wishlistinmyaccounts);
    	if(isDisplayed(text_emptywishList))
    	{
    		reportVP(PASS,"Wish list got deleted");
    	}
    	
    	else reportVP(FAIL,"Unable to delete the wishlist");
    }
   

}
