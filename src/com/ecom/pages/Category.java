package com.ecom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.ecom.base.CoreBase;

public class Category extends CoreBase {

    WebDriver driver; 
    
    
    
    @FindBy(xpath="//*[text()='Insulated']")
    WebElement button_insulated; 
    
    @FindBy(xpath="//*[text()='Refine']")
    WebElement button_refine;
    
    @FindBy(xpath="//*[text()='Best Match']")
    WebElement button_match;
    
    @FindBy(xpath="//*[text()='Price Low to High']")
    WebElement button_price;
    
    @FindBy(xpath="//*[text()='Men' and @role='link']")
    WebElement button_navigation;
    
    @FindBy(xpath="//*[@class='group_label']")
	
    WebElement text_genderName;
    
    public  Category(WebDriver driver)
    {
    	  this.driver = getDriver();
          //This initElements method will create all WebElements
          PageFactory.initElements(driver, this);
          Assert.assertTrue(super.isPageLoaded());
    }
    
 public void clickRefineby() {
    	
    	clickAnElement(button_insulated);
    	clickAnElement(button_refine);
    }
 
 public void sortBy() {
	 
	 clickAnElement(button_match);
	 clickAnElement(button_price);
	 
 }
 
 public void lefthandNavigation() {
	 clickAnElement(button_navigation);
 }
 
 public String getgenderName() {
	 
	 String name= getText(text_genderName).toLowerCase();
	 System.out.println(name);
	 return name;
 }
 
}
