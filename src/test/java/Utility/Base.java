package Utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Base {
    public static ExtentReports extent;
    public static ExtentTest test;
    public static WebDriver driver;
    public String browser = util.getConfigValue("browser");
   
   
    private void setDriver(String browserType, String appURL) {
        switch(browserType){
     	  case "chrome":
     		System.out.println("Inside set driver");
     		launchChromeDriver(appURL);
     		System.out.println("Outside set driver");
     	     break;
     	  
     	  case "firefox":
     		launchFirefoxDriver(appURL);
  		
         	  
          }	
		
	}
      
    
    private static void launchFirefoxDriver(String appURL) {
    	ExtentTestManager.getTest().log(LogStatus.INFO, "Launching Firefox browser"); 
		
    	System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
    	driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(appURL);
		
	
	}

    private static void launchChromeDriver(String appURL) {
    	ExtentTestManager.getTest().log(LogStatus.INFO, "Launching Chrome browser"); 
    	System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(appURL);
		System.out.println("Url loaded");
		
	}

    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }
    
    @BeforeMethod
	public void initializeTestBaseSetup(Method method) {
		try {
		    ExtentTestManager.startTest(method.getName());
			setDriver(browser, util.getConfigValue("appUrl"));

		} catch (Exception e) {
			System.out.println("Error....." + e.getStackTrace());
		}
	}
    
   
   

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult result) {
    	System.out.println("Inside after method..");
    	
    	try {
    	  ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));  // new
          ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));  // new

          for (String group : result.getMethod().getGroups()) {
              ExtentTestManager.getTest().assignCategory(group);  // new
          }

          if (result.getStatus() == 1) {
             ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");  // new
             
             System.out.println("Inside status 1 block..");
              
              try {
  				Thread.sleep(3000);
  			} catch (InterruptedException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
              
              String path1 = util.CaptureScreenShotWithTestStepNameUsingRobotClass(result.getName());
              
              String image1 = ExtentTestManager.getTest().addScreenCapture(path1);
            
              ExtentTestManager.getTest().log(LogStatus.PASS,image1);
                     	  
          
             	
          } else if (result.getStatus() == 2) {
              String path = util.getscreenshot(driver, result.getName());
             
              if(browser.equalsIgnoreCase("firefox")){
              String editPath = "file://";
              String FinalPath = editPath.concat(path);
              
              System.out.println("Path in gecko refreshed");
             
              String image = ExtentTestManager.getTest().addScreenCapture(FinalPath);
          	  ExtentTestManager.getTest().log(LogStatus.FAIL,getStackTrace(result.getThrowable()));
          	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          	ExtentTestManager.getTest().log(LogStatus.FAIL,image);
          	driver.get(FinalPath);
          	
              }else{
              	
                  String image = ExtentTestManager.getTest().addScreenCapture(path);
               	ExtentTestManager.getTest().log(LogStatus.FAIL,getStackTrace(result.getThrowable()));
               	ExtentTestManager.getTest().log(LogStatus.FAIL,image);
               	driver.get(path);
              }
          	
          } else if (result.getStatus() == 3) {
              ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");  // new
          }

          ExtentTestManager.endTest();  // new
     
          
    	}catch(Exception e) {
    		
    		 System.out.println("Inside catch block..");
    		 String path1 = util.CaptureScreenShotWithTestStepNameUsingRobotClass(result.getName());
             
             String image1 = ExtentTestManager.getTest().addScreenCapture(path1);
           
             ExtentTestManager.getTest().log(LogStatus.FAIL,image1);
             
             if (result.getThrowable() != null) {
            	String message = result.getThrowable().getMessage();
            	 ExtentTestManager.getTest().log(LogStatus.FAIL,message);
             }
             //driver.get(path1); 
    	}finally {
    		extent.flush();
            driver.quit();
    	}
    }

    @AfterSuite
    public void generateReport() {
    	
       extent.close();
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
