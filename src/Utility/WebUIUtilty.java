package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ecom.base.ScriptHelper;

public class WebUIUtilty {

	static WebDriver driver;
	public static Properties prop;
	
	public static WebDriver launchBrowser(String browserName) throws FileNotFoundException, IOException {
		
		
		
		if (browserName.equalsIgnoreCase("browserstack")) {
			prop = new Properties();
			prop.load(new FileInputStream(new File("./GlobalSettings.properties")));
			
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("browserName", prop.getProperty("browserName"));
			caps.setCapability("device", prop.getProperty("device"));
			caps.setCapability("realMobile", prop.getProperty("realMobile"));
			caps.setCapability("os_version", prop.getProperty("os_version"));
			caps.setCapability("name", prop.getProperty("name"));
			caps.setCapability("name", prop.getProperty("name"));
			caps.setCapability("browserstack.local", "true");
			caps.setCapability("browserstack.acceptInsecureCerts", "true");
			caps.setCapability("browserstack.networkLogs", "true");
			
			 ChromeOptions options = new ChromeOptions();
			    Map < String, Object > prefs = new HashMap < String, Object > ();
			    Map < String, Object > profile = new HashMap < String, Object > ();
			    Map < String, Object > contentSettings = new HashMap < String, Object > ();

			    // SET CHROME OPTIONS
			    // 0 - Default, 1 - Allow, 2 - Block
			    contentSettings.put("geolocation", 1);
			    profile.put("managed_default_content_settings", contentSettings);
			    prefs.put("profile", profile);
			    options.setExperimentalOption("prefs", prefs);

			    // SET CAPABILITY
			    caps.setCapability(ChromeOptions.CAPABILITY, options);

			driver = new RemoteWebDriver(new URL("https://" + prop.getProperty("USERNAME") + ":" + prop.getProperty("AUTOMATE_KEY") + "@hub-cloud.browserstack.com/wd/hub"), caps);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
		} else if (browserName.equalsIgnoreCase("chrome")) {
			// getLocator("chomreDriverexePath")
			System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe"); 
			Map<String, String> mobileEmulation = new HashMap<>();

			mobileEmulation.put("deviceName", "Nexus 5");
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("mobileEmulation", mobileEmulation);
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		}
		
		return driver;
	}
	
	
	 public static String triggerEmail(){
	 		
	 		String generatedString = RandomStringUtils.random(10, false, true);
	 		String email = generatedString + "@ebtesting.com";
	 		return email;
	 		
	 	}
     

	public static String getRandomEmailID(){

		String generatedString = RandomStringUtils.random(10, false, true);
		String email = generatedString + "@ebtesting.com";
		return email;

	}
	
	public void mouseHoverclick(WebElement ele) {
		Actions se = new Actions(driver);
		se.moveToElement(ele).build().perform();
		se.click();
		
		
	}

	public static void lauchApplication(String url) {
		driver.navigate().to(url);
	}

	public static void timeOUt(long seconds) {
		long milliseconds = seconds * 1000;
		try {
			Thread.sleep(milliseconds);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void jsclick(WebElement element) {

		JavascriptExecutor executor = (JavascriptExecutor) ScriptHelper.getDriver();
		executor.executeScript("arguments[0].click();", new Object[] { element });
	}

	public static void acceptOrDismissAlert(String acceptOrDismiss) {
		if (acceptOrDismiss.equalsIgnoreCase("Accept")) {
			Alert alertObj = driver.switchTo().alert();
			alertObj.accept();
		} else if (acceptOrDismiss.equalsIgnoreCase("Dismiss")) {
			Alert alertObj = driver.switchTo().alert();
			alertObj.dismiss();
		}
	}

	public static String getTextOfAlert() {
		Alert alertObj = driver.switchTo().alert();
		String alertText = alertObj.getText();
		return alertText;

	}

	public static WebUIUtilty scrollArrowDown(int numberOfTimes) {
		Actions actionObj = new Actions(driver);
		for (int i = 0; i < numberOfTimes; ++i) {
			actionObj.sendKeys(Keys.ARROW_DOWN).build().perform();
		}
		WebUIUtilty obj = new WebUIUtilty();
		return obj;

	}

	public static void scrollArrowUP(int numberOfTimes) {
		Actions actionObj = new Actions(driver);
		for (int i = 0; i < numberOfTimes; ++i) {
			actionObj.sendKeys(Keys.ARROW_UP).build().perform();
		}

	}

	public void selectDropDownUsingText(WebElement ele, String value) {
		 
			new Select(ele).selectByVisibleText(value);
		

	}
	
	public static void pressTab(int numberOfTimes) {
		Actions actionObj = new Actions(driver);
		for (int i = 0; i < numberOfTimes; ++i) {
			actionObj.sendKeys(Keys.TAB).build().perform();
		}
	}

	public static void scrollPageDown(int numberOfTimes) {
		Actions actionObj = new Actions(driver);
		for (int i = 0; i < numberOfTimes; ++i) {
			actionObj.sendKeys(Keys.PAGE_DOWN).build().perform();
		}

	}

	public static void scrollPageUP(int numberOfTimes) {
		Actions actionObj = new Actions(driver);
		for (int i = 0; i < numberOfTimes; ++i) {
			actionObj.sendKeys(Keys.PAGE_UP).build().perform();
		}

	}

	public static void pressEnter() {
		Actions actionObj = new Actions(driver);
		actionObj.sendKeys(Keys.ENTER).build().perform();
	}
	
	public static void pressEsc() {
		Actions actionObj = new Actions(driver);
		actionObj.sendKeys(Keys.ESCAPE).build().perform();
	}

	public static String getTimeStamp() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sd.format(new Date());
		String date1 = date.replaceAll("[^0-9]", "");
		return date1;

	}
	
	public long takeSnap(){
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+number+".jpg"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return number;
	}
	
	public void explicitWait(String xpath) {

		WebDriverWait wait = new WebDriverWait(driver, 40);


		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
	
			}
			
	public void explicitWaitwithclassName(String xpath) {

		WebDriverWait wait = new WebDriverWait(driver, 40);


		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(xpath))).isDisplayed();
	
			}
			
	public void explicitWaitwithID(String xpath) {

		WebDriverWait wait = new WebDriverWait(driver, 40);


		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(xpath))).isDisplayed();
	
			}
			

	public static void clickOnAccountProfileLinks(String linkText) {
		List<WebElement> accountProfile_LinkList = driver
				.findElements(By.xpath("//div[@class='c-popover__content']//li"));
		int count = accountProfile_LinkList.size();
		for (int i = 0; i < count; i++) {
			String str = accountProfile_LinkList.get(i).findElement(By.xpath(".//a/div/div")).getText();
			if (str.trim().equalsIgnoreCase(linkText)) {
				accountProfile_LinkList.get(i).findElement(By.xpath(".//a")).click();
				break;
			}

		}

	}

}
