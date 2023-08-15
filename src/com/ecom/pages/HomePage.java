

package com.ecom.pages;

import com.ecom.base.ScriptHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.ecom.util.LoadConfigFile;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static com.ecom.util.commonfunctions.generateRandomAddress;

public class HomePage extends AbstractPage {
    public String actualPrice;
    public String productId;
    protected WebDriver driver;

    Random randomGenerator = new Random();
    int randomInt;
    public String sizeoption,color;

    @FindBy(xpath = "//*[@class='sign_in']")
    private WebElement button_singIn;

    @FindBy(id = "loginEmail")
    private WebElement text_loginEmail;

    @FindBy(id = "loginPwd")
    private WebElement text_loginPassword;

    @FindBy(id = "signinFormSubmit")
    private WebElement button_signSubmit;

    @FindBy(xpath = "//*[contains(text(),'Address Book')]")
    private WebElement text_addressBook;

    @FindBy(className = "add-address ir")
    private WebElement button_add_addressir;

    @FindBy(xpath = "//*[@id='addNewStreetAddr']")
    private WebElement text_addNewAddressStreet;

    @FindBy(xpath = "//*[@id='addNewCity']")
    private WebElement text_addNewCity;

    @FindBy(xpath = "//*[@id='addNewState']")
    private WebElement dd_addNewState;

    @FindBy(xpath = "//*[@id='addNewZip']")
    private WebElement text_addNewZip;

    @FindBy(xpath = "//*[@id='editAddressDaytime']")
    private WebElement text_phoneNumber;

    @FindBy(id = "addNewCountry")
    private WebElement dd_selectCountry;

    @FindBy(xpath = "//*[@value='Add Address']")
    private WebElement button_addAddress;

    @FindBy(id = "primaryShip")
    private WebElement cb_primaryShip;

    @FindBy(id = "primaryBilling")
    private WebElement cb_primaryBilling;

    @FindBy(className = "submit")
    private WebElement button_addresssubmit;

    @FindBy(xpath="//*[text()=' Search ']")
    private WebElement button_clickSearch;
    
    @FindBy(xpath="//*[text()='Men']")
    private WebElement button_men;
    
