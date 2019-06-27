package PageObjects;

import java.io.FileNotFoundException;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;

import com.relevantcodes.extentreports.LogStatus;

import Utility.Base;
import Utility.ExtentTestManager;
import Utility.util;

public class ManagerPage extends Base{
	
	public static String changePasswordXpath = "//a[text()='Change Password']";
	public static String oldPasswordXpath = "//input[@name='oldpassword']";
	public static String newPasswordXpath = "//input[@name='newpassword']";
	public static String confirmPasswordXpath = "//input[@name='confirmpassword']";
	public static String submitChangePasswordXpath = "//input[@value='Submit']";
	public static String changePasswordPageHeadingXpath = "//p[@class=\"heading3\"][text()=\"Change Password\"]";
	
    public static String addNewCustomerXpath = "//a[text()='New Customer']";
    public static String customerNameXpath = "//input[@name='name']";
    public static String customerGenderXpath = "//input[@type='radio'][@value='genderXpathVal']";
    public static String customerDOBXpath = "//input[@id='dob']";
    public static String customerAddressXpath = "//textarea[@name='addr']";
    public static String customerCityXpath = "//input[@name='city']";
    public static String customerStateXpath = "//input[@name='state']";
    public static String customerPinNoXpath = "//input[@name='pinno']";
    public static String customerMobileXpath = "//input[@name='telephoneno']";
    public static String customerEmailXpath = "//input[@name='emailid']";
    public static String customerPasswordXpath = "//input[@name='password']";
    public static String customerSubmitXpath = "//input[@value='Submit']";
    
    private static String filePath = util.getConfigValue("TestDataFile");
    private static String sheetName = "Manager";
    
	public static String addedCustomerNameXpath = "//table[@id='customer']/tbody/tr/td[text()='Customer Name']/following-sibling::td";
	public static String addedGenderXpath = "//table[@id='customer']/tbody/tr/td[text()='Gender']/following-sibling::td";
	public static String addedDOBXpath = "//table[@id='customer']/tbody/tr/td[text()='Birthdate']/following-sibling::td";
	public static String addedAddressXpath = "//table[@id='customer']/tbody/tr/td[text()='Address']/following-sibling::td";
	public static String addedCityXpath = "//table[@id='customer']/tbody/tr/td[text()='City']/following-sibling::td";
	public static String addedStateXpath = "//table[@id='customer']/tbody/tr/td[text()='State']/following-sibling::td";
	public static String addedPinXpath = "//table[@id='customer']/tbody/tr/td[text()='Pin']/following-sibling::td";
	public static String addedMobileNoXpath = "//table[@id='customer']/tbody/tr/td[text()='Mobile No.']/following-sibling::td";
	public static String addedEmailXpath = "//table[@id='customer']/tbody/tr/td[text()='Email']/following-sibling::td";
	
	
	
	public static void clickChangePasswordLink() {
		
		getDriver().findElement(By.xpath(changePasswordXpath)).click();
		
	}
	public static void enterOldPassword(String oldPassword){
		
		getDriver().findElement(By.xpath(oldPasswordXpath)).sendKeys(oldPassword);
		
	}
	
	
	public static void enterNewPassword(String newPassword) {
		
		getDriver().findElement(By.xpath(newPasswordXpath)).sendKeys(newPassword);
	}
	
	public static void enterConfirmPassword(String confirmPassword) {
		
		getDriver().findElement(By.xpath(confirmPasswordXpath)).sendKeys(confirmPassword);
	}
	
	public static void clickSubmitForChangePassword() {
		
		getDriver().findElement(By.xpath(submitChangePasswordXpath)).click();
	}

	
	
