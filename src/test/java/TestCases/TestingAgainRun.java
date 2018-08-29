package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utility.Base;

public class TestingAgainRun extends Base {
	 @Test
	  public void ReRunningOne() {
		  
		  Assert.assertTrue(true);
		  
	  }
	  
	  
	  @Test
	  public void ReRunningTwo() {
		  
		  Assert.assertTrue(false);
		  
	  }
}
