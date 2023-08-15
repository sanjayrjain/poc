@CarFunctionality
Feature: car

Scenario: To verify display of Shopping cart page.
Given Guest/Registered user is in home page  
When searched for anyproduct and item selected
Then click on add to bag button 
When popup window displayed click on checkout 
Then cart page displayed
And check display of items

Scenario: To verify the display of product block in shopping cart page.
Given Guest/Registered user is in home page   
When searched for anyproduct and item selected
Then click on add to bag button 
And click on Mini cart from global header 
Then User should be navigated to Shopping cart page
And check display of product block in shopping cart page.

Scenario: To verify products list price and Sale price in shopping cart page 
Given Guest/Registered user is in home page  
When  User search and select any product contains list and sale price 
Then  User clicks on add to bag button 
And   User clicks on Mini cart from global header                      
Then  User should be navigated to shopping cart page  
And   User checks display of sale and list price in cart page


Scenario: To verify Order Summary section in Shopping cart page.
Given Guest/Registered user is in home page   
When  User searchs for any product and select
Then  User clicks on add to bag button 
And   User clicks on Mini cart from global header                     
Then  User clicks cart button                                                                          
And   User checks displayed Order Summary section in cart page.

Scenario: To verify the display of Selected for you section in shopping cart page.
Given Guest/Registered user is in home page   
When User searchs for any product and select
Then User clicks on add to bag button  
And  User clicks on Mini cart from global header                                                
Then User should be navigated to shopping cart page                                                                           
And  User checks the display of Selected for you section.

Scenario: To verify save the product to Save for later section in Shopping cart page.
Given Guest/Registered user is in home page  
When  User searchs for any product and select
Then  User clicks on add to bag button  
And   User clicks on Mini cart from global header                                                
Then  User should be navigated to shopping cart page                                                                           
And   User checks the display of Save for later section               

Scenario: To verify the display of "People also purchased" in Shopping cart page.
Given Guest/Registered user is in home page   
When  User searchs for any product and select
Then  User clicks on add to bag button  
And   User clicks on Mini cart from global header                                                
Then  User should be navigated to shopping cart page                                                                           
And   User checks the display of "People also purchased" section.

Scenario: To verify user is able to edit quantity in shopping cart page.
Given Guest/Registered user is in home page   
When  User searchs for any product and select
Then  User clicks on add to bag button  
And   User clicks on Mini cart from global header                                                
Then  User should be navigated to shopping cart page                                                                           
And   User clicks on edit link                                              
Then  Edit item popup should be displayed with elements                                                                                      
And   User edit any option from edit pop up                                                                         
Then  User checks display of shopping cart page after editing the product 

Scenario: To verify user is able to edit quantity in shopping cart page.
Given Guest/Registered user is in home page   
When  User searchs for any product and select
Then  User clicks on add to bag button  
And   User clicks on Mini cart from global header                                                
Then  User clicks cart button                                                                        
And   User clicks on edit link                                              
Then  Edit item popup should be displayed with elements                                                                                      
And   User edit any option from edit pop up                                                                         
Then  User checks display of shopping cart page after editing the product 

Scenario: To verify that user is able to update the quantity in shopping cart page.
Given Guest/Registered user is in home page   
When  User searchs for any product and select
Then  User clicks on add to bag button  
And   User clicks on Mini cart from global header                                                
Then  User should be navigated to shopping cart page                                                                           
And   User clicks on edit link to increase or decrease the product quantity                                             

Scenario: To verify that user is able to delete the product from cart.
Given Guest/Registered user is in home page   
When  User searchs for any product and select
Then  User clicks on add to bag button  
And   User clicks on Mini cart from global header                                                
Then  User should be navigated to shopping cart page                                                                           
And   User clicks on delete icon button                                          
Then  product should be removed from shopping cart 

Scenario: To verify that user is able to add Donation to cart.
Given Guest/Registered user is in home page   
When  User searchs for any product and select
Then  User clicks on add to bag button  
And   User clicks on Mini cart from global header                                                
Then  User should be navigated to shopping cart page                                                                           
And  User enters the amount in Amount field and click on Donate button                                                                       
Then User checks the donation amount added to shopping cart
And  User verify that donation amount is added as separate line item 



Scenario: To verify that user is able to remove donation from cart page.
Given Guest/Registered user is in home page   
When  User searchs for any product and select
Then  User clicks on add to bag button  
And   User clicks on Mini cart from global header                                                
Then  User should be navigated to shopping cart page                                                                           
And   User enters the amount in Amount field and click on Donate button                                                                       
Then  User checks the donation amount added to shopping cart
And   User clicks on remove button in donation section
Then  User verify if donation amount is removed

Scenario: To verify user is able to delete product from "Saved for later" section.
Given Guest/Registered user is in home page   
When  User searchs for any product and select
Then  User clicks on add to bag button  
And   User clicks on Mini cart from global header                                                
Then  User should be navigated to shopping cart page                                                                                                                                                   
And   User selects an item from Saved for later section 
Then  User clicks on Remove button     

Scenario: To verify user is able to delete product from "Saved for later" section.
Given Guest/Registered user is in home page   
When  User searchs for any product and select
Then  User clicks on add to bag button  
And   User clicks on Mini cart from global header                                                
Then  User should be navigated to shopping cart page                                                                                                                                                   
And   User selects an item from Saved for later section 
Then  User clicks on edit link to increase or decrease the quantity.

#
Scenario: Verify the recommendations section
Given Guest/Registered user is in home page 
When searched for any product and item selected
When popup window displayed click oncheckout
Then scroll the page and check the display of recommendation section

Scenario: Verify the registered user is able to add the product to cart
Given User is in home page as a registered user
When searched for product and item selected
Then the product must be added to cart for the registered user

Scenario: Verify the order details in cart
Given Guest/Registered user is in home page   
When searched for any product and item selected
When popup window displayed click oncheckout
Then verify the order total in cart page

Scenario: Verify whether the ship this item and store pick up is displayed in bag page
Given Guest/Registered user is in home page   
When searched for any product and item selected
When popup window displayed click oncheckout
Then ship this item and store pickup should be displayed

Scenario: Verify the giftbox section
Given Guest/Registered user is in home page   
When searched for any product and item selected
When popup window displayed click oncheckout
Then the giftbox should get added properly

Scenario: Verify whether the user navigates to login page when login link is clicked from cart page
Given Guest/Registered user is in home page   
When searched for any product and item selected
When popup window displayed click oncheckout
Then the sign in link should be displayed when clicked should navigate to login page

Scenario: Verify whether the ship this item and store pick up is displayed in bag page
Given Guest/Registered user is in home page  
When searched for any product and item selected
When popup window displayed click oncheckout
Then the sign in link should be displayed when clicked should navigate to login page


Scenario: Verify the giftbox section for any product
Given Guest/Registered user is in home page   
When searched for any product and item selected
When popup window displayed click oncheckout
Then the giftbox should get added properly

Scenario: Verify the user is able to add the same product twice
Given Guest/Registered user is in home page  
When searched for product and item selected
When the user adds multiple product
Then the product should get added properly

Scenario: Verify the application behavior when invalid promo code is applied
Given Guest/Registered user is in home page  
When searched for any product and item selected
When popup window displayed click oncheckout
Then the applied invalid promotions should throw an error message


Scenario: Verify the user is able to add multiple products to bag
Given Guest/Registered user is in home page  
When searched for product and item selected
When the user adds multiple product
Then the product should get added properly
