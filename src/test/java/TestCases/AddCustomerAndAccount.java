package TestCases;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import PageObjects.HomePage;
import PageObjects.ManagerPage;
import Utility.Base;
import Utility.ExtentTestManager;
import Utility.util;

public class AddCustomerAndAccount extends Base {
	
	
  @Test
  public void VerifyAddingNewCustomer_SM4() {
	  try {
	  
	  boolean status = true;
	  
	  HomePage.login(util.getCellData("TestData.xlsx", "LoginData", 1, 2),util.getCellData("TestData.xlsx", "LoginData", 1, 3));
	  
	  ManagerPage.clickAddNewCustomer();
	  
	  ManagerPage.enterNewCustomerDetails();
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Submitted new customer details.");  
	  
	  status = ManagerPage.verifyCustomerDetails();
	  
	  Assert.assertTrue(status, "Customers details are not matching.");
	  
	  }catch(FileNotFoundException e) {
			
			ExtentTestManager.getTest().log(LogStatus.ERROR, "File not found exception.");
	  }
	   catch(Exception e) {
			
			ExtentTestManager.getTest().log(LogStatus.ERROR, e);
	  }
	  
	  
	  
  }
}
