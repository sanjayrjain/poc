

package com.ecom.tests;

import com.ecom.base.ScriptHelper;
import com.ecom.base.SuiteListener;
import com.ecom.pages.HomePage;
import com.ecom.pages.signOn;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.ecom.util.commonfunctions.verifyCurrentURLRequest;
import static com.ecom.util.commonfunctions.verifyPageContentForText;

@Listeners({SuiteListener.class})

public class UserCreation extends signOn {
    private static WebDriver driver;

    public UserCreation() {
        super(getDriver());
    }


    @Test
    public void Usercreation() {
        try {
            driver = ScriptHelper.getDriver();
            HomePage landingPage = new HomePage(driver);
            signOn SignonPage = new signOn(driver);

            landingPage.clickLoginbutton();
            reportVP("INFO", "Login icon is successfully clicked");
            SignonPage.clickCreateoneLink();
            reportVP("INFO", "Create one account link is clicked");
            SignonPage.enteruserdetails("arjun", "kumar", "Cameraman123$");
            reportVP("INFO", "User creation successfully");
            verifyCurrentURLRequest("acc-home", "Sign In Landing Page");
            verifyPageContentForText("Member Number", "My accounts");
        } catch (Exception e) {
            e.printStackTrace();
            reportVP(FAIL, "Exception occured. " + e.getStackTrace());
        }

    }
}

