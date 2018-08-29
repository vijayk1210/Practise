package TestCases;

import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import PageObjects.HomePage;
import PageObjects.ManagerPage;
import Utility.Base;
import Utility.ExtentTestManager;
import Utility.util;

public class ChangePasswordTest extends Base {
	
	String expectedAlertTextForIncorrectOldPassword = "Old Password is incorrect";
	String incorrectOldPassword = "abcd12345";
	String oldPassword = "test@123Test";
	String newPassword = "testing@1234";
	String confirmPassword = "testing@1234";
	String actualAlertTextForIncorrectOldPassword = null;
	String actualAlertTextForPasswordChangeSuccess = null;
	String expectedAlertTextForPasswordChangeSuccess = "Password is Changed";
	String expectedTextPasswordPageHeading = "Change Password";
	String actualTextPasswordPageHeading = null;
	boolean status = false;
	String expectedTextForLogin = "Manger Id : mngr124806";
	String actualTextForLogin = null;
	
	
  @Test
  public void verifyChangePassword_IncorrectOldPassword_SM1() {
	
 
	try {  
		
	  HomePage.login(util.getCellData("TestData.xlsx", "LoginData", 1, 2),oldPassword);
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Logged in successfully.");
		
	  ManagerPage.clickChangePasswordLink();
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Clicked on change password link.");
	  
	  ManagerPage.enterOldPassword(incorrectOldPassword);
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Entered old password.");
	  
	  ManagerPage.enterNewPassword(newPassword);
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Entered new password.");
	  
	  ManagerPage.enterConfirmPassword(confirmPassword);
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Entered confirm password.");
	  
	  ManagerPage.clickSubmitForChangePassword();
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Clicked on submit");
	  
	  
	  actualAlertTextForIncorrectOldPassword = util.getAlertText();
	  
	  Assert.assertEquals(actualAlertTextForIncorrectOldPassword, expectedAlertTextForIncorrectOldPassword, "Incorrect Old password feature is not working");
	  
	  util.alertHandles("accept"); 	
	  
	  actualTextPasswordPageHeading =  util.getText(ManagerPage.changePasswordPageHeadingXpath);
	  
	  Assert.assertEquals(actualTextPasswordPageHeading, expectedTextPasswordPageHeading, "Page is not navigating back to change password page.");
	  
	  ExtentTestManager.getTest().log(LogStatus.PASS,"Page is navigating back to change password page." );
	  
	}catch(FileNotFoundException e) {
		
		ExtentTestManager.getTest().log(LogStatus.ERROR,"TestData file is either missing" );
		
	}
	
	catch(AssertionError e) {		
		
		String path1 = util.CaptureScreenShotWithTestStepNameUsingRobotClass("verifyChangePassword_IncorrectOldPassword_SM1");
        
        String image1 = ExtentTestManager.getTest().addScreenCapture(path1);
      
        ExtentTestManager.getTest().log(LogStatus.FAIL,image1);
		
        ExtentTestManager.getTest().log(LogStatus.ERROR,"Assertion error occurred: Text displayed on popup is incorrect." );
		
		util.alertHandles("accept"); 	
		  
		actualTextPasswordPageHeading =  util.getText(ManagerPage.changePasswordPageHeadingXpath);
		  
		Assert.assertEquals(actualTextPasswordPageHeading, expectedTextPasswordPageHeading, "Page is not navigating back to change password page.");
		  
		ExtentTestManager.getTest().log(LogStatus.PASS,"Page is navigating back to change password page." );
		 
	    Assert.fail("Test failed");
		
		 
	}
	
	catch(Exception e) {
		ExtentTestManager.getTest().log(LogStatus.ERROR,"Some Exception occurred" );
	}
	  
  }
  
  
  
  @Test (dependsOnMethods = {"verifyChangePassword_IncorrectOldPassword_SM1"},enabled = true)
  public void verifyChangePassword_WithCorrectOldPassword_SM2() {
	  
	  try {
		  HomePage.login(util.getCellData("TestData.xlsx", "LoginData", 1, 2),oldPassword);
		  
		  ExtentTestManager.getTest().log(LogStatus.INFO, "Logged in successfully.");
			
		  ManagerPage.clickChangePasswordLink();
		  
		  ExtentTestManager.getTest().log(LogStatus.INFO, "Clicked on change password link.");
		  
		  ManagerPage.enterOldPassword(oldPassword);
		  
		  ExtentTestManager.getTest().log(LogStatus.INFO, "Entered old password.");
		  
		  ManagerPage.enterNewPassword(newPassword);
		  
		  ExtentTestManager.getTest().log(LogStatus.INFO, "Entered new password.");
		  
		  ManagerPage.enterConfirmPassword(confirmPassword);
		  
		  ExtentTestManager.getTest().log(LogStatus.INFO, "Entered confirm password.");
		  
		  ManagerPage.clickSubmitForChangePassword();
		  
		  ExtentTestManager.getTest().log(LogStatus.INFO, "Clicked on submit");
		  
		  actualAlertTextForPasswordChangeSuccess = util.getAlertText();
		  
		  Assert.assertEquals(actualAlertTextForPasswordChangeSuccess, expectedAlertTextForPasswordChangeSuccess, "Password change feature is not working."); 
		  
		  
		  ExtentTestManager.getTest().log(LogStatus.PASS,"Password change feature is working fine." );
		 
		  
	    }catch(FileNotFoundException e) {
			
			ExtentTestManager.getTest().log(LogStatus.ERROR,"TestData file is missing." );
			
		}
		
		catch(Exception e) {
			
			ExtentTestManager.getTest().log(LogStatus.ERROR,"Exception occurred" );
		}  
	  
	  
  }
  
  
  
  @Test (dependsOnMethods = {"verifyChangePassword_WithCorrectOldPassword"})
  public void verifyLoginFromNewPassword_SM3() {
	  
	  try {
		  
          HomePage.login(util.getCellData("TestData.xlsx", "LoginData", 1, 2), newPassword );
          
          actualTextForLogin = util.getText(HomePage.userIdHomePageXpath);
          
          Assert.assertEquals(actualTextForLogin, expectedTextForLogin, "Login not working with new password.");
		  
		  ExtentTestManager.getTest().log(LogStatus.INFO, "Logged in successfully with new password.");
			
		  
		  
	  }catch(Exception e) {
		  
		  
		  ExtentTestManager.getTest().log(LogStatus.ERROR,"Exception occurred" );
		  
		  
	  }
	  
	  
	  
	  
	  
	  
	  
	  
  }
}
