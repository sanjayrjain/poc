package com.ecom.pages;

import com.ecom.base.CoreBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.ecom.base.BaseFactory.catHash;

public class signOn extends CoreBase {

    WebDriver driver;

    @FindBy(xpath = "//*[contains(text(),'Create one NOW')]")
    WebElement link_CreateNewNow;

    @FindBy(xpath = "//*[@id='firstname']")
    WebElement text_firstName;

    @FindBy(xpath = "//*[@id='lastname']")
    WebElement text_lastName;

    @FindBy(xpath = "//*[@id='login']")
    WebElement text_email;

    @FindBy(xpath = "//*[@id='loginPwd']")
    WebElement text_loginPassword;

    @FindBy(id = "regCheckoutFormSubmit")
    WebElement button_CheckoutSubmit;

    public signOn(WebDriver driver) {
        this.driver = getDriver();
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
   }


    public void clickCreateoneLink() {
        link_CreateNewNow.click();
    }

    public void enteruserdetails(String fname,String lname,String passwordInput) {

        enterText(text_firstName,fname);
        enterText(text_lastName,lname);
        String email  = getRandomEmailID();
        enterText(text_email,email);
        enterText(text_loginPassword,passwordInput);
        catHash.put("new_username",email);
        catHash.put("new_password",passwordInput);

        clickAnElement(button_CheckoutSubmit);


    }


}
