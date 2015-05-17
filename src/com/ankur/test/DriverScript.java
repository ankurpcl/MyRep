package com.ankur.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ankur.xls.read.Xls_Reader;

public class DriverScript {
	
	public static Logger APP_LOGS;
	
	//suite.ls
	public Xls_Reader suiteXLS;
	public int currentSuiteID;
	public String currentTestSuite;
	
	// single test suite
	public static Xls_Reader currentTestSuiteXLS;
	public int currentTestCaseID;
	public static String currentTestCaseName;
	
	public static int currentTestStepID;
	
	public static String currentKeyword;
	public static int currentTestDataSetID;
	public static Method method[];
	public static Keywords keywords;
	public static String keyword_execution_result;
	public static ArrayList<String> resultSet;
	public static String data;
	public static String object;
	
	// properties
	public static Properties CONFIG;
	public static Properties OR;
	
	public DriverScript(){
		
		keywords = new Keywords();
		method = keywords.getClass().getMethods();
		
	}
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		// TODO Auto-generated method stub
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\ankur\\config\\CONFIG.properties");
		CONFIG = new Properties();
		CONFIG.load(fs);
		
		fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\ankur\\config\\OR.properties");
		OR = new Properties();
		OR.load(fs);
	
		DriverScript test =  new DriverScript();
		test.start();	
	}
	
	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Properties loaded, starting testing");
		// check run mode of test suite
		// check run mode of test case in test suite
		// Execute keywords serially
		// Execute keywords as many times as test data set to Y
		
		suiteXLS = new Xls_Reader(System.getProperty("user.dir")+"//src//com//ankur//xls//Suite.xlsx");
		
		for(currentSuiteID=2;currentSuiteID<=suiteXLS.getRowCount("Test Suite");currentSuiteID++){
			
		//	System.out.println(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.TEST_SUITE_ID, currentSuiteID)+"----"+
		//	suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentSuiteID));
		
			String runmode = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentSuiteID);
			currentTestSuite = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.TEST_SUITE_ID, currentSuiteID);
			
			if(runmode.equals(Constants.RUNMODE_YES)){
				
				// executing test suite from total available Test Suite
				APP_LOGS.debug("*************Executing Test Suite*****************" + suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.TEST_SUITE_ID, currentSuiteID));
				
				 currentTestSuiteXLS = new Xls_Reader(System.getProperty("user.dir")+"//src//com//ankur//xls//"+currentTestSuite+".xlsx");

				 // iterating through all test cases in test sheet
				 for(currentTestCaseID=2;currentTestCaseID<=currentTestSuiteXLS.getRowCount("Test Cases");currentTestCaseID++){
					 
					 currentTestCaseName = currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TEST_CASE_ID, currentTestCaseID);
			//		 APP_LOGS.debug(currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TEST_CASE_ID, currentTestCaseID) +"---"+
				//			 currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID));
	
					 if( currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID).equals(Constants.RUNMODE_YES)){

						 APP_LOGS.debug("*****Executing Test Case*******"+ currentTestCaseName);
						
						 if(currentTestSuiteXLS.isSheetExist(currentTestCaseName)) {
						 // Run as many times as number of test data sets with runmode Yes
						 
						 for(currentTestDataSetID=2;currentTestDataSetID<=currentTestSuiteXLS.getRowCount(currentTestCaseName);currentTestDataSetID++)					
						 {
							 resultSet = new ArrayList<String>();
							 //Checking the run mode for current data set
							if(currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.RUNMODE,currentTestDataSetID).equals(Constants.RUNMODE_YES))
							{
							 APP_LOGS.debug("**********Iteration Number********** "+ (currentTestDataSetID-1));
							 
							 // iterate through all keywords
							 executeKeywords();
							
							}
							 createXLSReport();
						 }
					 }
						 else {
							 //simply execute keywords
							 resultSet= new ArrayList<String>();
							 currentTestDataSetID=2;
							 executeKeywords();
							 createXLSReport();
							 
							 }
						 } 							 
					 }
					}
				 
				 }
			}
			
	public void executeKeywords() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		for(currentTestStepID=2;currentTestStepID<=currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);currentTestStepID++)
		 {
			data = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.DATA, currentTestStepID);
			object = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.OBJECT, currentTestStepID);
			
			if(data.startsWith("Col")){
				// read actual data from corresponding column of test case data sheet
				data=currentTestSuiteXLS.getCellData(currentTestCaseName,data.split(Constants.SPLIT)[1],currentTestDataSetID);
			}
			else if(data.startsWith("Config")){
				// read data from Config.properties
				data=CONFIG.getProperty(data.split(Constants.SPLIT)[1]);
			}
			else{
				// read data from OR.properties
				data=OR.getProperty(data);
			}
			if(object.startsWith("Col")){
				
				object=currentTestSuiteXLS.getCellData(currentTestCaseName,object.split(Constants.SPLIT)[1],currentTestDataSetID);
			}
			
			 //checking TCID
			 if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TEST_CASE_ID, currentTestStepID)))
			 {	currentKeyword = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.KEYWORD, currentTestStepID);
			APP_LOGS.debug(currentKeyword);
			// code to execute keywords as well

			for(int i =0; i<method.length;i++)
			{
			//	System.out.println(method[i].getName());

			if(method[i].getName().equals(currentKeyword))
			{
				keyword_execution_result= (String)method[i].invoke(keywords,object,data);
				APP_LOGS.debug(keyword_execution_result);
				resultSet.add(keyword_execution_result);
			}
			}
	
			 
			 } 
		
	}
		}
	
	
	public void createXLSReport(){
		
		String colName=Constants.RESULT +(currentTestDataSetID-1);
		boolean isColExist=false;
		
		for(int c=0;c<currentTestSuiteXLS.getColumnCount(Constants.TEST_STEPS_SHEET);c++){
			
			if(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET,c , 1).equals(colName)){
				isColExist=true;
				break;
			}
		}
		
		if(!isColExist)
			currentTestSuiteXLS.addColumn(Constants.TEST_STEPS_SHEET, colName);
		int index=0;
		for(int i=2;i<=currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);i++){
			
			if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TEST_CASE_ID, i))){
				if(resultSet.size()==0)
					currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, Constants.SKIP);
				else	
					currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, resultSet.get(index));
				index++;
			}

		}
		
		
		if(resultSet.size()==0){
			//skip
			currentTestSuiteXLS.setCellData(currentTestCaseName,Constants.RESULT,currentTestDataSetID,Constants.SKIP);
			return;
			
		} else{
			
			for(int i =0; i<resultSet.size();i++){
				
				if(!resultSet.get(i).equals(Constants.KEYWORD_PASS)){
					
					currentTestSuiteXLS.setCellData(currentTestCaseName,Constants.RESULT,currentTestDataSetID,resultSet.get(i));
					return;
				}
			}
		}
		
		currentTestSuiteXLS.setCellData(currentTestCaseName,Constants.RESULT,currentTestDataSetID,Constants.PASS);
	}
	
}
		
	



