package Utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.server.FirefoxDriverProvider;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

//import applicationUtility.PageObjects;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;

public class Base {
	
    public static ExtentReports extent;
    public static ExtentTest test;
    public static ThreadLocal<RemoteWebDriver> webDriver = new ThreadLocal<RemoteWebDriver>();
    
    
    private void grid(String browser,String node,String appURL){
      AndroidDriver driver = null;
      DesiredCapabilities capability = null;
      switch(browser){
   	  case "chrome":
   		 capability = new DesiredCapabilities();
   	     //capability.setCapability("version","");
   		 capability.setCapability("platformName","Android");
   	     capability.setCapability("deviceName","Samsung Galaxy S6");
   	     capability.setBrowserName(browser);
   	  try {
			driver = new AndroidDriver(new URL(node),capability);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	     break;
   	  
   	  case "firefox":
   		capability = new DesiredCapabilities();
   		  //capability.setCapability("version","");
  	      //capability.setCapability("platform","LINUX");
   	    capability.setCapability("platformName","Android");
   	    capability.setCapability("deviceName","Nexus 5");
   		capability.setBrowserName(browser);
  	    try {
			driver = new AndroidDriver(new URL(node),capability);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
        	
    	 setWebDriver(driver);
    	 
    	 getDriver().manage().window().maximize();
 	     ExtentTestManager.getTest().log(LogStatus.INFO, "Launching test on: "+browser); 
 	     getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 	     getDriver().get(appURL);
 	     System.out.println("Launching test on: "+browser);
    }
    
    public static WebDriver getDriver() {
        return webDriver.get();
    }
 
    public void setWebDriver(AndroidDriver driver) {
    	webDriver.set(driver);
    }
  
   
    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }
    
    
    @BeforeMethod
    @Parameters({ "browser", "node" })
	public void initializeTestBaseSetup(String browser,String node, Method method) {
    	
		try {
			 ExtentTestManager.startTest(method.getName());
			 
			//setDriver(browser, util.getConfigValue("appUrl"));
			
			 grid(browser,node,util.getConfigValue("appUrl"));
             System.out.println(util.getConfigValue("appUrl"));
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
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));  // new
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));  // new

        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);  // new
        }

        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");  // new
        } else if (result.getStatus() == 2) {
           // String path = util.getscreenshot(getDriver(), result.getName());
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));  // new
           // String image = ExtentTestManager.getTest().addScreenCapture(path);
        	ExtentTestManager.getTest().log(LogStatus.FAIL,getStackTrace(result.getThrowable()));
        	//ExtentTestManager.getTest().log(LogStatus.FAIL,image);
        	//getDriver().get(path);
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");  // new
        }

        ExtentTestManager.endTest();  // new
   
        extent.flush();
        //driver.quit();
        
        if(getDriver()!=null){
        	 
        	getDriver().close();
        	 
         }
        
        webDriver.set(null);
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
