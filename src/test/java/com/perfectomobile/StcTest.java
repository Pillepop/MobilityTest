package com.perfectomobile;

import com.perfectomobile.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import com.perfectomobile.httpclient.MediaType;
import com.perfectomobile.httpclient.utils.FileUtils;
import com.perfectomobile.selenium.MobileAddressLocation;
import com.perfectomobile.selenium.MobileCoordinatesLocation;
import com.perfectomobile.selenium.MobileDriver;
import com.perfectomobile.selenium.MobileDevice;
import com.perfectomobile.selenium.MobileDriver;
import com.perfectomobile.selenium.MobileLocation;
import com.perfectomobile.selenium.api.IMobileDevice;
import com.perfectomobile.selenium.api.IMobileDriver;
import com.perfectomobile.selenium.api.IMobileWebDriver;
import com.perfectomobile.selenium.options.MobileApplicationInstallOptions;
import com.perfectomobile.selenium.options.MobileBrowserType;
import com.perfectomobile.selenium.options.MobileDeviceHomeOptions;
import com.perfectomobile.selenium.options.MobileDeviceOpenOptions;
import com.perfectomobile.selenium.options.visual.MobileSourceOptions;
import com.perfectomobile.selenium.options.visual.image.MobileImageMatchMode;
import com.perfectomobile.selenium.options.visual.text.MobileTextAnalysisMode;
import com.perfectomobile.selenium.by.*;


public class StcTest {
	private static  MobileDriver driver;
	private IMobileDevice device;
	//private String repositoryPath = "PUBLIC:Philipp\\builds\\STC.apk";
	//private String localPath = "C:\\mybuild\\MySTC_com.stc_2.4.0_46.apk";
	//private String appIdentifier = "MySTC";
	private String repositoryPath = "PUBLIC:Philipp\\builds\\car2go_com.car2go_2.2.3_20203.apk";
	private String localPath = "C:\\mybuild\\car2go_com.car2go_2.2.3_20203.apk";
	private String appIdentifier = "car2go";
	private static String _Device;
	
	private boolean bUpload=false;
	private boolean bInstall=false;
	

	@Test(dataProvider = "dp")
	public void f(Integer n, String s) {
	  _Device = s;
	  Reporter.log("Test started on "+s);
	  device = driver.getDevice(s);
	  device.open();	  
	 	  
	 if (bInstall){  
		 install(s);  
	 }
	testCar2Go(s);
	}
	  @DataProvider
	  public Object[][] dp() {
	    return new Object[][] {
	      new Object[] { 1, "1E674EB8" },
	      new Object[] { 2, "219595A5" },
	    };
	  }

  @BeforeTest
  public void beforeTest() {
	  driver  = new MobileDriver("demo.perfectomobile.com", "philipps@perfectomobile.com", "Perfect0123");
	  if (bUpload){ 
	  uploadLatestBuild();
	  }
	  driver.quit();
  }

  @BeforeMethod
  public void beforeMethod() {
//	  System.out.println("Instantiate driver");
	  driver  = new MobileDriver("demo.perfectomobile.com", "philipps@perfectomobile.com", "Perfect0123");
//	  uploadLatestBuild();
  }  
  @AfterMethod
  public void AfterMethod() {
//	  System.out.println("Executing AfterMethod");	 
	  Reporter.log("Test Run finished");
	  driver.quit();
	  obtainReport();
  }  
  
  @AfterTest
  public void afterTest() {
	  
//	  System.out.println("finished");
	  
  }

  @BeforeSuite
  public void beforeSuite() {
  }

  @AfterSuite
  public void afterSuite() {
  }

  public void uploadLatestBuild(){
	  try {
		 driver.uploadMedia(repositoryPath,localPath);
		 System.out.println("Upload of "+appIdentifier+ " succeeded");
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Upload of "+appIdentifier + " failed: " +e.getMessage());
	}
  }

  public void install(String deviceID){
 
	  try {
		  try {
			  	device.getNativeDriver("Car2Go").uninstall();
			} catch (Exception e) {
				// TODO: handle exception
			}
		  
		  device.installApplication(repositoryPath);
		  System.out.println("Installation of "+repositoryPath + " succeeded");
	} catch (Exception e) {
		System.out.println("Installation of "+repositoryPath + " failed: " +e.getMessage());
		// TODO: handle exception
	}
}
  
