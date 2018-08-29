package TestCases;

public class testing3 {
	
	 public static String validateRow(String input1,String input2)
	    {
		//Write code here
	    String status = null;
		
		int len1 = input1.length();
		int len2 = input2.length();
		
		if(len1==len2) {
			
			
			for(int i=0;i<len1;i++) {
				status = "No";
				String subStr = input1.substring(i, i+1);
				
				for(int j=0;j<len1;j++) {
					
					String subStr2 = input2.substring(j, j+1);
					
					if(subStr.equals(subStr2)) {
						status = "Yes";
						break;
					}
				}
				
				if(status.equals("No")) {
					
					break;
				}
				
			}
			
			
			
			
		}
		
		
		
		return status;
		
	    }
	 
	 
	 

}
