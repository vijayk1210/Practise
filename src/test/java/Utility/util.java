package Utility;


import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;

import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class util extends Base {
	
	protected static FileReader reader;
	protected static Properties properties;
	
	protected static XSSFWorkbook ExcelWBook;
	protected static XSSFSheet ExcelWSheet;
	protected static XSSFRow Row;
	protected static XSSFCell Cell;
	private static String [][] arrayExcelData;
	
	
	
	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
	 
	public static void setExcelFile(String Path,String SheetName) throws Exception {

			try {

   			// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(Path);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			} catch (Exception e){

				throw (e);

			}

	}
	
	
	public static String[][]  get2DExcelData(String path, String sheetName){
		arrayExcelData = null;
		try{
			
		setExcelFile(path,sheetName);
		XSSFRow row = ExcelWSheet.getRow(0);
		int totalRows = ExcelWSheet.getLastRowNum();
		int totalColumns = row.getLastCellNum();
		System.out.println("last column no. is: "+totalColumns);
		System.out.println("Total Rows are : "+totalRows);
		
		arrayExcelData = new String[totalRows][totalColumns];
		
		for(int i=1;i<=totalRows;i++){
			
			for(int j=0;j<totalColumns;j++){
				Cell = ExcelWSheet.getRow(i).getCell(j);
				
				
				if(Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
	  				
					arrayExcelData[i-1][j] = Cell.getStringCellValue();
	  			
	            else if(Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC || Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA)
	            {
	                String cellValue  = NumberToTextConverter.toText(Cell.getNumericCellValue());
	                
	                if (HSSFDateUtil.isCellDateFormatted(Cell))
	                {
	                    DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
	                    Date date = Cell.getDateCellValue();
	                    cellValue = df.format(date);
	                }
	                arrayExcelData[i-1][j] =  cellValue;
	            }else if(Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK)
	            	
	            	arrayExcelData[i-1][j] =  "";
	            else
	            	arrayExcelData[i-1][j] = String.valueOf(Cell.getBooleanCellValue());
				
			}
		}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	} 

	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

    public static String getCellData(String path, String sheetName,int RowNum, int ColNum) throws Exception{

			try{
				
		    setExcelFile(path,sheetName);
				
  			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
  			
  			if(Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
  				
                return Cell.getStringCellValue();
  			
            else if(Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC || Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA)
            {
                String cellValue  = NumberToTextConverter.toText(Cell.getNumericCellValue());
                
                if (HSSFDateUtil.isCellDateFormatted(Cell))
                {
                    DateFormat df = new SimpleDateFormat("yyyy/mm/dd");
                    Date date = Cell.getDateCellValue();
                    cellValue = df.format(date);
                }
                return cellValue;
            }else if(Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK)
            	
                return "";
            else
                return String.valueOf(Cell.getBooleanCellValue());
  			
  				

  			}catch (Exception e){

				return"";

  			}

    }

	
	public static String getConfigValue(String key){
		String val=null;
		try {
			reader = new FileReader("config.properties");
			properties = new Properties();
			properties.load(reader);
			val = properties.getProperty(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return val;
	}
	
	
	public static String getTestData(String key){
		String val=null;
		try {
			reader = new FileReader(util.getConfigValue("testData"));
			properties = new Properties();
			properties.load(reader);
			val = properties.getProperty(key);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ExtentTestManager.getTest().log(LogStatus.FATAL, "Testdata file is missing");
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return val;
	}
	
	public static String getRandomAlpaNumericString() {
		
        String RandCHARS = "ABCDEFGHIJK LMNOPQRSTUVWXYZ 1234567890";
        StringBuilder rans = new StringBuilder();
        Random rnd = new Random();
        while (rans.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RandCHARS.length());
            rans.append(RandCHARS.charAt(index));
        }
        String ranFinal = rans.toString();
        return ranFinal;

    }
	
	/*public static String getscreenshot(Webdriver getDriver(),String screenshotName)
    {    
		String filePath=null;
		try{
            File scrnFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            filePath  = System.getProperty("user.dir")+"\\Screenshots\\"+screenshotName+".png";
            
            FileUtils.copyFile(scrnFile, new File(filePath));
            
    }catch(Exception e){
    	   e.printStackTrace();
    }
    
	return filePath;
    }
	
	*/
	
	public static String CaptureScreenShotWithTestStepNameUsingRobotClass(String testStepsName)
	{
		try{
			
			// Creating Robot class object
			Robot robotClassObject = new Robot();
			
			// Get screen size
			Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			
			// Capturing screenshot by providing size
            BufferedImage tmp = robotClassObject.createScreenCapture(screenSize); 
            
            // Defining destination file path
            String path=System.getProperty("user.dir")+"/Screenshots/"+testStepsName+System.currentTimeMillis()+".png";
            
            // To copy temp image in to permanent file
            ImageIO.write(tmp, "png",new File(path)); 
            return path;
            
		}catch(Exception e)
		{
			System.out.println("Some exception occured." + e.getMessage());
			return "";
		}
	}
	
	
	public static void clickByXpath(String xpath){
		getDriver().findElement(By.xpath(xpath)).click();
	}
    
	public static String  replaceChar(String testChar,String initial,String replacedChar){
		String newstr = testChar.replace(initial, replacedChar);
		
		return newstr;
	}
	
	public static String getAlertText() {
		String text = null;
		Alert alert = getDriver().switchTo().alert();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		text = alert.getText();
		
		return text;
	}
	
	
	public static String alertHandles(String operation) {
		Alert alert = getDriver().switchTo().alert();
		String text = "";
		switch(operation) {
		
		case "accept":
			alert.accept();
			
			break;
		case  "dismiss":
			alert.dismiss();
			
			break;
		case "text":
			text = alert.getText();
		
		  	
		}
		return text;
	}
	
	public static void mousehover(String st1){
	    Actions a = new Actions(getDriver());
	    WebElement we =   getDriver().findElement(By.xpath(st1));
	    a.moveToElement(we).build().perform();
	}

	public static String getText(String xpath){
		String text=null;
		text = getDriver().findElement(By.xpath(xpath)).getText();
		return text;
	}
	

	public static String getAttribute(String attribute,String xpath){
		String attributeVal = null;
		attributeVal = getDriver().findElement(By.xpath(xpath)).getAttribute(attribute);
		return attributeVal;
	}
	
	public static void enterText(String data, String xpath){
		getDriver().findElement(By.xpath(xpath)).sendKeys(data);
	}
	
	public static void clearText(String xpath){
		getDriver().findElement(By.xpath(xpath)).clear();
	}

	public static void selectDropDownValue(String text,String xpath){
		WebElement we = getDriver().findElement(By.xpath(xpath));
		Select selectDrop = new Select(we);
		selectDrop.selectByVisibleText(text);
		
	}
	
	public static List<WebElement> getElements(String xpath){
		List<WebElement> ls = new ArrayList<WebElement>();
		 ls = getDriver().findElements(By.xpath(xpath));
		
		return ls;
	}
	
	public static void scrollTo() {
		JavascriptExecutor jse = (JavascriptExecutor)getDriver();
		jse.executeScript("window.scrollBy(0,550)", "");
    }
	
	public static void switchToIframe(String frameid){
		
		getDriver().switchTo().frame(frameid);
	}
	public static boolean elementPresentOrNot(String xpath){
 	   boolean status=false;
 	   status = getDriver().findElement(By.xpath(xpath)).isDisplayed();
 	   return status;
    }

	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	/*public void addremovewishlist(String ctr, String wishlistbttn) throws InterruptedException{
		getDriver().findElement(By.xpath(wishlistbttn)).click();
		Thread.sleep(5000);
		String str = getDriver().findElementById(wishlist_counter_e).getText();
		System.out.println("Counter value is : "+str);
		if(str.equalsIgnoreCase(ctr))
		{
		System.out.println(" TestCase is passed");
		Reporter.log(str);
	}

	else{
		
		System.out.println("Test is Fail");
	}
	}*/


	   
    public static void mousehoverNitin(String st1){
      try{ 
    	  Actions a = new Actions(getDriver());
        
        WebElement we =   getDriver().findElement(By.xpath(st1));
        Point coordinates = we.getLocation();
        Robot robot = new Robot();
        robot.mouseMove(coordinates.getX(),coordinates.getY()+120);
         
        a.moveToElement(we).build().perform();
        Thread.sleep(3000);
      }catch(Exception e){
    	  ExtentTestManager.getTest().log(LogStatus.SKIP, e.getMessage());  
      }
        
    }
    
    public static String lower_casestring(String st1) {
    	st1= st1.toLowerCase();
    	return st1;
    }
    public static String Replace_space_string(String st1) {
    	st1= st1.replaceAll("\\s+$", "");
    	return st1;
    }

   /* public void screenshot(String pagename) throws IOException{
    	File srcfile =((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
    	FileUtils.copyFile(srcfile, new File("C:\\Users\\nitin\\Desktop\\Testing Report\\Whole Site Test Cases\\Automated Test Cases\\12950\\"+pagename+".png"));

    }*/

    public static String replacecharachter(String s){
    	String numberOnly= s.replaceAll("[^0-9]", "");
    	double i = (Double.parseDouble(numberOnly))/100;
    	int k = (int) i;
    	String numbero = Integer.toString(k);
    	return numbero;
    }
    
    public static void selectdropdownvalue(String str1, String str2){
    	
    	 Select daydropdown = new Select(getDriver().findElement(By.xpath(str1)));
    	 int i= Integer.parseInt(str2);
    	 daydropdown.selectByIndex(i); 
    }

   

    public static void clickevent(String str){
    
    	getDriver().findElement(By.xpath(str)).click();
    }
    
    public static void clickIDevent(String str){
        
    	getDriver().findElement(By.id(str)).click();
    }
    
    public static void pagescroll(int i){
    	JavascriptExecutor jse = (JavascriptExecutor)getDriver();
    	jse.executeScript("window.scrollBy(0,"+i+")","");
    	    	
    }
   

   
    public static void enterdata(String str, String str1){
    	
    	getDriver().findElement(By.xpath(str)).sendKeys(str1);
    	
    }
    
    
    
   public static void enterdataID(String str, String str1){
    	
    	getDriver().findElement(By.id(str)).sendKeys(str1);
    	
    }


  public static boolean elementdisplayed(String str){
	  getDriver().findElement(By.xpath(str)).isDisplayed();
	  return true;
  }
  
  public static void getelement(String stname, String stelement){
	  String str;
	  
	  List<WebElement> drop = getDriver().findElements(By.xpath(stelement));
		
		 java.util.Iterator<WebElement> i = drop.iterator();
		
		 while(i.hasNext()) {
			    WebElement row = i.next();
			     str = row.getText();
			
			    if(str.equalsIgnoreCase(stname)){
				   ExtentTestManager.getTest().log(LogStatus.INFO, "clicking on product " + str);
				   row.click();
			    		break;
			    		}
			}
	  
  }
  
 
  
  public static String IDgetText(String id){
		String text = null;
		text = getDriver().findElement(By.id(id)).getText();
		return text;
	}
  
  public static String getattribute(String xpath, String att){
	 
	 String st = getDriver().findElement(By.xpath(xpath)).getAttribute(att);
	return st;
  }
  
  public static boolean guiMatchFind(String imagePath){
	    boolean match = false;
	    System.out.println("Image pathh is: "+imagePath);
		Screen screen = new Screen();
		
		try {
		    Pattern image = new Pattern(imagePath);
		    screen.wait(image,3);
			
			screen.find(image);
			
			System.out.println("Image found");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Image match found.");
			match = true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentTestManager.getTest().log(LogStatus.INFO, "Image match not found.");
			e.printStackTrace();
		}
	 
	  return match;
  }
  
  
  public static boolean guiMatchClick(String imagePath){
	    boolean match = false;
	    System.out.println("Image pathh is: "+imagePath);
		Screen screen = new Screen();
		
		try {
		    Pattern image = new Pattern(imagePath);
		    screen.wait(image,3);
			
		    screen.click(image);
		   // screen.compare(imagePath);
			System.out.println("Image found and clicked");
			
			match = true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ExtentTestManager.getTest().log(LogStatus.INFO, "Image match not found.");
			e.printStackTrace();
		}
	 
	  return match;
}
  
  
  public static void sliderArrowsUI(String childNodes , String nextArrowXpath , String previousArrowEnabledpath, String nextArrowdisabledPath, int numberOfImageInDefaultView){
	  boolean previousEnabledArrow = false;
	  boolean nextDisabledArrow = false;
	  
	  List<WebElement> images = new ArrayList<WebElement>();
	  int numberOfImages = 0;
	  int numberOfClicks = 0;
	  
	 
	  
	  images = getElements(childNodes); //getting total number of images.
	  
	  numberOfImages = images.size();
	  System.out.println("Number of images: "+numberOfImages);
	  numberOfClicks = numberOfImages - numberOfImageInDefaultView;
	  
	  for(int i=1;i<=numberOfClicks;i++){
		  clickByXpath(nextArrowXpath);
		 
		  if(i==1){
			  ExtentTestManager.getTest().log(LogStatus.INFO, "Verifying previous arrow is enabled after clicking next arrow or not.");
			  previousEnabledArrow = guiMatchFind(previousArrowEnabledpath); 
			  
			  if(previousEnabledArrow){
				  
				  ExtentTestManager.getTest().log(LogStatus.PASS, "Previous Arrow is enabled after clicking next arrow.");
			  }else{
				  ExtentTestManager.getTest().log(LogStatus.FAIL, "Previous Arrow is not enabled after clicking next arrow.");
				  
			  }
		  }
	  }
	  
	  nextDisabledArrow = util.guiMatchFind(nextArrowdisabledPath);
	  
	  if(nextDisabledArrow){
		  ExtentTestManager.getTest().log(LogStatus.PASS, "Next Arrow is disabled correctly.");
	  }else{
		  ExtentTestManager.getTest().log(LogStatus.FAIL, "Next Arrow is not getting disabled.");
		  
	  }
	  
	  if(!(previousEnabledArrow || nextDisabledArrow)){
			 Assert.fail("Slider UI is not looking as expected");
		  }  
  }
  
  public static void sliderArrowsDefaultCaseUI(String previousArrowImage, String nextArrowImage){
	  boolean previousDisabledArrow = false;
	  boolean nextEnabledArrow = false;
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Verifying previous arrow is disabled or not.");
	  previousDisabledArrow = util.guiMatchFind(previousArrowImage);
	  
	  if(previousDisabledArrow){
		  ExtentTestManager.getTest().log(LogStatus.PASS, "Previous Arrow is disabled initially.");
	  }else{
		  ExtentTestManager.getTest().log(LogStatus.FAIL, "Previous Arrow is not disabled initially.");
		  
	  }
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Verifying next arrow is enabled or not.");
	  nextEnabledArrow = util.guiMatchFind(nextArrowImage);
	  
	  if(previousDisabledArrow){
		  ExtentTestManager.getTest().log(LogStatus.PASS, "Next Arrow is enabled initially.");
	  }else{
		  ExtentTestManager.getTest().log(LogStatus.FAIL, "Next Arrow is not enabled  initially.");
		  
	  }
	  
	  if(!(previousDisabledArrow || nextEnabledArrow)){
			 Assert.fail("Slider UI is not looking as expected");
		  }  
  }
  
  
  public static void sliderArrows(String childNodes , String nextArrowXpath , String previousArrowEnabledpath, String nextArrowdisabledPath, int numberOfImageInDefaultView){
	  boolean previousEnabledArrow = false;
	  boolean nextDisabledArrow = false;
	  
	  List<WebElement> images = new ArrayList<WebElement>();
	  int numberOfImages = 0;
	  int numberOfClicks = 0;
	  
	 
	  
	  images = getElements(childNodes); //getting total number of images.
	  
	  numberOfImages = images.size();
	  System.out.println("Number of images: "+numberOfImages);
	  numberOfClicks = numberOfImages - numberOfImageInDefaultView;
	  
	  for(int i=1;i<=numberOfClicks;i++){
		  clickByXpath(nextArrowXpath);
		  
		  if(i==1){
			  ExtentTestManager.getTest().log(LogStatus.INFO, "Verifying previous arrow is enabled after clicking next arrow or not.");
			  
			  previousEnabledArrow = util.elementPresentOrNot(previousArrowEnabledpath); 
			  
			  if(previousEnabledArrow){
				  
				  ExtentTestManager.getTest().log(LogStatus.PASS, "Previous Arrow is enabled after clicking next arrow.");
			  }else{
				  ExtentTestManager.getTest().log(LogStatus.FAIL, "Previous Arrow is not enabled after clicking next arrow.");
				  
			  }
		  }
	  }
	  
	
	  nextDisabledArrow = util.elementPresentOrNot(nextArrowdisabledPath); 
	  
	  if(nextDisabledArrow){
		  ExtentTestManager.getTest().log(LogStatus.PASS, "Next Arrow is disabled correctly.");
	  }else{
		  ExtentTestManager.getTest().log(LogStatus.FAIL, "Next Arrow is not getting disabled.");
		  
	  }
	  
	  if(!(previousEnabledArrow || nextDisabledArrow)){
			//Assert.fail("Slider UI is not looking as expected");
		  }  
  }
  
  
  public static void sliderArrowsDefaultCase(String previousArrowXpath, String nextArrowImageXpath){
	  boolean previousDisabledArrow = false;
	  boolean nextEnabledArrow = false;
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Verifying previous arrow is disabled or not.");
	  previousDisabledArrow = util.elementPresentOrNot(previousArrowXpath);
	  
	  if(previousDisabledArrow){
		  ExtentTestManager.getTest().log(LogStatus.PASS, "Previous Arrow is disabled initially.");
	  }else{
		  ExtentTestManager.getTest().log(LogStatus.FAIL, "Previous Arrow is not disabled initially.");
		  
	  }
	  
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Verifying next arrow is enabled or not.");
	  nextEnabledArrow = util.elementPresentOrNot(nextArrowImageXpath);
	  
	  if(previousDisabledArrow){
		  ExtentTestManager.getTest().log(LogStatus.PASS, "Next Arrow is enabled initially.");
	  }else{
		  ExtentTestManager.getTest().log(LogStatus.FAIL, "Next Arrow is not enabled  initially.");
		  
	  }
	  
	  if(!(previousDisabledArrow || nextEnabledArrow)){
			 Assert.fail("Slider default arrow functionality is incorrect.");
		  }  
  }
  
  
 /* public static boolean matchListOfText(String XpathOfList,String WorkBookPath, String SheetName,int columnNumber){
	  boolean textMatch =false;
	  int failCount=0;
	  try{
		
		List<WebElement> actualTexts = new ArrayList<WebElement>();
		
		actualTexts = getElements(XpathOfList);
		System.out.println("List size: "+actualTexts.size());
	    setExcelFile(WorkBookPath,SheetName);
	    
	    int totalRows = ExcelWSheet.getLastRowNum();
	    System.out.println("Row size: "+totalRows);
	    
	    for(int i=1;i<=totalRows;i++){
	    	String expectedText = getCellData(i, columnNumber);
	    	int length = expectedText.length();
	    	
	    	if(length==0){
	    		System.out.println("Cell empty"); 
	    		break;
	    	}
	    	for(int j=0;j<actualTexts.size();j++){
	    		textMatch = false;
	    		WebElement actualElement = actualTexts.get(j);
	    		String actualText = actualElement.getText();
	    		
	    		if(expectedText.equalsIgnoreCase(actualText)){
	    			
	    			ExtentTestManager.getTest().log(LogStatus.PASS, "Match exists for :"+expectedText);
	    			textMatch=true;
	    			break;
	    		}
	    	}
	    	
	    	if(textMatch==false){
	    		ExtentTestManager.getTest().log(LogStatus.FAIL, "Match missing for :"+expectedText);
	    		failCount++;
	    		
	    	}
	    }
	    
	    if(failCount>0){
	    	textMatch=false;
			
		}
	  
	  }catch(Exception e){
		  e.printStackTrace();
	 
    }
    return textMatch;
  } 
  
  
  public static void AllProductHoverThumbnailSlider(int numberOfImagesInDefaultView, String productRows, String productsliderthumnails, String products, String ProductThumbnailUpArrow, String ProductThumbnailDownArrow, String ProductThumbnailUpArrowDisabled) throws InterruptedException {
	   

	  
	    
	    int imagenumber=1; int rownumber= 1;
	    WebElement image;
	    List<WebElement> imagelist = new ArrayList<WebElement>();
	    List<WebElement> Thumbnaillist = new ArrayList<WebElement>();
	    List<WebElement> rowlist = new ArrayList<WebElement>();
	    String productByRow;
	      String thumbnailslider, thumbnailsliderlist ;
	 
	    rowlist = util.getElements(productRows);
	       java.util.Iterator<WebElement> row = rowlist.iterator();
	       
	    for (rownumber = 1; rownumber<= rowlist.size(); rownumber++)
	    {
	   
	         
	         row.next();
	         
	         productByRow = products.replace("rownumber", Integer.toString(rownumber));
	         
	         imagelist = util.getElements(productByRow);
	             
	         java.util.Iterator<WebElement> imageelement = imagelist.iterator();
	         
	             util.pagescroll(400);
	                   
	             Thread.sleep(2000);
	  
	               for (imagenumber = 1 ; imagenumber <= imagelist.size(); imagenumber++){
	             
	                System.out.println("step5");
	      
	                image = imageelement.next();
	             
	                Thread.sleep(2000);
	  

	             Actions a = new Actions(getDriver());
	            a.moveToElement(image).build().perform();
	            
	            Thread.sleep(2000);
	     
	    System.out.println("hovering on product "  + imagenumber +" at row "+  rownumber );
	   
	  
	    thumbnailslider = productsliderthumnails.replace("productrow", Integer.toString(rownumber));
	    
	    thumbnailsliderlist = thumbnailslider.replace("imagenumber", Integer.toString(imagenumber));
	    
	    Thumbnaillist = util.getElements(thumbnailsliderlist);
	    
	    System.out.println(Thumbnaillist.size());
	    
	    util.sliderArrows(thumbnailsliderlist, ProductThumbnailUpArrow, ProductThumbnailDownArrow, ProductThumbnailUpArrowDisabled, numberOfImagesInDefaultView);
	    
	    }
	    
	   }
	 }
  
  public static void productSlider(String rowsXpath,String numberOfProductsInRowXpath,String productsInRowXpath,String sliderImages,String nextArrowEnabledXpath,String previousArrowEnabledXpath ,String nextArrowDisabledXpath,String previousArrowDisabledXpath){
	  
	  List<WebElement> rows = new ArrayList<WebElement>();
	  List<WebElement> productsForRow = new ArrayList<WebElement>();
	  List<WebElement> numberOfImagesList = new ArrayList<WebElement>();
	  int numberOfImageInDefaultView = 4;
	  
	  
	  rows = getElements(rowsXpath);
	  
	  for(int i=1;i<=(rows.size());i++){
		  
		  
		  String replacementOuter = Integer.toString(i);
		  String numberOfProductsInRow = numberOfProductsInRowXpath.replace("outerVal", replacementOuter);
		  
		  productsForRow = getElements(numberOfProductsInRow);
		  
		  for(int j=1;j<=(productsForRow.size());j++){
			  
			  String replacementInner  = Integer.toString(j);
			  
			  String productsInRowTemp = productsInRowXpath.replace("outerVal", replacementOuter);
			  String productsInRow  = productsInRowTemp.replace("innerVal", replacementInner);
			  
			  util.mousehover(productsInRow);
			  
			  String numberOfImagesTemp = sliderImages.replace("outerVal", replacementOuter);
			  String numberOfImages = numberOfImagesTemp.replace("innerVal", replacementInner);
			  numberOfImagesList = getElements(numberOfImages);
			  
			  if((numberOfImagesList.size()) > 4){
			  
			  String nextArrowEnabledTemp = nextArrowEnabledXpath.replace("outerVal", replacementOuter);
			  String nextArrowEnabled = nextArrowEnabledTemp.replace("innerVal", replacementInner);
			  
			  String previousArrowEnabledTemp = previousArrowEnabledXpath.replace("outerVal", replacementOuter);
			  String previousArrowEnabled = previousArrowEnabledTemp.replace("innerVal", replacementInner);
			  
			  String nextArrowDisabledTemp = nextArrowDisabledXpath.replace("outerVal", replacementOuter);
			  String nextArrowDisabled = nextArrowDisabledTemp.replace("innerVal", replacementInner);
			  
			  String previousArrowDisabledTemp = previousArrowDisabledXpath.replace("outerVal", replacementOuter);
			  String previousArrowDisabled = previousArrowDisabledTemp.replace("innerVal", replacementInner);
			  
			  try{
			  util.sliderArrowsDefaultCase(previousArrowDisabled, nextArrowEnabled);
			  util.sliderArrows(numberOfImages, nextArrowEnabled, previousArrowEnabled, nextArrowDisabled, numberOfImageInDefaultView);
			  
			  }catch(Exception e){
				  ExtentTestManager.getTest().log(LogStatus.ERROR, "Slider is not matching in: "+i+" row "+j+" product"); 
			  }
			  
			  }else{
				  ExtentTestManager.getTest().log(LogStatus.INFO, "Number of images less then or equal to 4 hence slider not available.");
				  continue;
			  }
			  
		  }
		 	  
		 util.pagescroll(250);
		 
		 try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	  }
	  
	  
  }
  
  
  public static void MatchButtonTextOnTheProduct(String categorieProduct, String ProductToTheSeriesButton, String ButtonExpText) throws InterruptedException {
	  
	   WebElement row;
	   WebElement row1;
	  
	 
	  Thread.sleep(2000);
	  ExtentTestManager.getTest().log(LogStatus.INFO, "Storing the elements of the product list and button in the array");
	  List<WebElement> imagelist = new ArrayList<WebElement>();
	  List<WebElement> buttonlist = util.getElements(ProductToTheSeriesButton);
	 
	   java.util.Iterator<WebElement> buttonlistsequence = buttonlist.iterator();
	
	   imagelist = util.getElements(categorieProduct);
	 
	  
	   java.util.Iterator<WebElement> imagelistsequence = imagelist.iterator();
	  
	   ExtentTestManager.getTest().log(LogStatus.INFO, "Scroll the page to product list");
	   util.pagescroll(1200);
	   
	   
	   Thread.sleep(2000);
	   int section=0; 
	   ExtentTestManager.getTest().log(LogStatus.INFO, "Getting the size of product list");
	   System.out.println(imagelist.size());
	   
	   while(imagelistsequence.hasNext())
	   {
		    row = imagelistsequence.next();
		    row1= buttonlistsequence.next(); 
		   
		    ExtentTestManager.getTest().log(LogStatus.INFO, "Getting the size of product list");
		   
		   System.out.println("going to next elemenet");  
		   
		   Thread.sleep(2000);
		   
		   Actions a = new Actions(getDriver());
		   a.moveToElement(row).build().perform();
		
		   
		   
		   
		   String ButtonActText = row1.getText();
		   
		   ExtentTestManager.getTest().log(LogStatus.INFO, "Verifying Button text " +ButtonExpText +" is matching or not");
		   
		   if(ButtonActText.equalsIgnoreCase(ButtonExpText)){
			   
			   System.out.println("Text Matches "+ ButtonActText);
			   ExtentTestManager.getTest().log(LogStatus.PASS, "Text is correct.");
			   ExtentTestManager.getTest().log(LogStatus.PASS, "Match exists for :"+ButtonExpText);
		   }
		   else{
			   System.out.println("Not matches");
			   ExtentTestManager.getTest().log(LogStatus.PASS, "Text is incorrect.");
			   ExtentTestManager.getTest().log(LogStatus.FAIL, "Match missing for :"+ ButtonExpText);
			   Assert.fail("Expected Text does not match with actual text");
		   }
		
		   section++;
		
		   Thread.sleep(2000);
		   // System.out.println(j);
		   
		   if(section == 3 ){
			
		   Thread.sleep(5000);
		   ExtentTestManager.getTest().log(LogStatus.INFO, "Scrolling the page to another list");
			util.pagescroll(400);
			section=1;

		   }
		   
		   
	   }
		  */
 
 
  
}
