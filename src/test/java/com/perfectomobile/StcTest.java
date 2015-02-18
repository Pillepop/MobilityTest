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
import com.perfectomobile.selenium.MobileDriver;
import com.perfectomobile.selenium.MobileDevice;
import com.perfectomobile.selenium.MobileDriver;
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
  
	@Test(dataProvider = "dp")
	public void f(Integer n, String s) {
	  System.out.println("test run "+s);
	  //uploadLatestBuild();
	  //install(s);
	  testrun(s);
	  }
	
  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { 1, "1E674EB8" },
      new Object[] { 2, "219595A5" },
    };
  }
  @BeforeMethod
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
  }

  @BeforeTest
  public void beforeTest() {
	  driver  = new MobileDriver("demo.perfectomobile.com", "philipps@perfectomobile.com", "Perfect0123");
  }

  @AfterTest
  public void afterTest() {
	  device.close();
	  System.out.println("finished");
	  driver.quit();
	  downloadReport(driver, "results.html");
  }

  @BeforeSuite
  public void beforeSuite() {
  }

  @AfterSuite
  public void afterSuite() {
  }

  public void uploadLatestBuild(){
	  try {
		 driver.uploadMedia("PUBLIC:Philipp\\builds\\STC.apk","C:\\mybuild\\MySTC_com.stc_2.4.0_46.apk");
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.getMessage());
	}
  }

public void install(String deviceID){
	  device = driver.getDevice(deviceID);
	  
	  try {
		  device.installApplication("PUBLIC:Philipp\\builds\\STC.apk");
		  MobileApplicationInstallOptions installOptions9 = new MobileApplicationInstallOptions();
		  installOptions9.setInstrument(false);
		  device.installApplication("PUBLIC:Philipp\\builds\\STC.apk", installOptions9);


		
	} catch (Exception e) {
		System.out.println(e.getMessage());
		// TODO: handle exception
	}
}
  
  public void testrun(String deviceID){
	  //MobileDriver driver = new MobileDriver();
	 
	 // IMobileDevice device = driver.getDevice("219595A5"); //S4
	  device = driver.getDevice(deviceID); //S5
	  MobileDeviceOpenOptions openOptions2 = new MobileDeviceOpenOptions();
	  device.open(openOptions2);
	  MobileDeviceHomeOptions homeOptions1 = new MobileDeviceHomeOptions();
	  device.home(homeOptions1);

	  IMobileWebDriver visualDriver = device.getVisualDriver();
      IMobileWebDriver webDriver = 	device.getDOMDriver(MobileBrowserType.DEFAULT);
      IMobileWebDriver nativeDriver = device.getNativeDriver();
      	  
      device.getNativeDriver("MySTC").open();
      visualDriver.findElementByLinkText("MySTC Account");
		visualDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		visualDriver.manageMobile().visualOptions().validationOptions().setThreshold(90);
		
		try{
		visualDriver.findElement(By.linkText("MyStc account"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}		
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
