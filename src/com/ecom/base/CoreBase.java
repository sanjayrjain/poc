package com.ecom.base;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import com.ecom.util.LoadConfigFile;

import javax.mail.MessagingException;

import static com.ecom.util.commonfunctions.generateAndSendEmail;


public class CoreBase extends ScriptHelper{

	public static ExtentReports extent;

	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";
	public static final String WARNING = "WARNING";
	public static final String INFO = "INFO";
	public static String reportScreenshot="" ;
	public static boolean screenshotForPassStep = false;
	public static boolean screenshotForFailStep = false;
	public static boolean screenshotForAllSteps = false;
	public static String currentURL = "";

	public CoreBase() {
		super(getDriver());
	}

	public CoreBase(WebDriver driver) {
		super(getDriver());
	}



	@BeforeSuite
	public void beforeSuite() {
		killBrowserDriverInstances();
		extent = BaseFactory.createExtentReportInstance();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		extent.flush();
        try {
            generateAndSendEmail();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("####MESSAGE::ExtentReport- Final report flushed successfully.");

	}

	protected void killBrowserDriverInstances() {
		Process process = null;
		if (System.getProperty("os.name").contains("Windows")) {
			try {
				process = Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
				process = Runtime.getRuntime().exec("taskkill /F /IM firefoxdriver.exe /T");
				process = Runtime.getRuntime().exec("taskkill /F /IM iedriver.exe /T");
			} catch (IOException e) {

				e.printStackTrace();
			}
			process.destroy();
		}
	}

	public static String captureScreen() {
		String name = "failed_"+ UUID.randomUUID().toString();
		try {
			File scrFile = ((TakesScreenshot) ScriptHelper.getDriver()).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(BaseFactory.reportFolder + "screenshots/" + name + ".jpg"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return name;
	}

	public static void reportVP(String stepStatus, String reportDescription) {

		if(reportScreenshot.equalsIgnoreCase("") ) {

			LoadConfigFile.getInstance();
			screenshotForPassStep = LoadConfigFile.getValue("TakeScreenshotForPassedStep").contains("true");
			screenshotForFailStep = LoadConfigFile.getValue("TakeScreenshotForFailedStep").contains("true");
			screenshotForAllSteps = LoadConfigFile.getValue("TakeScreenshotForAllSteps").contains("true");

			if (screenshotForAllSteps) {
				screenshotForPassStep = true;
				screenshotForFailStep = true;
			}
			reportScreenshot = "true";
		}

		ExtentTest tes = ScriptHelper.getTest();
		MediaEntityModelProvider img = null;

		try {

			if (stepStatus.equalsIgnoreCase(Status.PASS.toString())) {
				tes.pass("PASSED: " + reportDescription);
                System.out.println("PASS: " + reportDescription);
				if (screenshotForPassStep) {
					String scrnshtName = captureScreen();
					scrnshtName = "./screenshots/" + scrnshtName + ".jpg";
					img = MediaEntityBuilder.createScreenCaptureFromPath(scrnshtName).build();
					tes.pass("Refer Screenshot for URL [" + ScriptHelper.getDriver().getCurrentUrl() + "]", img);
				}
			} else if (stepStatus.equalsIgnoreCase(Status.FAIL.toString())) {
				currentURL = ScriptHelper.getDriver().getCurrentUrl();
				tes.fail(MarkupHelper.createLabel("FAILED: " + reportDescription + " URL ["+currentURL +"]", ExtentColor.RED));
                System.out.println("FAIL: " + reportDescription + " , The URL is [ " +currentURL+" ]" );
				/*if (screenshotForFailStep) {*/
					String scrnshtName = captureScreen();
					scrnshtName = "./screenshots/" + scrnshtName + ".jpg";
					img = MediaEntityBuilder.createScreenCaptureFromPath(scrnshtName).build();
					tes.fail("Refer Screenshot: [URL:" + currentURL + "]", img);
				//}
			} else if (stepStatus.equalsIgnoreCase(Status.WARNING.toString())) {
				tes.warning(MarkupHelper.createLabel("WARNING: " + reportDescription, ExtentColor.ORANGE));
				String scrnshtName = captureScreen();
				scrnshtName = "./screenshots/" + scrnshtName + ".jpg";
				img = MediaEntityBuilder.createScreenCaptureFromPath(scrnshtName).build();
				tes.warning("Refer Screenshot: [URL:" + ScriptHelper.getDriver().getCurrentUrl() + "]", img);
			} else if (stepStatus.equalsIgnoreCase(Status.INFO.toString())) {
				tes.info("INFO: " + reportDescription);
                System.out.println("INFO: " + reportDescription );
				if (screenshotForAllSteps) {
					String scrnshtName = captureScreen();
					scrnshtName = "./screenshots/" + scrnshtName + ".jpg";
					img = MediaEntityBuilder.createScreenCaptureFromPath(scrnshtName).build();
					tes.info("Refer Screenshot: [URL:" + ScriptHelper.getDriver().getCurrentUrl() + "]", img);
				}
			}
		} catch (IOException e) {
			System.out.println("Exception occured building Media Entity Builder :: " + e.getMessage());
		}
		extent.flush();
	}

	public static void enterText(WebElement identifier, String text){

		WebDriver driver = getDriver();
		currentURL = getDriver().getCurrentUrl();
		boolean bflag_id = false;


		try {
			boolean flag = isElementLoaded(identifier);
			if( flag){
				reportVP("INFO", "The Web Element is present in the Page, The Element xpath is [<br>'"+identifier+"</br>]");
				WebElement textBox = identifier;
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].style.border='3px dotted red'", textBox);

			}else{
				reportVP("FAIL", "The Web Element is not present in the Page, The Element xpath is ["+identifier+"]");
			}

		}catch (Exception e){
			reportVP("FAIL", "General Error, The Element xpath is ["+identifier+"] is not loaded on the page");
		}

		try{
			identifier.clear();
			identifier.sendKeys(text);
			bflag_id = true;

		}catch (Exception e){

		}

		try{
			if( ! bflag_id ){
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("document.getElementsById("+identifier+").value='"+text+"';");
				bflag_id = true;
			}
		}catch (Exception e){
			reportVP("FAIL", "Couldn't enter the text in the edit box, The Element xpath is ["+identifier+"] is not loaded on the page");
		}

		if( bflag_id ){
			reportVP("INFO", "Entered the text in the edit box, The Element xpath is ["+identifier+"] on the page");
		}
	}

	public static void enterTextandclickEnter(WebElement identifier, String text){

		WebDriver driver = getDriver();
		currentURL = getDriver().getCurrentUrl();
		boolean bflag_id = false;


		try {
			boolean flag = isElementLoaded(identifier);
			if( flag){
				reportVP("INFO", "The Web Element is present in the Page, The Element xpath is ["+identifier+"]");
				WebElement textBox = identifier;
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].style.border='3px dotted red'", textBox );
				identifier.sendKeys(Keys.ENTER);

			}else{
				reportVP("FAIL", "The Web Element is not present in the Page, The Element xpath is ["+identifier+"]");
			}

		}catch (Exception e){
			reportVP("FAIL", "General Error, The Element xpath is ["+identifier+"] is not loaded on the page");
		}

		try{
			identifier.clear();
			identifier.sendKeys(text);
			identifier.sendKeys(Keys.ENTER);
			bflag_id = true;

		}catch (Exception e){

		}

		try{
			if( ! bflag_id ){
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("document.getElementsById("+identifier+").value='"+text+"';");
				identifier.sendKeys(Keys.ENTER);
				bflag_id = true;
			}
		}catch (Exception e){
			reportVP("FAIL", "Couldn't enter the text in the edit box, The Element xpath is ["+identifier+"] is not loaded on the page");
		}

		if( bflag_id ){
			reportVP("INFO", "Entered the text in the edit box, The Element xpath is ["+identifier+"] on the page");
		}
	}


	public static boolean isElementLoaded(WebElement elementToBeLoaded) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 120);
			wait.until(ExpectedConditions.elementToBeClickable(elementToBeLoaded));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public static String getRandomEmailID(){

		String generatedString = RandomStringUtils.random(10, false, true);
		String email = generatedString + "@ebtesting.com";
		return email;

	}
public static String getText(WebElement element)
{
	String text = element.getText();
	return text;
}
	public  static  void clickAnElement(WebElement element){
		boolean flag = false;
		boolean bflag = isElementLoaded(element);
		WebDriver driver = getDriver();
		if( bflag){
			reportVP("INFO", "The Web Element is present in the Page, The Element xpath is ["+element+"]");
			WebElement textBox = element;
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='3px dotted blue'", textBox);
		}

		if(element.getClass().getName().contains("By")) {
			getDriver().findElement((By) element).click();
			flag = true;
		} else {
			((WebElement) element).click();
			flag = true;
		}


		try {
			if( !flag ){

				WebElement ele = element;
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", ele);
				flag = true;
			}

		}catch (Exception e){

		}

		if(! flag ){
			reportVP("FAIL", "Click Operation failed for the element");
		}

	}
	
	public String getcurrentURL() {
		String URL;
		return  URL=ScriptHelper.getDriver().getCurrentUrl();
	}

	public static void selectFromDropDown(WebElement dropId,String value){

		boolean flag = false;
		boolean bflag = isElementLoaded(dropId);
		WebDriver driver = getDriver();
		if(bflag) {
			reportVP("INFO", "The Web Element is present in the Page, The Element xpath is [" + dropId + "]");
			WebElement textBox = dropId;
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='3px dotted blue'", textBox);
		}
		Select select = new Select(dropId);
		List<WebElement> lst = select.getOptions();
		for (int i = 0;i<lst.size();i++) {
			try {
				String txt = lst.get(i).getText();
				if (txt.toLowerCase().contains(value.toLowerCase())) {
					select.selectByVisibleText(value);
					flag = true;
					break;
				}
			} catch (Exception e) {
				select.selectByIndex(i);
			}
		}

		if(! flag ){
			reportVP("FAIL", "Drop down doesnt have the value present. value is ["+value+"]");
		}

	}
	
	public boolean isDisplayed(WebElement element)
	{
		boolean flag=element.isDisplayed();
		return flag;
	}
}