	public static void clickAddNewCustomer() {
		
		getDriver().findElement(By.xpath(addNewCustomerXpath)).click();
	}
	
	
	public static void enterNewCustomerDetails() {
		try {
			
			getDriver().findElement(By.xpath(customerNameXpath)).sendKeys(RandomStringUtils.random(7,new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m'}));
			
			String genderNewXpath = customerGenderXpath.replace("genderXpathVal", util.getCellData(filePath, sheetName, 1, 1));
			
			getDriver().findElement(By.xpath(genderNewXpath)).click();
			
			System.out.println("Gender xpath: "+genderNewXpath);
			
			ExtentTestManager.getTest().log(LogStatus.INFO, "Selected  gender option."); 
			
			getDriver().findElement(By.xpath(customerDOBXpath)).sendKeys(util.getCellData(filePath, sheetName, 1, 2));
			
			
			
			getDriver().findElement(By.xpath(customerAddressXpath)).sendKeys(util.getRandomAlpaNumericString());
			
			getDriver().findElement(By.xpath(customerCityXpath)).sendKeys(util.getCellData(filePath, sheetName, 1, 4));
			
			getDriver().findElement(By.xpath(customerStateXpath)).sendKeys(util.getCellData(filePath, sheetName, 1, 5));
			
			getDriver().findElement(By.xpath(customerPinNoXpath)).sendKeys(util.getCellData(filePath, sheetName, 1, 6));
			
			getDriver().findElement(By.xpath(customerMobileXpath)).sendKeys(util.getCellData(filePath, sheetName, 1, 7));
			
			getDriver().findElement(By.xpath(customerEmailXpath)).sendKeys(util.getCellData(filePath, sheetName, 1, 8));
			
			getDriver().findElement(By.xpath(customerPasswordXpath)).sendKeys(util.getCellData(filePath, sheetName, 1, 9));
			
			getDriver().findElement(By.xpath(customerSubmitXpath)).click();
			
			
		}catch(FileNotFoundException e) {
			
			ExtentTestManager.getTest().log(LogStatus.ERROR, "File not found exception.");
		}
		catch(Exception e) {
			
			ExtentTestManager.getTest().log(LogStatus.ERROR, e);
		}	
		
		
		
		
	}
	
	
	public static boolean verifyCustomerDetails() {
		
		boolean status = true;
		int count = 0;
		
	  try {	
		
		if(!util.getText(addedCustomerNameXpath).equals(util.getCellData(filePath, sheetName, 1, 0))) {
			
			count++;
			
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Customer name is incorrect.");
			
		}else {
			
			ExtentTestManager.getTest().log(LogStatus.PASS, "Customer name is correct.");
			
		}
		
        if(!util.getText(addedGenderXpath).contains(util.getCellData(filePath, sheetName, 1, 1))) {
			
			count++;
			
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Gender is incorrect.");
			
		}else {
			
			ExtentTestManager.getTest().log(LogStatus.PASS, "Gender is correct.");
			
		}
        
        if(!util.getText(addedDOBXpath).contains(util.getCellData(filePath, sheetName, 1, 2))) {
			
			count++;
			
			ExtentTestManager.getTest().log(LogStatus.ERROR, "DOB is incorrect.");
			
		}else {
			
			ExtentTestManager.getTest().log(LogStatus.PASS, "DOB is correct.");
			
		}


        if(!util.getText(addedAddressXpath).contains(util.getCellData(filePath, sheetName, 1, 3))) {
	
	        count++;
	        
	        ExtentTestManager.getTest().log(LogStatus.ERROR, "Address is incorrect.");
	
         }else {
	
	       ExtentTestManager.getTest().log(LogStatus.PASS, "Address is correct.");
	
        }
        
        if(!util.getText(addedCityXpath).contains(util.getCellData(filePath, sheetName, 1, 4))) {
        	
	        count++;
	        
	        ExtentTestManager.getTest().log(LogStatus.ERROR, "City is incorrect.");
	
         }else {
	
	       ExtentTestManager.getTest().log(LogStatus.PASS, "City is correct.");
	
        }
         
        
        
        if(!util.getText(addedStateXpath).contains(util.getCellData(filePath, sheetName, 1, 5))) {
        	
	        count++;
	        
	        ExtentTestManager.getTest().log(LogStatus.ERROR, "State is incorrect.");
	
         }else {
	
	       ExtentTestManager.getTest().log(LogStatus.PASS, "State is correct.");
	
        }
        
        
        if(!util.getText(addedPinXpath).contains(util.getCellData(filePath, sheetName, 1, 6))) {
        	
	        count++;
	        
	        ExtentTestManager.getTest().log(LogStatus.ERROR, "Pin is incorrect.");
	
         }else {
	
	       ExtentTestManager.getTest().log(LogStatus.PASS, "Pin is correct.");
	
        }
        
        
        if(!util.getText(addedMobileNoXpath).contains(util.getCellData(filePath, sheetName, 1, 7))) {
        	
	        count++;
	        
	        ExtentTestManager.getTest().log(LogStatus.ERROR, "Mobile number is incorrect.");
	
         }else {
	
	       ExtentTestManager.getTest().log(LogStatus.PASS, "Mobile number is correct.");
	
        }
        
        
        if(!util.getText(addedEmailXpath).contains(util.getCellData(filePath, sheetName, 1, 8))) {
        	
	        count++;
	        
	        ExtentTestManager.getTest().log(LogStatus.ERROR, "Email is incorrect.");
	
         }else {
	
	       ExtentTestManager.getTest().log(LogStatus.PASS, "Email is correct.");
	
        }
        
        System.out.println("count value is: "+count);
        
        if(count>0) {
        	
        	status = false;
        	ExtentTestManager.getTest().log(LogStatus.FAIL, "Customers details are not matching.");
        	
        }
		
	  }catch(FileNotFoundException e) {
			
			ExtentTestManager.getTest().log(LogStatus.ERROR, "File not found exception.");
		}
		catch(Exception e) {
			
			ExtentTestManager.getTest().log(LogStatus.ERROR, e);
		}	
		
	  return status;
		
	}
	
	
	
}
