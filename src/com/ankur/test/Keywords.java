package com.ankur.test;

import static com.ankur.test.DriverScript.APP_LOGS;
import static com.ankur.test.DriverScript.CONFIG;
import static com.ankur.test.DriverScript.OR;
import static com.ankur.test.DriverScript.currentTestCaseName;
import static com.ankur.test.DriverScript.currentTestDataSetID;
import static com.ankur.test.DriverScript.currentTestSuiteXLS;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Keywords{
	
	public WebDriver driver;

	
	public  String openBrowser(String object, String data){		
		APP_LOGS.debug("Opening browser");
		if(data.equals("Mozilla"))
		driver = new FirefoxDriver();
		else if (data.equals("IE"))
			driver = new InternetExplorerDriver();
		else if (data.equals("Chrome"))
			driver = new ChromeDriver();
		
		System.out.println(object);
		System.out.println(data);
		
		return Constants.KEYWORD_PASS;
	}
	
	public  String navigate(String object, String data){		
		APP_LOGS.debug("Navigating to URL");
		try{
		driver.navigate().to(data);
		}
		catch(Exception e){
			return Constants.FAIL+"----- Not able to navigate----"+e.getMessage();
			
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	
	public  String clickLink(String object, String data){
        APP_LOGS.debug("Clicking on link ");
        try{
        driver.findElement(By.xpath(OR.getProperty(object))).click();
        }
        catch(Exception e){
			return Constants.FAIL+"----- Not able to click on link----"+e.getMessage();
			
		}
		return Constants.KEYWORD_PASS;
	}
	
	public  String verifyLinkText(String object, String data){
		APP_LOGS.debug("Verifying Link Text ");
       try{
    	   
    	   String actual= driver.findElement(By.xpath(OR.getProperty(object))).getText();
    	   String expected = data;
    	   
    	   if(actual.equals(expected))
    		   return Constants.KEYWORD_PASS;
    	   else
    		   return Constants.KEYWORD_FAIL +"----- Text not get verified----";
       }catch(Exception e){
			return Constants.FAIL+"----- Text not get verified----"+e.getMessage();
			
		}
       
	

        
	}
	
	
	public  String clickButton(String object, String Data){
        APP_LOGS.debug("Clicking on Button");
 
        try{
        driver.findElement(By.xpath(OR.getProperty(object))).click();
        }
        catch(Exception e){
			return Constants.FAIL+"----- Not able to click on button----"+e.getMessage();
			
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	public  String verifyButtonText(String object, String data){
		APP_LOGS.debug("Verifying the button text");
		
	   String actual= driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
 	   String expected = data;
 	   try{
 	   if(actual.equals(expected))
 		   return Constants.KEYWORD_PASS;
 	   else
 		   return Constants.KEYWORD_FAIL +"----- Button Text not get verified----"+"Expected:"+expected+"Actual:"+actual;
 	   }
 	   catch( Exception e)
 	   {
 		  return Constants.KEYWORD_FAIL +"----- Object not found---"+e.getMessage();   
 	   }
	}
	
	public  String selectList(String object, String data){
		APP_LOGS.debug("Selecting from list");
		try{
			if(!data.equals("random")){
			  driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			}else{
				System.out.println("inside random");
				// logic to find a random value in list
				WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
				List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
				Random num = new Random();
				int index=num.nextInt(droplist_cotents.size());
				System.out.println("Index value"+index);
				String selectedVal=droplist_cotents.get(index).getText();
				
			  driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(selectedVal);
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

		}
		
		return Constants.KEYWORD_PASS;	
	}
	
	public  String verifyListSelection(){
		APP_LOGS.debug("Verifying the selection of the list");
		
		return Constants.KEYWORD_PASS;	
	}
	
	public  String verifyAllListElements(){
		APP_LOGS.debug("Verifying all the list elements");
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String selectRadio(){
		APP_LOGS.debug("Selecting a radio button");
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String verifyRadioSelected(){
		APP_LOGS.debug("Verify Radio Selected");
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String verifyCheckBoxSelected(){
		APP_LOGS.debug("Verifying checkbox selected");
		
		return Constants.KEYWORD_PASS;
		
	}
	
	
	public  String verifyText(String object, String data){
		APP_LOGS.debug("Verifying the text");
		
		 String actual= driver.findElement(By.xpath(OR.getProperty(object))).getText();
	 	 String expected = data;
	 	 System.out.println("Actual length"+actual.length());
	 	 System.out.println("Expected Length"+expected.length());
	 	  
	 	   try{
	 	   if(actual.equals(expected) || actual==expected)
	 		   return Constants.KEYWORD_PASS;
	 	   else
	 		   return Constants.KEYWORD_FAIL +"----- Text not get verified----"+"Expected:"+expected+"Actual:"+actual;
	 	   }
	 	   catch( Exception e)
	 	   {
	 		  return Constants.KEYWORD_FAIL +"----- Object not found---"+e.getMessage();   
	 	   }
	 	   
		
	}
	
	public  String writeInInput(String object, String data){
		APP_LOGS.debug("Writing in text box");
		APP_LOGS.debug(object);
		APP_LOGS.debug(data);
		
		try{
			
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		}
		catch(Exception e){
			
			return Constants.FAIL+"---- Unable to write" +e.getMessage();
		}
		
		return Constants.KEYWORD_PASS;
		
	}
	
	public  String verifyTextinInput(String object, String data){
       APP_LOGS.debug("Verifying the text in input box");
		try{
			
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			String expected = data;
			if(actual.equals(expected)){
				
				return Constants.PASS;
				
			}else{
				return Constants.FAIL+"----not matching";
			}
		}
		catch (Exception e){
			return Constants.FAIL+"----Unable to find element"+e.getMessage();
		}
		
	}
	
	public  String clickImage(){
	       APP_LOGS.debug("Clicking the image");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String verifyFileName(){
	       APP_LOGS.debug("Verifying inage filename");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String pause(){
	       APP_LOGS.debug("Waiting");
			
			return Constants.KEYWORD_PASS;
	}
	
	
	public  String store(){
	       APP_LOGS.debug("Storing value");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String verifyTitle(){
	       APP_LOGS.debug("Verifying title");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String exist(){
	       APP_LOGS.debug("Checking existance of element");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String click(){
	       APP_LOGS.debug("Clicking on any element");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String synchronize(){
		APP_LOGS.debug("Waiting for page to load");
		
		return Constants.KEYWORD_PASS;
	}
	
	public  String waitForElementVisibility(){
		APP_LOGS.debug("Waiting for an elelement to be visible");
		
		return Constants.KEYWORD_PASS;
	}
	
	public  String closeBrowser(String object, String data){
		APP_LOGS.debug("Closing the browser");
		
		try{
			driver.quit();
		}catch (Exception e) {
			return Constants.FAIL+"unable to close browser"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}
	
	
	/************************APPLICATION SPECIFIC KEYWORDS********************************/
	
	public  String verifyLaptops(){
        APP_LOGS.debug("Verifying the laptops in app");
		
		return Constants.KEYWORD_PASS;
	}
	
	
	public String validateLogin(String object, String data) throws InterruptedException{
		
		APP_LOGS.debug("Validating the Login");
		
		String data_flag=currentTestSuiteXLS.getCellData(currentTestCaseName, "Data_Correctness",currentTestDataSetID );
		
		System.out.println("Data correctness value"+ data_flag);
		int size1;
		
		System.out.println(object);
		System.out.println(data);
		
		Thread.sleep(1000);
		if(data_flag.equals("N"))	{
			
			System.out.println(object);
			System.out.println(OR.getProperty(object));
		size1=  driver.findElements(By.xpath(OR.getProperty(object))).size();
		System.out.println("Size is" +size1);
		}
		else
			size1=0;

		
		if(size1!=0){
			if(data_flag.equals("Y"))		{			// Not Able to login with correct data
				System.out.println("Reached at 1");
				return Constants.FAIL; }
			else								// Not able to login with incorrect data
			{
				System.out.println("Reached at 2");
				return verifyText(object,data);
			}
		}
		else{
			if(data_flag.equals("Y"))	{					// Able to login with correct data
				System.out.println("Reached at 3");
				return Constants.PASS;
				
			}
			else {
				System.out.println("Reached at 4");
				return Constants.FAIL;				// Able to login with incorrect data
				
			}
		}
			
		
		
	}
	
	
	
	
	
	
	
	
}