    @FindBy(xpath="//*[text()='Jackets & Vests']")
    private WebElement button_subcategory;

    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        //This initElements method will create all WebElements
        this.randomInt = this.randomGenerator.nextInt(10100);
        PageFactory.initElements(driver, this);
        Assert.assertTrue(super.isPageLoaded());
    }

    public String getProductTitle() {
        WebElement prodTitle = ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@class,'StyledHeader')]/h1"));
        ScriptHelper.explicitWaitVisibilityOfElement(prodTitle, 30);
        prodTitle.click();
        return prodTitle.getText();
    }


    public void clickOnAddToBag() {
        WebElement addToCartBtn = ScriptHelper.getDriver().findElement(By.xpath("//button[contains(@class,'add_to_cart')]"));
        addToCartBtn.click();

    }

    public boolean verifyAddToBagOverlay(String productName) {
        WebElement overlay = ScriptHelper.getDriver().findElement(By.cssSelector("#add_to_cart_success"));
        ScriptHelper.explicitWaitVisibilityOfElement(overlay, 30);
        return overlay.getText().contains(productName);
    }

    public void continueToCheckOut() {
        WebElement checkOutBtn = ScriptHelper.getDriver().findElement(By.xpath("//div[@id='sbCartLinksHolder']/div/a"));
        checkOutBtn.click();
    }
    
    public void proceedwithCheckOut() {
        WebElement checkOutBtn = ScriptHelper.getDriver().findElement(By.xpath("//*[@alt='Proceed to Checkout']"));
        checkOutBtn.click();
    }
    
    public void guestCheckout() {
        WebElement checkOutBtn = ScriptHelper.getDriver().findElement(By.xpath("//*[@alt='GUEST CHECKOUT']"));
        checkOutBtn.click();
    }
    

    public void clickFirstProd() {
        WebElement product = ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@class,'styles__StyledImage')]")); 
        JavascriptExecutor executor = (JavascriptExecutor)ScriptHelper.getDriver();
        executor.executeScript("arguments[0].click();", new Object[]{product});
        
        WebElement price = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='offer']"));
        explicitWaitVisibilityOfElement(price, 50);
        this.actualPrice = price.getText();
        WebElement prID = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='style_number']"));
        this.productId = prID.getText();
        WebElement size = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='size_option_wrapper']"));
        size.click();
        sizeoption =size.getText();
        System.out.println(sizeoption);
        WebElement colortext = ScriptHelper.getDriver().findElement(By.xpath("//*[@id='product-colors']"));
        color = colortext.getText();
       
    }
    public void verifyMiniCart() {
      WebElement size=  ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@class,'pdp-atc-details')]/dl/div[1]"));
      String sizeinminicart=size.getText();
      System.out.println(sizeinminicart);
      if(sizeinminicart.contains(sizeoption))
      {
    	  System.out.println("Size is displayed properly in mini cart");
      }
      else {
    	  System.out.println("size mismatch");
      }
      WebElement colorinminicart=  ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@class,'pdp-atc-details')]/dl/div[2]"));
      String colorminicart=colorinminicart.getText();
      System.out.println(colorminicart);
      if(colorminicart.contains(color))
      {
    	  System.out.println("color is displayed properly in mini cart");
    	  
      }
      else {
    	  System.out.println("color mismatch");
      }
      WebElement instocktext=  ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@class,'pdp-atc-details')]/dl/div[3]"));
      String instock=instocktext.getText();
      if(instock.contains("In Stock"))
      {
    	  System.out.println("Product in stock");
      }
      else {
    	  System.out.println("product out of stock");
      }
      WebElement quanitity=  ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@class,'pdp-atc-details')]/dl/div[4]"));
      String quantitytext=quanitity.getText();
      if(quantitytext.contains("1"))
      {
    	  System.out.println("Quantity is updated properly");
      }
      else {
    	  System.out.println("Quantity mismatch");
    	  
      }
        ScriptHelper.getDriver().findElement(By.xpath("//*[@class='close_button']")).click();
    }

   // public void clickonbag()
    //{
    	//WebElement cart= ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@href,'/checkout/bag.jsp')]"));
    	//explicitWaitVisibilityOfElement(cart, 30);
    	 // JavascriptExecutor executor = (JavascriptExecutor)ScriptHelper.getDriver();
          //executor.executeScript("arguments[0].click();", new Object[]{cart});
    	
    //}
    public void verifyCartforedit() {
        ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@href,'/checkout/bag.jsp')]")).click();
        ScriptHelper.getDriver().findElement(By.xpath("//*[@class='sb-quantity-select']")).sendKeys(new CharSequence[]{"2", Keys.ENTER});
        ScriptHelper.getDriver().navigate().refresh();
  
    }
    
    public String updatedquantitiy()
    {
    	
    	WebElement quantity=ScriptHelper.getDriver().findElement(By.xpath("//*[@class='wl-quantity left']"));
        String updatedquanitity=quantity.getText();  
       
        return updatedquanitity;
    }
    public void verifyCartfordelete() {
    	
    	
        WebElement delete = ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@id,'remove-item')]"));
        JavascriptExecutor executor = (JavascriptExecutor)ScriptHelper.getDriver();
        executor.executeScript("arguments[0].click();", new Object[]{delete});
        ScriptHelper.getDriver().findElement(By.xpath("//*[@src='/static/img/btn-ok.png']")).click();

    }

    public String messageforcartdelete() {
    	
    	WebElement message = ScriptHelper.getDriver().findElement(By.xpath("//*[contains(text(),' bag is currently empty')]"));
    	String text = message.getText();
    	System.out.println(text);
		return text;
    	
    	
    }
    

  

    public void searchprod(String searchInput) {
    	
    	WebElement search =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='search']"));
    	search.sendKeys(new CharSequence[]{searchInput});
    	search.sendKeys(new CharSequence[]{Keys.ENTER});
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public String getprodNamefromsearchresults()
    {
    	
    	WebElement prodName=ScriptHelper.getDriver().findElement(By.xpath("(//h4[@class='title'])"));
    	String prName=prodName.getText();
    	System.out.println(prName);
    	return prName;
    }
    
    public String getprodNamefromlistingpage()
    {
    	
    	WebElement exprodName=ScriptHelper.getDriver().findElement(By.xpath("//h1[@class='title']"));
    	String exprName=exprodName.getText();
    	System.out.println(exprName);
    	return exprName;
    }

    public void searchCase(String search) {
    	
    	WebElement searchField =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='search']"));
    	searchField.sendKeys(new CharSequence[]{search});
    	searchField.sendKeys(new CharSequence[]{Keys.ENTER});
        
    }

   

    public String verifyTitle() {
       WebElement title= ScriptHelper.getDriver().findElement(By.xpath("//h1[@class='title']"));
       String searchTitle=title.getText();
       System.out.println(searchTitle);
       return searchTitle;
    		   
    }

    public void clickguestuserbutton() {
        ScriptHelper.getDriver().findElement(By.xpath("//*[@alt='GUEST CHECKOUT']")).click();
    }

    public void clickLoginbutton() {
        clickAnElement(button_singIn);
    }
    
    public void clicksearch()
    {
    	  WebElement search = ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@class,'styles__StyledSearchButton')]")); 
          JavascriptExecutor executor = (JavascriptExecutor)ScriptHelper.getDriver();
          executor.executeScript("arguments[0].click();", new Object[]{search});
    	  //clickAnElement(search);
    	
    }

    
    public void enterguestUserdetails(String fname, String lname,String address,String zip,String email,String phone) {
    	
    	WebElement firstName =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='addNewFName']"));
    	firstName.sendKeys(new CharSequence[]{fname});
    	WebElement lastName =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='addNewLName']"));
    	lastName.sendKeys(new CharSequence[]{lname});
    	
    	WebElement address1 =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='addNewStreetAddr']"));
    	address1.sendKeys(new CharSequence[]{address});
    
    	
    	
    	//WebElement city =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='addNewCity']"));
    	//city.clear();  
    	//city.sendKeys(new CharSequence[]{cityName});
    	
    	
    	WebElement zipCode =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='addNewZip']"));
    	zipCode.clear();
    	zipCode.sendKeys(new CharSequence[]{zip});
    	zipCode.sendKeys(new CharSequence[]{Keys.TAB});  
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	/*WebElement stateField =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='addNewState']"));
    	stateField.sendKeys(new CharSequence[]{state});

    	stateField.sendKeys(new CharSequence[]{Keys.ENTER});
    	
    	WebElement countryField =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='addNewCountry']"));
    	countryField.sendKeys(new CharSequence[]{country});

    	countryField.sendKeys(new CharSequence[]{Keys.ENTER});*/
	
    	WebElement addEmail =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='addEmail']"));
    	addEmail.sendKeys(new CharSequence[]{email});
    	
    	WebElement addPhone =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='editAddressDaytime']"));
    	addPhone.sendKeys(new CharSequence[]{phone});
    	
    	
    	
    }
    
    public void checkoutButton() {
     WebElement element=   ScriptHelper.getDriver().findElement(By.xpath("//*[@id='checkoutEditAddressFormSubmit']"));
     JavascriptExecutor executor = (JavascriptExecutor)ScriptHelper.getDriver();
     executor.executeScript("arguments[0].click();", new Object[]{element});
    }
    
    
    public void reviewpageValidationsfortotalprice() {
       
        
        WebElement price=ScriptHelper.getDriver().findElement(By.xpath("//*[@class='right os-detail bag-order-amount']"));
        String expectedPrice = price.getText();
        System.out.println(expectedPrice);
        if (ScriptHelper.getDriver().getCurrentUrl().contains("com")) {
            if(expectedPrice.contains("$")){
            System.out.println("Amount is displayed in USD");
            }
            
        }
        
        else if (expectedPrice.contains("CAD")){
            System.out.println("Amount is displayed in CAD");
        }
         
        
        WebElement finalPrice=ScriptHelper.getDriver().findElement(By.xpath("//*[@class='right os-detail bold']"));
        String finalAmount=finalPrice.getText();
        if (ScriptHelper.getDriver().getCurrentUrl().contains("com")) {
            if(expectedPrice.contains("$")){
            System.out.println("Amount is displayed in USD");
            }
            
        }
        
        else  if (expectedPrice.contains("CAD")){
            System.out.println("Amount is displayed in CAD");
        }
        
        
        
    }
    
    public void clickcheckoutButton()
    {
    	ScriptHelper.getDriver().findElement(By.xpath("//*[@alt='CONTINUE CHECKOUT']")).click();
    	
    }
    
 public boolean verifypaymentsincheckoutpage() {
    	
    	boolean flag=ScriptHelper.getDriver().findElement(By.xpath("//*[text()='PAYMENT']")).isDisplayed();
    	return flag;
    }
    
    public boolean verifycheckoutbuttonincheckoutpage() {
    	
    	boolean flag=ScriptHelper.getDriver().findElement(By.xpath("//*[@alt='CONTINUE CHECKOUT']")).isDisplayed();
    	return flag;
    }
    
 public boolean verifyshippingaddressncheckoutpage() {
    	
    	boolean flag=ScriptHelper.getDriver().findElement(By.xpath("//*[text()='SHIPPING ADDRESS']")).isDisplayed();
    	return flag;
    }
 
 public boolean verifybillingaddressincheckoutpage() {
 	
 	boolean flag=ScriptHelper.getDriver().findElement(By.xpath("//*[text()='BILLING ADDRESS']")).isDisplayed();
 	return flag;
 }
 
 public boolean verifyshippingmethodincheckoutpage() {
	 	
	 	boolean flag=ScriptHelper.getDriver().findElement(By.xpath("//*[text()='SHIPPING METHOD']")).isDisplayed();
	 	return flag;
	 }
 
 public boolean verifycontactinformationincheckoutpage() {
	 	
	 	boolean flag=ScriptHelper.getDriver().findElement(By.xpath("//*[contains(text(),'CONTACT INFORMATION')]")).isDisplayed();
	 	return flag;
	 }
 
 public boolean verifypreviousstepincheckoutpage() {
	 	
	 	boolean flag=ScriptHelper.getDriver().findElement(By.xpath("//*[@id='previousStep']")).isDisplayed();
	 	return flag;
	 }
    

    

    public void loginforOrder(String email, String password)
    
    {
    	WebElement emailField =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='loginEmail']"));
    	emailField.sendKeys(new CharSequence[]{email});
    	WebElement passwordField =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='loginPwd']"));
    	passwordField.sendKeys(new CharSequence[]{password});
    	
    	WebElement signIn=ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@id,'loginFormSubmit')]"));
    	 JavascriptExecutor executor = (JavascriptExecutor)ScriptHelper.getDriver();
         executor.executeScript("arguments[0].click();", new Object[]{signIn});
        
         
    	
    }


    public String getproductIDincheckout()
    {
    	   WebElement prodId = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='checkout-textstyle-6']"));
           String id = prodId.getText();
           return id;
    }
    
    

    public void checkoutpagevalidationforproductID() {
        WebElement prodId = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='checkout-textstyle-6']"));
        String id = prodId.getText();
        System.out.println(id);
        Assert.assertNotNull(prodId);
        Assert.assertEquals(this.productId, id);
    }
    
    public String pricevalidationcheckout()
    {
    	
    	WebElement price = ScriptHelper.getDriver().findElement(By.className("reduced-price"));
    	String pricedisplayed=price.getText();
    	System.out.println(pricedisplayed);
    	return pricedisplayed;
    }

    public void checkoutpagevalidationfortotalprice() {
        WebElement price = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='bag-order-amount right os-detail bold']"));
        String expectedPrice = price.getText();
        System.out.println(expectedPrice);
        Assert.assertNotNull(price);
        if (ScriptHelper.getDriver().getCurrentUrl().contains("com")) {

if(expectedPrice.contains("$"))
{
	System.out.println("Amount is in USD");
}

else if (ScriptHelper.getDriver().getCurrentUrl().contains("ca")) {

if(expectedPrice.contains("CAD"))
{
	System.out.println("Amount is in CAD");
}

}
        }


    }
    
    
    public void Login(String email, String password) {
        String em = email;

       if(email == null ||
         email.toLowerCase().contains("")){
           email = LoadConfigFile.getValue("login_username");
           password = LoadConfigFile.getValue("login_password");
       }

        enterText(text_loginEmail,email);
        enterText(text_loginPassword,password);
        clickAnElement(button_signSubmit);
    }


    	
    public void movetoWishList() {
    	
    	WebElement wishList = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='move-item-to-wishlist ']"));
        JavascriptExecutor js = (JavascriptExecutor)ScriptHelper.getDriver();
        js.executeScript("arguments[0].click();", new Object[]{wishList});
    }
    public void editwishList()
    {
        ScriptHelper.getDriver().findElement(By.xpath("//*[@class='wl-item-quantity']")).sendKeys("2",Keys.ENTER);
    }
    public boolean editedWishlistdisplayed() {
       boolean flag= ScriptHelper.getDriver().findElement(By.xpath("//*[@class='sb-price-stock left']/div[2]")).isDisplayed();
       return flag;
        
    }
        
        public void deletewishList() {
        WebElement removewishList =ScriptHelper.getDriver().findElement(By.xpath("//*[@class='remove-item-from-wl']"));
        JavascriptExecutor js = (JavascriptExecutor)ScriptHelper.getDriver();
        js.executeScript("arguments[0].click();", new Object[]{removewishList});
        ScriptHelper.getDriver().findElement(By.xpath("//*[@src='/static/img/btn-ok.png']")).click();
        
    	
    }
    
    public boolean wishListDisplayed()
    {
    	WebElement wishList = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='move-item-to-wishlist ']"));
    	boolean flag=wishList.isDisplayed();
    	return flag;
    }
    
    public void confirmWishlistinEmpty()
    
    {
    	WebElement wishList =ScriptHelper.getDriver().findElement(By.xpath("//*[@aria-label='Select this link to visit your Wish List']"));
        JavascriptExecutor js = (JavascriptExecutor)ScriptHelper.getDriver();
        js.executeScript("arguments[0].click();", new Object[]{wishList});
    }
    
    public boolean confirmwishlistisempty()
    {
      boolean flag=  ScriptHelper.getDriver().findElement(By.xpath("//*[@id='wishListCountHolder']")).isDisplayed();
    	return flag;
    }
    
   public void clicklogo()
   {
	   
	 WebElement logo=  ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@aria-label,'Eddie Bauer')]"));
	 mousehover(logo);
	 logo.click();
   }
   
    public void PDPvalidationsforproductID() {
    	
    	WebElement productID= ScriptHelper.getDriver().findElement(By.xpath("//*[@class='style_number']"));
    	productID.isDisplayed();
    	}
    public String getproductID() {
    	WebElement productID= ScriptHelper.getDriver().findElement(By.xpath("//*[@class='style_number']"));
    	String id = productID.getText();
    	return id;
    }
    public String priceinPDP() {
    	
    	 WebElement price = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='offer']"));
         String expectedPrice = price.getText();
         return expectedPrice;
    	
    }
    public void PDPvalidationsforprice() {
    	  
    	   WebElement price = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='old_price']"));
           String expectedPrice = price.getText();
           System.out.println(expectedPrice);
           Assert.assertNotNull(expectedPrice);
    }
           
           public String getPrice()
           {
        	   
        	   WebElement price = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='old_price']"));
               String expectedPrice = price.getText();
           if (ScriptHelper.getDriver().getCurrentUrl().contains("com")) {
               Assert.assertTrue(expectedPrice.contains("$"), "amount is in USD");
           } else {
               Assert.assertTrue(expectedPrice.contains("CAD"), "amount is in CAD");
           }
           return expectedPrice;
    }
    public boolean PDPvalidationsforreview() {
         boolean flag=  ScriptHelper.getDriver().findElement(By.xpath("//*[@class='reviews']")).isDisplayed();
     	return flag;
    }
    
    public boolean PDPvalidationsforcolors() { 
          boolean flag= ScriptHelper.getDriver().findElement(By.xpath("//*[@id='product-colors']")).isDisplayed();
      	return flag;
    }
    
    public boolean PDPvalidationsforsize() {
          boolean flag= ScriptHelper.getDriver().findElement(By.xpath("//*[@id='product-sizes']")).isDisplayed();
      	return flag;
}

    public boolean PDPvalidationsforaddtocartbutton() {
          boolean flag= ScriptHelper.getDriver().findElement(By.xpath("//*[text()='Add to Bag']")).isDisplayed();
      	return flag;
    }
    
    public boolean PDPvalidationsforproductdescription() {
         boolean flag=  ScriptHelper.getDriver().findElement(By.xpath("//*[text()='Product Description']")).isDisplayed();
     	return flag;
    }
    public boolean PDPvalidationsforfeatures() {
    	 boolean flag=     ScriptHelper.getDriver().findElement(By.xpath("//*[text()='Features']")).isDisplayed();
		return flag;
    }
    
     
    	  
    	

   
    public void navigatetomensubcategory()
    {
    	
    
    	mousehover(button_men);
    	  clickAnElement(button_subcategory);
    	  
    }
    
   
    	
    	public void sortBy() {
    	  ScriptHelper.getDriver().findElement(By.xpath("")).click();
    	  ScriptHelper.getDriver().findElement(By.xpath("")).click();
    	}
    	public void lefthandNavigation()
    	{
    	  ScriptHelper.getDriver().findElement(By.xpath("")).click();
    	  
    	  
    	  
    	  WebElement outerWear =ScriptHelper.getDriver().findElement(By.xpath("//*[text()='Outerwear' and @role='link']"));
          JavascriptExecutor js = (JavascriptExecutor)ScriptHelper.getDriver();
          js.executeScript("arguments[0].click();", new Object[]{outerWear});

    	
    }
    	
    	public String getTitle()
    	{
    		WebElement title = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='title']"));
    		String text = title.getText();
			return text;
    		
    		
    	}
    	
    	
    
    public void myaccountsLink()
    {
    	Actions se = new Actions(driver);
    	
    	List <WebElement> alllinks = ScriptHelper.getDriver().findElements(By.xpath("//*[@id='accountNav']"));

        for(int i=0;i<alllinks.size();i++) {
            System.out.println(alllinks.get(i).getText());
            
    
    
        }
    		
    }
    public void removetheaddedproduct() {
        WebElement remove = ScriptHelper.getDriver().findElement(By.xpath("//*[@id='remove-item-cart-id_0']"));
        remove.click();

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

        WebElement confirmdelete = ScriptHelper.getDriver().findElement(By.xpath("//*[@class='confirm-modal button removeAllTealium']"));
        JavascriptExecutor js = (JavascriptExecutor)ScriptHelper.getDriver();
        js.executeScript("arguments[0].click();", new Object[]{confirmdelete});
    }

    public void enterAddress(String country) {
        HashMap<String,String> address = new HashMap<>();

        if(country.equalsIgnoreCase("CA")){
            address=  generateRandomAddress("CA");
        }else{
            address = generateRandomAddress("US");
        }


        clickAnElement(text_addressBook);
        clickAnElement(button_add_addressir);
        enterText(text_addNewAddressStreet,address.get("street"));
        enterText(text_addNewAddressStreet,address.get("city"));
        selectFromDropDown(text_addNewAddressStreet,address.get("state"));

//		enterText(text_addNewState,state);
//		enterText(text_addressBook,cityName);
//		enterText(text_addressBook,cityName);
//		enterText(text_addressBook,cityName);




    }
    
   
        
         
    	
   

    public void editAddress(String address2)
    {

        ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@aria-label,'Select this link to Edit')]")).click();
        WebElement editAddress =ScriptHelper.getDriver().findElement(By.xpath("//*[@id='addNewStreetAddr2']"));
        editAddress.sendKeys(new CharSequence[]{address2});

        ScriptHelper.getDriver().findElement(By.xpath("//*[@value='Add Address']")).click();


    }

    public void deleteAddress()
    {

        WebElement deleteAddress = ScriptHelper.getDriver().findElement(By.xpath("//*[contains(@aria-label,'Select this link to Delete')]"));
        JavascriptExecutor js = (JavascriptExecutor)ScriptHelper.getDriver();
        js.executeScript("arguments[0].click();", new Object[]{deleteAddress});
        ScriptHelper.getDriver().findElement(By.xpath("//*[@src='/static/img/btn-ok.png']")).click();



    }
}
