package com.ecom.pages;

import static com.ecom.util.commonfunctions.generateRandomAddress;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.ecom.util.LoadConfigFile;

public class Checkout extends AbstractPage {
	public String prodID,price,finalprice;
    WebDriver driver;
    
    @FindBy(className="checkout-textstyle-6")
    WebElement text_productIDincheckout;
    
    @FindBy(xpath="//span[contains(@class,'bag-order-amount right')]")
    WebElement text_priceincheckout;
    
    @FindBy(xpath="//*[@alt='Proceed to Checkout']")
    WebElement button_proceedtocheckout;
    
    @FindBy(xpath="//*[@id='loginEmail']")
    WebElement text_email;
    
    @FindBy(xpath="//*[@id='loginPwd']")
    WebElement text_password;
    
    @FindBy(xpath="//*[contains(@id,'loginFormSubmit')]")
    WebElement button_login;
    
    @FindBy(xpath="//*[contains(@class,'subtotal')]/p[2]")
    WebElement text_priceinplaceorder;
    
    @FindBy(xpath="//*[@id='btnContinueCheckout']")
    WebElement button_checkout;
    
    @FindBy(xpath="//*[@alt='GUEST CHECKOUT']")
    WebElement button_guest;
    
    @FindBy(xpath="//*[@id='addNewFName']")
    WebElement text_fname;
    
    @FindBy(xpath="//*[@id='addNewLName']")
    WebElement text_lname;
    
    @FindBy(xpath="//*[@id='addNewStreetAddr']")
    WebElement text_address1;
    
    @FindBy(xpath="//*[@id='addNewZip']")
    WebElement text_zip;
    
    @FindBy(xpath="//*[@id='addNewCity']")
    WebElement text_city;

    @FindBy(xpath="//*[@id='addNewState']")
    WebElement text_state;
    
    @FindBy(xpath="//*[@id='addNewCountry']")
    WebElement text_country;
    
    @FindBy(xpath="//*[@id='addEmail']")
    WebElement text_emailinguestcheckout;
    
    @FindBy(xpath="//*[@id='editAddressDaytime']")
    WebElement text_phoneinguestcheckout;
    
    @FindBy(xpath="//*[@id='checkoutEditAddressFormSubmit']")
    WebElement button_checkoutasguest;
    
    
    
    public Checkout(WebDriver driver) {
        this.driver = getDriver();
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
        Assert.assertTrue(super.isPageLoaded());
   }
    
    public String productIDCheckoutPage()
    {
    	prodID=getText(text_productIDincheckout);
    	
    	return prodID;
    	
    }

    public void productIDcomparison() {
    	
    	
    	if(Cart.productID.contains(prodID))
    	{
    		System.out.println("Product ID matches");
    	}
    	else System.out.println("Product ID mismatch");
    }
    
    public String getPrice() {
    	
    	 price=getText(text_priceincheckout);
    	System.out.println(price);
    	return price;
    	
    }
    
    public void pricecheckincheckOut()
    {
    	System.out.println(price);
    	if(getcurrentURL().contains("com"))
    		if(price.contains("$"))
    		{
    			reportVP(PASS,"Amount is in USD");
    		}
    		else reportVP(FAIL,"Incorrect price is displayed");

    		else if (getcurrentURL().contains("ca")) {

    		if(price.contains("CAD"))
    		{
    			reportVP(PASS,"Amount is in CAD");
    		}
    		else reportVP(FAIL,"Incorrect price is displayed");

    		}

    }
    
    public void proccedtocheckoutClick() {
    	clickAnElement(button_proceedtocheckout);
    }

    public void Loginincheckout(String email, String password) {
      

    if(email == null ||
        email.toLowerCase().contains("")){
         email = LoadConfigFile.getValue("login_username");
           password = LoadConfigFile.getValue("login_password");
       }

        enterText(text_email,email);
        enterText(text_password,password);
        clickAnElement(button_login);
    }
    
 public String getPriceinorderPage() {
    	
	 	explicitWaitVisibilityOfElement(text_priceinplaceorder, 3000);
    	 finalprice=getText(text_priceinplaceorder);
    	 System.out.println(finalprice);
    	 return finalprice;
    	
    }
 
 public void pricecheckinOrderpage()
 {

	 System.out.println(finalprice);
 	if(getcurrentURL().contains("www.ecom.com"))
 		if(finalprice.contains("$"))
 		{
 			reportVP(PASS,"Amount is in USD");
 		}
 		else reportVP(FAIL,"Incorrect price is displayed");

 		else if (getcurrentURL().contains("www.ecom.ca")) {

 		if(finalprice.contains(" CAD"))
 		{
 			reportVP(PASS,"Amount is in CAD");
 		}
 		else reportVP(FAIL,"Incorrect price is displayed");

 		}

 }
 
 public void clickCheckout() {
	 
	 clickAnElement(button_checkout);
 }
 
 public void clickGuestcheckout() {
	 clickAnElement(button_guest);
 }
   public void enterAddress() {
	   
	   enterText(text_fname, "arjun");
	   enterText(text_lname, "kumar");
	   
   }
   
   public void triggerAddressforguestCheckout(String country) {
	   
	 	HashMap<String,String> address = new HashMap<>(5);

	      if(country.equalsIgnoreCase("CA")){
	   	  address=  generateRandomAddress("CA");
	      }else{
	          address = generateRandomAddress("US");
	      }
	      
	      enterText(text_fname,address.get("FirstName"));
	      enterText(text_lname,address.get("LastName"));
	      enterText(text_address1,address.get("Street"));
	      enterText(text_zip,address.get("zipcode"));
	      enterText(text_city,address.get("city")); 
	      selectFromDropDown(text_state,address.get("state"));

	}
   
   public void enterAddressforGuestCheckout() {
	   
	   if(currentURL.contains("com"))
	   {
		   triggerAddressforguestCheckout("US");
	   }
	   
	   else {
		   triggerAddressforguestCheckout("CA");
	   }
		   
		   reportVP(INFO,"Address entered successfully");
		   
		   if(currentURL.contains("requestid"))
		   {
			   reportVP(PASS,"Address got added for guest checkout");
			   
		   }
		   
		   else
			   reportVP(FAIL,"Unable to add the address");
		   
   }
   
   
   
   public void enterEmailandPhone() {
	   
	   enterText(text_emailinguestcheckout, "u.arjun@gmail.com");
	   enterText(text_phoneinguestcheckout, "7845269020");
   }
   
   
   public void clickguestorderButton() {
	   
	   clickAnElement(button_checkoutasguest);
   }
}










