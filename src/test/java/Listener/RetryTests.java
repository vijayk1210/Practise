package Listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTests implements IRetryAnalyzer{

	int counter = 0;
	int retryLimit = 0;
	
	
	
	public boolean retry(ITestResult result){
		if(counter < retryLimit){
			counter++;
			return true;
		}
		return false;
	}
}
