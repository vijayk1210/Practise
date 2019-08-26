package TestCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utility.Base;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredBDD{
	
  @Test (enabled=false)
  public void SimpleAsserts() {
	  
	  
	 /* given().
	    when().
	        get("http://ergast.com/api/f1/2017/circuits.json").
	    then().
	        assertThat(). body("MRData.CircuitTable.Circuits.circuitId",hasSize(20));*/
	  
	  given().
	    when().
	        get("http://ergast.com/api/f1/2017/circuits.json").
	    then().
	        assertThat().
	        body("MRData.CircuitTable.Circuits[0].circuitId",equalTo("albert_park")).
	        and().
	        body("MRData.CircuitTable.Circuits[0].circuitName",equalTo("Albert Park Grand Prix Circuit"));
	  
  }
  
  @DataProvider(name="circuitIDandTheirNames")
 	public Object[][] createTestDataRecords() {
 	    return new Object[][] {
 	        {"albert_park","Albert Park Grand Prix Circuit","Australia"},
 	        {"americas","Circuit of the Americas","USA"},
 	        {"bahrain","Bahrain International Circuit","Bahrain"}
 	    };
 	}	
  
  @Test (dataProvider = "circuitIDandTheirNames")
  public void pathParams(String circuitId, String circuitName,String location) {
	  
	  	        
	    given().
	        pathParam("circuitId",circuitId).
	    when().
	        get("http://ergast.com/api/f1/circuits/{circuitId}.json").
	    then().
	        assertThat().
	        body("MRData.CircuitTable.Circuits[0].circuitName",equalTo(location)).
	        and(). 
	        body("MRData.CircuitTable.Circuits.Location[0].country",equalTo(location));
	  
	  
  }
  
  
}
