package TestCases;

import org.apache.commons.lang3.RandomStringUtils;

public class testing2 extends testing {
	
     public void normalMethod() {
		
		System.out.println("This is a non static method in subclass");
	}
     
     public static void staticMethod(String text) {
 		
 		System.out.println("This is a static method in sub"+text);
 	}
     
     public String methodOne(int a , int b , int g) {
 		int c = a + b + g ;
 		String x = Integer.toString(c);
 		
 		return x;
 		
 	}
     
     public void nativeSubMeth() {
    	 
    	 System.out.println("Native to sub class");
     }
     
     
     public static void switchMeth() {
    	 
    	 for(int i=0;i<3;i++) {
    		 
    		 if(i==2) {
    			 
    			 continue;
    		 }
    		 
    		 switch (i) {
    		 
    		 case 0:
    			 System.out.println("");
    			 
    			 break;
    		 
    		 case 1:
    			 
    			 System.out.println("One");
    			 
    			 break;
    			 
    		 case 2:
    			 
    			 System.out.println("Two");
    			 
    			 break;
    			 
    		 case 3:
    			 
    			 System.out.println("Three");
    			 
    			 break;
    			 
    			 
    		 default :
    			 
    			 System.out.println("Zero");
    			 
    			 break;
    		 }
    	 }
     }
	
	
     public static void main(String[] args) {
    	 
    	/* testing subObj = new testing2();
    	 
    	 testing2 subObj2 = new testing2();
    	 
    	 testing subObj3 = new testing();
    	 
    	 subObj.staticMethod("class");
    	 
    	 subObj.normalMethod();
    	 
    	 subObj2.staticMethod("class");
    	 
    	 subObj2.normalMethod();
    	 
    	 subObj3.staticMethod("class");
    	 
    	 subObj3.normalMethod();
    	 
    	 String name = "vijAY";
    	 
    	 String newName = "";
    	 
    	 
    	 for(int i=0;i < name.length();i++) {
    		 
    		
    	  if( Character.isLowerCase(name.charAt(i)) ) {
    		  
    		  newName = newName + Character.toUpperCase(name.charAt(i));
    		 
    		  
    	  }else {
    		  
    		  newName =  newName + Character.toLowerCase(name.charAt(i));
    		  
    	  }
    		 
    		 
    		 
    	 }
    	 
    	 
    	 System.out.println("Toggle effect on string case is: "+newName);
    	 
     }*/
    	 
    	 String random = RandomStringUtils.random(7,new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m'}); 
    	 
    	// System.out.println("Random string: "+random);
    	 
    	 
    	// switchMeth();
    	 
    	 testing subObj = new testing2();
    	 
    	 testing2 subObj2 = new testing2();
    	 
    	 testing subObj3 = new testing();
    	 
    	 subObj.normalMethod();
    	 
    	
    	 
    	 subObj2.nativeSubMeth();
    	 
    	 
    	 subObj3.normalMethod();
    	 
    	 

}
}
