package TestCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import PageObjects.HomePage;
import Utility.Base;
import Utility.ExtentTestManager;
import Utility.util;

public class LoginTest {
	
	String expectedTextForLogin = "Manger Id : mngr124806";
	String expectedTextForInvalidData = "User or Password is not valid";
	String actualText = null;
	
	
	
  @DataProvider(name = "Authentication")
  public static Object[][] testData(){
	  Object[][] loginData = null;
	  
	  loginData = util.get2DExcelData("TestData.xlsx", "LoginData");
	  
	  return loginData;
	  
  }
	
  @Test(dataProvider = "Authentication")
  public void verifyLogin(String Scenario,String testDescription, String UserName,String Password) {
	  
	  try {
		  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Starting "+Scenario+" scenario.");
		  
	  HomePage.enterUserID(UserName);
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Entered UserId: "+UserName); 
	  
	  HomePage.enterPassword(Password);
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Entered password");
	  
	  HomePage.clickLogin();
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Clicked on login button");
	  
	  
	  
	  if(Scenario.equalsIgnoreCase("valid")) {
		 
		  actualText = util.getText(HomePage.userIdHomePageXpath);
		  
		  if(actualText.equals(expectedTextForLogin)) {
			  ExtentTestManager.getTest().log(LogStatus.PASS, "Login is working with valid credentials");  
		  }else {
			  ExtentTestManager.getTest().log(LogStatus.FAIL, "Login is not working with valid credentials");
		  }
		  
	  }else {
		  
		  actualText = util.getAlertText();
		  
		  if(actualText.equals(expectedTextForInvalidData)) {
			  ExtentTestManager.getTest().log(LogStatus.PASS, "Login is not working with invalid credentials");  
		  }else {
			  ExtentTestManager.getTest().log(LogStatus.FAIL, "Login is working with invalid credentials");
		  }
		    
		  
	  }
	  
	 
	  }catch(Exception e) {
		e.printStackTrace();
	  }
	  
  }
}
