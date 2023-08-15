package com.ecom.pages;

import static com.ecom.util.commonfunctions.generateRandomAddress;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.ecom.base.CoreBase;

public class Address extends CoreBase {

    WebDriver driver; 
    

    @FindBy(id = "loginEmail")
    private WebElement text_loginEmail;

    @FindBy(id = "loginPwd")
    private WebElement text_loginPassword;

    @FindBy(id = "signinFormSubmit")
    private WebElement button_signSubmit;
    
//    @FindBy(xpath = "//a[contains(@href,'add-address')]")
//    private WebElement text_addressBook;
    
    @FindBy(xpath = "//*[contains(text(),'Address Book')]")
    private WebElement text_addressBook;
    
//    @FindBy(className = "add-address ir")
//    private WebElement button_add_addressir;
    
    @FindBy(xpath = "//a[contains(@href,'add-address')]")
    private WebElement button_add_addressir;

    @FindBy(xpath = "//*[@id='addNewStreetAddr']")
    private WebElement text_addNewAddressStreet;

    @FindBy(xpath = "//*[@id='addNewCity']")
    private WebElement text_addNewCity;

    @FindBy(xpath = "//*[@id='addNewState']")
    private WebElement dd_addNewState;
    
    @FindBy(xpath = "//*[@id='addNewCountry']")
    private WebElement dd_addNewCountry;

    @FindBy(xpath = "//*[@id='addNewZip']")
    private WebElement text_addNewZip;
    

    @FindBy(xpath = "//*[@id='editAddressDaytime']")
    private WebElement text_phoneNumber;
    
    @FindBy(xpath = "//*[@value='Add Address']")
    private WebElement button_saveAddress;
    
    @FindBy(xpath="//*[contains(@aria-label,'Select this link to Edit')]")
    WebElement link_editAddress;
    
    @FindBy(xpath="//*[@id='addNewStreetAddr2']")
    WebElement text_address2;
    
    @FindBy(xpath="//*[contains(@aria-label,'Select this link to Delete')]")
    WebElement button_delete;
    
    @FindBy(xpath="//*[@src='/static/img/btn-ok.png']")
    WebElement button_confirmDelete;
    
    public  Address(WebDriver driver)
    {
    	  this.driver = getDriver();
          //This initElements method will create all WebElements
          PageFactory.initElements(driver, this);
          Assert.assertTrue(super.isPageLoaded());
    }
    
    
    public void enterAddress(String country) {
        HashMap<String,String> address = new HashMap<>();

        clickAnElement(text_addressBook);
        clickAnElement(button_add_addressir);
                
        if(country.equalsIgnoreCase("CA")){
            address=  generateRandomAddress("CA");
            selectFromDropDown(dd_addNewState,"ALBERTA");
            selectFromDropDown(dd_addNewCountry,"Canada");  
            enterText(text_addNewZip,"T4U 4P3 ");
        }else{
            address = generateRandomAddress("US");
            selectFromDropDown(dd_addNewState,"CALIFORNIA");
            selectFromDropDown(dd_addNewCountry,"United States");  
            enterText(text_addNewZip,address.get("zipcode"));
        }

             
        enterText(text_addNewAddressStreet,address.get("Street"));
        enterText(text_addNewCity,address.get("city"));
            
        enterText(text_phoneNumber,"7845269020");
        clickAnElement(button_saveAddress);


    }
    
    public void Login(String email, String password) {
        

 //  if(email == null ||
       // email.toLowerCase().contains("")){
       //  email = LoadConfigFile.getValue("login_username");
         //  password = LoadConfigFile.getValue("login_password");
           
       //}

        enterText(text_loginEmail,email);
        enterText(text_loginPassword,password);
        clickAnElement(button_signSubmit);
    

    String errorMessage = "";
    

    try{
		errorMessage = driver.findElement(By.xpath("//*[@class='response-text']")).getText();
	}catch (Exception e){

	}

    if(errorMessage.toLowerCase().contains("sorry, we don't recognize that email address")){
		reportVP("FAIL", "Login failed, Not able to login using these username and failure");

    }
    }
    
    public void addnewAddress() {
			if (getcurrentURL().contains(".com")) {
				enterAddress("US");
			 }
			 else
			 {
				enterAddress("CA");
			 }

			reportVP("INFO", "Address added successfully");

			if(getcurrentURL().contains("requestid"))
			{
				reportVP(PASS,"Address added successfully");
			}
			else
			{
				reportVP(FAIL,"Unable to add the address");
			}
	}
    
    
    
    
    public void editAddress(String address2) {
    	
    	clickAnElement(link_editAddress);
    	enterText(text_address2, address2);
    	clickAnElement(button_saveAddress);

    	if(getcurrentURL().contains("requestid"))
		{
			reportVP(PASS,"Address edited successfully");
		}
		else
		{
			reportVP(FAIL,"Unable to edit the address");
		}

    	
    }
    
    public void deleteAddress() {
    	
    	clickAnElement(button_delete);
    	clickAnElement(button_confirmDelete);
    	if(getcurrentURL().contains("requestid"))
		{
			reportVP(PASS,"Address edited successfully");
		}
		else
		{
			reportVP(FAIL,"Unable to edit the address");
		}
    }
    }
    
  
    
    

