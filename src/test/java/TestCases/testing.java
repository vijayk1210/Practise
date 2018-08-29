package TestCases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class testing {
	
	public void normalMethod() {
		
		System.out.println("This is a non static method in superclass");
	}
	
	public int methodOne(int a, int b) {
		int c = a + b ;
		
		return c;
	}
	
	public static void staticMethod(String text) {
		
		System.out.println("This is a static method in super"+text);
	}
	
	
	public static void main(String[] args) {
		try {
		String str = "virat29kohli";
		String str2 = "36MsDhoni";
		String str3 = "vijaykumar30";
		
		str = str.replaceAll("[^0-9]+", "");
		str2 = str2.replaceAll("[^0-9]+", "");
		str3 = str3.replaceAll("[^0-9]+", "");
		
		int ageVirat = Integer.parseInt(str);
		int ageMS = Integer.parseInt(str2);
		int ageVijay = Integer.parseInt(str3);
		
		int avgAge = ( ageVirat + ageMS + ageVijay )/3;
		
		//System.out.println("Average age is: "+avgAge);
		
		
		
		}finally {
			
		}
		
		try {
			Date dateObj = new Date();
			
			
			System.out.println("Today's date is: "+dateObj.toString());
			
			SimpleDateFormat custDate = new SimpleDateFormat("dd/MM/yyyy");
			
			Date dateObj2 = custDate.parse("26/03/2018");
			
			custDate.format(dateObj2);
			
			if(dateObj.compareTo(dateObj2) > 0) {
				
				System.out.println("Date specified is a past date");
				
				
			}else {
				
				if(dateObj.compareTo(dateObj2) < 0) {
					System.out.println("Date specified is future date");
				}else {
					
				    if(dateObj.compareTo(dateObj2) == 0) {
				    	
				    	System.out.println("Date specified is current date");
				    }
				}
				
				
			}
			
			System.out.println("Date in new format: "+custDate.format(dateObj));
			
			
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
