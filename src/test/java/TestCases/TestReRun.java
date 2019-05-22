package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Utility.Base;

public class TestReRun extends Base{
	
  @Test
  public void eightyTwenty() {
	  
	  Assert.assertTrue(true, "1. Launch application. \n 2. Enter credentials and sign in.");
	
  }
  
  
  @Test
  public void testbugFromJenkins() {
	  
	  SoftAssert softAssertion= new SoftAssert();
	 
	  softAssertion.assertTrue(true, "Failed via soft assert");
	  softAssertion.assertTrue(false, "Another soft assert failure");
	  softAssertion.assertAll();
	  
  }
}
