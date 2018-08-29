package PageObjects;

import org.openqa.selenium.By;

import Utility.Base;

public class HomePage extends Base {
	
	public static String userIdXpath = "//input[@name='uid']";
	public static String passwordXpath = "//input[@name='password']";
	public static String loginXpath = "//input[@name='btnLogin']";
	public static String userIdHomePageXpath = "//tr[@class='heading3']/td[text()='Manger Id : mngr124806']";
	
	
	public static void enterUserID(String userID) {
		driver.findElement(By.xpath(userIdXpath)).sendKeys(userID);
	}
	
	public static void enterPassword(String password) {
		driver.findElement(By.xpath(passwordXpath)).sendKeys(password);
	}
	
	public static void clickLogin() {
		driver.findElement(By.xpath(loginXpath)).click();
	}
	
	public static void login(String userID,String password) {
		
		driver.findElement(By.xpath(userIdXpath)).sendKeys(userID);
		driver.findElement(By.xpath(passwordXpath)).sendKeys(password);
		driver.findElement(By.xpath(loginXpath)).click();
	}

}