  public void testrun(String deviceID){
	  //MobileDriver driver = new MobileDriver();
	  	 
	  MobileDeviceHomeOptions homeOptions1 = new MobileDeviceHomeOptions();
	  device.home(homeOptions1);

	  IMobileWebDriver visualDriver = device.getVisualDriver();
      IMobileWebDriver webDriver = 	device.getDOMDriver(MobileBrowserType.DEFAULT);
      IMobileWebDriver nativeDriver = device.getNativeDriver();
	  
      device.getNativeDriver(appIdentifier).open();		
		
		try{
		visualDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		visualDriver.manageMobile().visualOptions().validationOptions().setThreshold(90);	
		visualDriver.findElement(By.linkText("MyStc account"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}		
		device.close();
  }
  public void testCar2Go(String deviceID){
	  //MobileDriver driver = new MobileDriver();
	  	 
	  MobileDeviceHomeOptions homeOptions1 = new MobileDeviceHomeOptions();
	  device.home(homeOptions1);
	  device.resetLocation();


	  IMobileWebDriver visualDriver = device.getVisualDriver();
      IMobileWebDriver webDriver = 	device.getDOMDriver(MobileBrowserType.DEFAULT);
      IMobileWebDriver nativeDriver = device.getNativeDriver();
      	  
      device.getNativeDriver(appIdentifier).open();		
	
		try{
			visualDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			visualDriver.manageMobile().visualOptions().validationOptions().setThreshold(95);
			visualDriver.findElement(By.linkText("2015 Google"));

			MobileLocation location2 = new MobileAddressLocation("Wilhelm-Runge-Straﬂe 11, Ulm");
			MobileLocation location3 = new MobileCoordinatesLocation("48.42143,9.94168");

			device.setLocation(location2);
			System.out.println("location set, centering");			
/*			webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			visualDriver.manageMobile().visualOptions().imageMatchOptions().setMatchMode(MobileImageMatchMode.BOUNDED_SIZE);
			visualDriver.findElement(ByMobile.image("PUBLIC:Philipp\\button_location.png")).click();
			*/			
			//nativeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//nativeDriver.findElement(By.xpath("//*[@contentDesc=\"Position bestimmen\"]")).click();
			nativeDriver.findElement(By.xpath("//*[@resourceid=\"com.car2go:id/action_locate_me\"]")).click();
	
			visualDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			visualDriver.manageMobile().visualOptions().validationOptions().setThreshold(75);
			visualDriver.findElement(By.linkText("Car2Go"));
			System.out.println("Car displayed");
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}	
		device.getNativeDriver(appIdentifier).close();	
		device.close();
  }
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	 
	private static void downloadReport(IMobileDriver driver, String reportname) {
         InputStream reportStream = driver.downloadReport(MediaType.HTML);
         if (reportStream != null) {
                         File reportFile = new File("C:\\Results\\"+reportname);
                         try {
							FileUtils.write(reportStream, reportFile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
 }
}
	private static void obtainReport() {
		InputStream reportStream = ((IMobileDriver) driver).downloadReport(MediaType.HTML);

		if (reportStream != null) {
			File reportFile = new File(Constants.REPORT_LIB+"TestNG_"+_Device+".HTML");
			try {
				FileUtils.write(reportStream, reportFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Reporter.log( Constants.REPORT_LIB+"TestNG_"+_Device+".HTML");

			String filename =Constants.REPORT_LIB+"TestNG_"+_Device+".HTML"  ;
			//	Reporter.log("</br><b>Report:</b> <a href=" + filename +">Report</a>");

			try {
				BufferedReader br = new BufferedReader(new FileReader(filename));

				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				Reporter.log("<DIV valign=\"top\" align=\"left\" style=\"font-family: Verdana; font-style: normal; font-variant: normal; font-weight: normal; font-size: 10pt; color: black; text-indent: 0em; letter-spacing: normal; word-spacing: normal; text-transform: none;margin-top: 0pt; margin-bottom: 20pt; height: 3.146in; width: 10.562in; white-space: normal; line-height: normal\">");

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				Reporter.log(sb.toString());

				Reporter.log("</DIV>");

				br.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
  
}
