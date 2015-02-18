package com.perfectomobile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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
import com.perfectomobile.selenium.by.*;


public class StcTest {
	private  MobileDriver driver;
	private IMobileDevice device;
	//private String repositoryPath = "PUBLIC:Philipp\\builds\\STC.apk";
	//private String localPath = "C:\\mybuild\\MySTC_com.stc_2.4.0_46.apk";
	//private String appIdentifier = "MySTC";
	private String repositoryPath = "PUBLIC:Philipp\\builds\\car2go_com.car2go_2.2.3_20203.apk";
	private String localPath = "C:\\mybuild\\car2go_com.car2go_2.2.3_20203.apk";
	private String appIdentifier = "car2go";
	

	@Test(dataProvider = "dp")
	public void f(Integer n, String s) {
	  
	  System.out.println("test run "+s);
	  device = driver.getDevice(s);
	  device.open();	  
	 	  
//	  install(s);
	  testCar2Go(s);
	  }

	
	  @DataProvider
	  public Object[][] dp() {
	    return new Object[][] {
	      new Object[] { 1, "1E674EB8" },
	      new Object[] { 2, "219595A5" },
	    };
	  }
 /* @BeforeMethod
  public void beforeMethod() {

  }

  @AfterMethod
  public void afterMethod() {
  }

  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }*/

  @BeforeTest
  public void beforeTest() {
	  driver  = new MobileDriver("demo.perfectomobile.com", "philipps@perfectomobile.com", "Perfect0123");
	  uploadLatestBuild();
  }

  @AfterTest
  public void afterTest() {
	  
	  System.out.println("finished");
	  downloadReport(driver, "results.html");
	  driver.quit();
	  
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
//		  MobileApplicationInstallOptions installOptions9 = new MobileApplicationInstallOptions();
//		  installOptions9.setInstrument(false);
//		  device.installApplication("PUBLIC:Philipp\\builds\\STC.apk", installOptions9);
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

	  IMobileWebDriver visualDriver = device.getVisualDriver();
      IMobileWebDriver webDriver = 	device.getDOMDriver(MobileBrowserType.DEFAULT);
      IMobileWebDriver nativeDriver = device.getNativeDriver();
      	  
      device.getNativeDriver(appIdentifier).open();		
		
		try{
			visualDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			visualDriver.manageMobile().visualOptions().validationOptions().setThreshold(95);
			visualDriver.findElement(By.linkText("Google-Kartendaten 2015 Google, GeoBasis-DE/BKG"));

/*			MobileLocation location1 = new MobileAddressLocation("Münchner Freiheit, München");
			device.setLocation(location1);

			webDriver.findElement(By.xpath("//*[@contentDesc=\"Position bestimmen\"]")).click();
			sleep(3000);*/
			MobileLocation location2 = new MobileAddressLocation("Wilhelm-Runge-Straße 11, Ulm");
			driver.getDevice("1E674EB8").setLocation(location2);

			webDriver.findElement(By.xpath("//*[@contentDesc=\"Position bestimmen\"]")).click();
			
			visualDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			visualDriver.manageMobile().visualOptions().validationOptions().setThreshold(80);
			visualDriver.findElement(By.linkText("Car2Go"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}		
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
  
}
