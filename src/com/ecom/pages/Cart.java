package com.ecom.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.ecom.base.CoreBase;

public class Cart extends CoreBase {

    WebDriver driver; 
    public String textfromPLP,textfromMinicart,colorfromPLP,colorfromMinicart;
	public static String productID;
    
    
    @FindBy(xpath="//*[contains(@class,'styles__StyledImage')]")
    WebElement link_firstProduct;
    
    @FindBy(xpath="//*[@class='size_option_wrapper']")
    WebElement button_size;
    
    @FindBy(xpath="//button[contains(@class,'add_to_cart')]")
    WebElement button_addtoCart;
    
    @FindBy(xpath="//*[contains(@class,'pdp-atc-details')]/dl/div[1]")
    WebElement text_sizefromcart;
    
    @FindBy(xpath="//div[@class='label']/span[2]")
    WebElement text_colorfromplp;
    
    @FindBy(xpath="//*[contains(@class,'pdp-atc-details')]/dl/div[2]")
    WebElement text_colorfromminicart;
    
    @FindBy(xpath="//*[contains(@class,'pdp-atc-details')]/dl/div[3]")
    WebElement text_inStock;
    
    @FindBy(xpath="//*[contains(@class,'pdp-atc-details')]/dl/div[4]")
    WebElement text_quantity;
    
    @FindBy(className="button-link")
    WebElement button_checkOut;
  
    @FindBy(xpath="//*[@class='style_number']")
   static WebElement text_prodID;
    

    @FindBy(xpath = "//*[@href='/checkout/bag.jsp']")
    static WebElement header_CartImage;

    @FindBy(xpath="//div[@id='sbShoppingBagHolder']//div[contains(@class,'item-bg')]")
    private List<WebElement> cart_ItemsList;

    @FindBy(xpath="//*[@alt='OK']")
    private WebElement cart_ItemsDeleteOKButton;

    public  Cart(WebDriver driver)
    {
    	  this.driver = getDriver();
          //This initElements method will create all WebElements
          PageFactory.initElements(driver, this);
          Assert.assertTrue(super.isPageLoaded());
    }
    
    public void clickfirstProduct()
    {
    	explicitWaitVisibilityOfElement(link_firstProduct, 30000);
    	clickAnElement(link_firstProduct);
    }
    
   public void clickSize()
   {
	   clickAnElement(button_size);
   }
   
   public String sizefromPLP()
   {
	    textfromPLP= getText(button_size);
	   return textfromPLP;
   }
    
   public void clickaddtoCart() {
	   clickAnElement(button_addtoCart);
   }
   
   public String sizefromminiCart()
   {
	    textfromMinicart= getText(text_sizefromcart);
	   return textfromMinicart;
   }
   
   public void sizeComparison()
   {
	   boolean flag=(textfromMinicart.contains(textfromPLP));
	  if(flag)
	  {
		  System.out.println("Size matches");
	  }
	  else System.out.println("size mismatch");
	  
   }
   
   public String colorfromplp()
   {
	   colorfromPLP=getText(text_colorfromplp);
	   return colorfromPLP;
   }
   
   public String colorfromminiCart()
   {
	   
	   colorfromMinicart=getText(text_colorfromminicart);
	   return colorfromMinicart;
   }
   
   public void colorComparison()
   {
	   boolean flag=(colorfromMinicart.contains(colorfromPLP));
	  if(flag)
	  {
		  System.out.println("Color matches");
	  }
	  else System.out.println("Color mismatch");
	  
   }
    
public void inStockCheck()
{
	String text =getText(text_inStock);
	boolean flag=text.contains("In Stock");
	if(flag) {
		System.out.println("Product in stock");
	}
		else System.out.println("Product out of stock");
	}


public void quantityCheck()
{
	String text =getText(text_quantity);
	boolean flag=text.contains("1");
	if(flag) {
		System.out.println("Quantity is updated properly");
	}
		else System.out.println("Quantity mismatch");
	}

public void clickCheckout()
{
	clickAnElement(button_checkOut);
}

public static String productIDfromPDP() {
	
	productID=getText(text_prodID);
	return productID;
	
}
public int getCartItemCount() {
return cart_ItemsList.size();
}

private IcartItem cartItem = new CartItem();
private WebElement currentCartItem;

public interface IcartItem {
public void deleteItem();
}

public IcartItem getCartItem(int index) {
currentCartItem = cart_ItemsList.get(index);
return cartItem;
}

public class CartItem implements IcartItem {
@Override
public void deleteItem() {
currentCartItem.findElement(By.xpath(".//div[2]//a[text()='Remove']")).click();
}
}

public void deleteCartItems() {
int count = getCartItemCount();
for (int i = 0; i < count; i++) {
getCartItem(0).deleteItem();
clickAnElement(cart_ItemsDeleteOKButton);
waitForPageToLoad(5);
}
}

}
