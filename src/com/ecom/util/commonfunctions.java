package com.ecom.util;
import com.ecom.base.ScriptHelper;
import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.ITestResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import static com.ecom.base.BaseFactory.*;


public class commonfunctions {

        public static ExpectedCondition<Boolean> angularHasFinishedProcessing() {
            return new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (driver instanceof JavascriptExecutor){
                        JavascriptExecutor je = (JavascriptExecutor) driver;
                        je.executeScript("window.alert = function(){};");
                        je.executeScript("window.confirm = function(){return true;};");
                    }
                    return Boolean.valueOf(((JavascriptExecutor) driver).executeScript("return (window.angular !== undefined) && " +
                            "(angular.element(document).injector() !== undefined) && " +
                            "(angular.element(document).injector().get('$http').pendingRequests.length === 0)").toString());
                }
            };
        }

        public static ExpectedCondition<Boolean> pageLoadFinished() {
            return new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (driver instanceof JavascriptExecutor){
                        JavascriptExecutor je = (JavascriptExecutor) driver;
                        je.executeScript("window.alert = function(){};");
                        je.executeScript("window.confirm = function(){return true;};");
                    }
                    return Boolean.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
                }
            };
        }


        public static ExpectedCondition<Boolean> isJqueryCallDone() {
            return new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if(!(Boolean)((JavascriptExecutor) driver).executeScript("return jQuery.active==0")){
                        System.out.println("Ajax call is in Progress....");
                    }
                    return (Boolean)((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
                }
            };
        }

    public static void generateAndSendEmail() throws MessagingException, IOException {

        String emailServer = com.ecom.util.LoadConfigFile.getValue("smtpserver");
        String emailTo = com.ecom.util.LoadConfigFile.getValue("SendTo");
        String emailCC = com.ecom.util.LoadConfigFile.getValue("SentCC");
        String emailFrom = com.ecom.util.LoadConfigFile.getValue("SendFrom");
        String emailPort = com.ecom.util.LoadConfigFile.getValue("port");
        String sendEmail1 =com.ecom.util.LoadConfigFile.getValue("SentCC1");
        String env = com.ecom.util.LoadConfigFile.getValue("Environment");

        // Recipient's email ID needs to be mentioned.
        String to = emailTo;

        // Sender's email ID needs to be mentioned
        String from = emailFrom;

        // Assuming you are sending email from localhost
        String host = emailServer;

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        boolean status = true;
        String stat  = "PASS";
        try {

            InternetAddress fromAddress = new InternetAddress(from);
            InternetAddress toAddress = new InternetAddress(to);
            InternetAddress ccAddress = new InternetAddress(emailCC);
            InternetAddress cc1Address = new InternetAddress(sendEmail1);

            String report = reportFileName;



            String bodyTextFailureResult = "";
            String bodyTextFooter =  "For more detailed report please follow the below location.\n\n" + "Result " +
                    "location: " + report + "\n\n\n\n\n" + "Regards,\n" + "Automation Team.\n\n\n\n\n\n";

            StringBuilder failuredescription = new StringBuilder();

            if(FailedNetworkLinks.size() > 0 ){
                failuredescription.append("The Failed URL's, which are due to Error Message as 'Network Error' : \n");
                for(int i=0;i<FailedNetworkLinks.size();i++){
                    int y = 0;
                    y = i +1 ;
                    failuredescription.append("URL - "+ y +" :  " + FailedNetworkLinks.get(i).trim()+ " \n");
                }
                status = false;
            }

            if(FailedUnknownLinks.size() > 0 ){
                failuredescription.append("\n\nThe Failed URL's, which are due to Error Message as 'Unknown Error' :  \n");
                for(int i=0;i<FailedUnknownLinks.size();i++){
                    int y = 0;
                    y = i +1 ;
                    failuredescription.append("URL - "+ y +" :  " + FailedUnknownLinks.get(i).trim()+ " \n");
                }
                status = false;
            }

            if(FailedProductNoPresentLinks.size() > 0 ){
                failuredescription.append("The Failed URL's, which are due to Error Message as 'Please try again or browse our popular products below Or Product Unavailable' : \n");
                for(int i=0;i<FailedProductNoPresentLinks.size();i++){
                    int y = 0;
                    y = i +1 ;
                    failuredescription.append("URL - "+ y +" :  " + FailedProductNoPresentLinks.get(i).trim()+ " \n");
                }
                status = false;
            }

            if(FailedNomatchLinks.size() > 0 ){
                failuredescription.append("\n\nThe Failed URL's which are failed due to error Message as 'No Match Found' , which is general error :\n");
                for(int i=0;i<FailedNomatchLinks.size();i++){
                    int y = 0;
                    y = i +1 ;
                    failuredescription.append("URL - "+y +" :  " + FailedNomatchLinks.get(i).trim()+ " \n");
                }
                status = false;
            }
            bodyTextFailureResult = failuredescription.toString();

            String bodyText = "Hi All,\n\n\n " + " ' NIT - URL Verification ' Test Automation Execution Completed. The report is attached as file along with this email."
                    + bodyTextFailureResult +  bodyTextFooter;


            System.out.println(bodyText);
            // Create an Internet mail msg.
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(fromAddress);
            msg.setRecipient(Message.RecipientType.TO, ccAddress);
            msg.setRecipient(Message.RecipientType.CC, cc1Address);
            msg.setRecipient(Message.RecipientType.BCC, toAddress);
            if( ! ScriptHelper.getTest().getStatus().equals(ITestResult.SUCCESS )){
                if(! status ){
                    stat = "FAILED";
                }
            }

            msg.setSubject("Sanity Suite Completed  on - [" + env + "] with Status ["+stat+"]");
            msg.setSentDate(new Date());

            // Set the email msg text.
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(bodyText);

            // Set the email attachment file
            FileDataSource fileDataSource = new FileDataSource(report);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
            attachmentPart.setFileName(fileDataSource.getName());

            // Create Multipart E-Mail.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            multipart.addBodyPart(attachmentPart);


            msg.setContent(multipart);
            Transport.send(msg);


      /*  try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.CC, emailTo);
            message.setRecipients(Message.RecipientType.TO, to);

            // Set Subject: header field
            message.setSubject(" Sanity Suite Completed  on - "+ env );

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            String report = reportFileName;

            // Fill the message
            messageBodyPart.setText("Hi All,\n\n\n " + " ' NIT - URL Verification ' Test Automation Execution Completed. For more detailed report please follow the below location.\n\n" + "Result " +
                    "location: " + report + "\n\n\n\n\n" + "Regards,\n" + "Automation Team.\n\n\n\n\n\n");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = reportFileName;
            DataSource source = new FileDataSource(filename);
            FileDataSource fileDataSource = new FileDataSource(filename);

            messageBodyPart.setDataHandler(new DataHandler(source));

            multipart.addBodyPart(messageBodyPart);
            addAttachment(multipart, filename);
            //message.setContent(fileDataSource.getName(), "text/html");

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);*/


        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}

    private static void addAttachment(Multipart multipart, String filename) throws MessagingException {
        DataSource source = new FileDataSource(filename);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
    }

    public static void createFile(String fileContent, String filename) throws IOException {


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fos.write(fileContent.getBytes());
        fos.flush();
        fos.close();
    }

    public static void verifyCurrentURLRequest(String expectedResults,String PageName ){
            String currentURL = ScriptHelper.getDriver().getCurrentUrl();

        if(currentURL.toLowerCase().contains(expectedResults))
        {
            reportVP("PASS", "The current URL ["+currentURL+"] contains the value ["+ expectedResults+"] " +
                    "for the Page ["+PageName+"]" );
        }
        else
        {
            reportVP("FAIL", "The current URL ["+currentURL+"] doesn't contains the value <br>["+ expectedResults+"] </br>" +
                    "for the Page <br>["+PageName+"] </br>" );
        }
    }

    public static void verifyPageContentForText(String textToSearch,String pageName){
        String currentPage = ScriptHelper.getDriver().getPageSource().toLowerCase();

        if(currentPage.contains(textToSearch.toLowerCase()))
        {
            reportVP("PASS", "The ["+pageName+"] Page contains text ["+textToSearch+"] in the page." );
        }
        else
        {
            reportVP("FAIL", "The ["+pageName+"] Page <br> doesn't contains text ["+textToSearch+"] </br>in the page." );
        }
    }
    
   

    public static void writeInPropertyFile(String key, String value, String filename){

        try (OutputStream output = new FileOutputStream(filename)) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty(key, value);
                        // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    public static HashMap<String,String> generateRandomAddress(String countryType){
    
    	       HashMap<String,String> address = new HashMap(5);
    	       Faker faker;
    	       String zipcode = null;
    	       String state = null;
    	       if(countryType.equalsIgnoreCase("US")){
    	       	faker = new Faker(new Locale("en-US"));
    	        address.put("fulladdress",faker.address().fullAddress());
    	            zipcode = faker.address().zipCodeByState("CA");
    	            state = "CALIFORNIA";
    	       } else {
    	       	faker = new Faker(new Locale("en_CA"));
    	        address.put("fulladdress",faker.address().fullAddress());
    	       	zipcode = "T4U 4P3";
    	       	state = "ALBERTA";
    	       }
    	       
    	       String fName = faker.address().firstName();
    	       String lName = faker.address().lastName();
    	       String streetName = faker.address().streetName();
    	       String number = faker.address().buildingNumber();
    	       String city = faker.address().cityName();
    	       
    	       
    	       address.put("Street",streetName);
    	       address.put("building",number);
    	       address.put("city",city);
    	       address.put("zipcode",zipcode);
    	       address.put("state",state);
    	       address.put("FirstName",fName);
    	       address.put("LastName",lName);

    	       return address;
    	 
    }
    
    public static HashMap<String,String> getSearchedProduct(){
    	HashMap pd = new HashMap<String,String>();
    	
    	
    	
    	return pd;
    }


}

