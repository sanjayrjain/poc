package com.ecom.base;

import com.ecom.util.commonfunctions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static com.ecom.base.BaseFactory.*;

public class ScriptHelper {
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<ExtentTest> eTest = new ThreadLocal<ExtentTest>();
	public static WebDriver driver;
	
	
	
	public ScriptHelper(WebDriver driver) {
		this.driver = getDriver();
	}


	public static WebDriver getDriver() {
		if(driver == null){
			driver =  webDriver.get();
		}
		return driver;
	}

	public static void setWebDriver(WebDriver driver) {
		webDriver.set(driver);
	}

	public static ExtentTest getTest() {
		return eTest.get();
	}

	static void setTest(ExtentTest test) {
		eTest.set(test);
	}

	public static void mousehover(WebElement element) {

		WebDriver wb = getDriver();
		waitForPageLoad(wb);
		Actions actions = new Actions(wb);
        actions.moveToElement(element).build().perform();;
	}

	public static void explicitWaitVisibilityOfElement(WebElement element, int time) {
		waitForPageToLoad(6);
		WebDriverWait elementToBeVisible = new WebDriverWait(getDriver(), time*1000);
		elementToBeVisible.until(ExpectedConditions.visibilityOf(element));
		elementToBeVisible.until(ExpectedConditions.elementToBeClickable(element));
		}

		
	
	
		public static void JSClick()
		   {

		WebElement element = null;
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();;
		       executor.executeScript("arguments[0].click();", new Object[]{element});
		     
		   	
		   }
		
	public static Boolean checkStateSeeking() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		Boolean seekingState = (Boolean) js.executeScript("return document.readyState").equals("complete");
		while (seekingState) {
			seekingState = (Boolean) js.executeScript("return document.readyState").equals("complete");
		}
		return seekingState;
	}

	public static String getConvertedDate(){
		Date today = Calendar.getInstance().getTime();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String datenow = "";
		datenow = df.format(today);
		return datenow;
	}
	public static void waitForPageToLoad(int sec) {
		try {
		Thread.sleep(sec*1000);
		} catch (Throwable e) {
		e.printStackTrace();
		}
		}

	public static void createReportFile(StringBuilder filename){

		// Check whether report folder exists.
		File fnFolder = new File(reportFolder);

		if (! fnFolder.exists()){
			fnFolder.mkdirs();
		}

		File fn = new File(reportFolder+ filename);
		if(! fn.exists()){
			File fname = new File(fn.toString());
			try {
				fname.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	



	public static void checkAnyPageContainsError(String sectionName) {
		WebDriver driver = ScriptHelper.getDriver();
		StringBuilder pageSrc = null;
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body"))));
		pageSrc = new StringBuilder(driver.findElement(By.tagName("body")).getText().replaceAll("[^a-zA-Z0-9]", ""));
		String ErrorMessage = "";

		List<String> errMsgs = new ArrayList<>();
		errMsgs.add("nomatchesfound");
		errMsgs.add("unknownerror");
		errMsgs.add("networkerror");
		errMsgs.add("pleasetryagainorbrowseourpopularproducts");
		errMsgs.add("productunavailable");
		currentURL = getDriver().getCurrentUrl();

		boolean flag = true;
		for (String errMsg : errMsgs) {
			if (pageSrc.toString().toLowerCase().contains(errMsg)) {
				ErrorMessage = errMsg;

				switch(ErrorMessage.toLowerCase()){

                    case "nomatchesfound":
                        FailedNomatchLinks.add(getDriver().getCurrentUrl());
                        break;

                    case "unknownerror":
                        FailedUnknownLinks.add(getDriver().getCurrentUrl());
                        break;

                    case "networkerror":
                        FailedNetworkLinks.add(getDriver().getCurrentUrl());
                        break;

					case "pleasetryagainorbrowseourpopularproducts":
						FailedProductNoPresentLinks.add(getDriver().getCurrentUrl());
						break;

					case "productunavailable":
						FailedProductNoPresentLinks.add(currentURL);
						break;
                }

				flag = false;
			}
		}
		if (flag) {
			reportVP(PASS, sectionName + " - Page loaded correctly as expected.");// <br>[URL: " +
		} else {
			reportVP(FAIL, sectionName + " - Page loaded with ERROR MESSAGE. Error Message type is [ "+ErrorMessage+" ]);// <br> " +
							"[ URL: " + driver.getCurrentUrl()  + "]");
		}
	}

	public static void waitForPageLoad(WebDriver wdriver) {

		// WebDriverWait wait = new WebDriverWait(wdriver, 5, 1000);
		Wait<WebDriver> wait = new FluentWait<>(wdriver).withTimeout(15, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		try {
			//wait.until(AdditionalConditions.angularHasFinishedProcessing());
		} catch (Exception e) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}


		try {
			wait.until(commonfunctions.pageLoadFinished());
		} catch (Exception ee) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		try {
			wait.until(commonfunctions.isJqueryCallDone());
		} catch (Exception ee) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@FindBy(xpath="//a[contains(@aria-label,'Eddie Bauer Home')]")
    private WebElement button_logo;

	public boolean isPageLoaded() {
		waitForPageToLoad(2);
		//getDriver().manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
		return button_logo.isDisplayed();
	}

}