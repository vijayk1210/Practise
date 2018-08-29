package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utility.Base;

public class TestReRun extends Base{
	
  @Test
  public void ReRunOne() {
	  
	  Assert.assertTrue(false);
	  
  }
  
  
  @Test
  public void ReRuntwo() {
	  
	  Assert.assertTrue(true);
	  
  }
}
