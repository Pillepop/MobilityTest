package com.perfectomobile;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import com.perfectomobile.selenium.MobileDriver;
import com.perfectomobile.selenium.MobileDevice;
import com.perfectomobile.selenium.MobileDriver;
import com.perfectomobile.selenium.api.IMobileDevice;
import com.perfectomobile.selenium.api.IMobileWebDriver;
import com.perfectomobile.selenium.options.MobileBrowserType;
import com.perfectomobile.selenium.options.MobileDeviceHomeOptions;
import com.perfectomobile.selenium.options.MobileDeviceOpenOptions;
import com.perfectomobile.selenium.by.*;


public class StcTest {
  @Test
  public void f() {
	  System.out.println("test run");
	  testrun();
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
  }

  @AfterTest
  public void afterTest() {
  }

  @BeforeSuite
  public void beforeSuite() {
  }

  @AfterSuite
  public void afterSuite() {
  }

  public void testrun(){
	  //MobileDriver driver = new MobileDriver();
	  MobileDriver driver = new MobileDriver("demo.perfectomobile.com", "philipps@perfectomobile.com", "Perfect0123");
	 // IMobileDevice device = driver.getDevice("219595A5"); //S4
	  IMobileDevice device = driver.getDevice("1E674EB8"); //S5
	  MobileDeviceOpenOptions openOptions2 = new MobileDeviceOpenOptions();
	  device.open(openOptions2);
	  MobileDeviceHomeOptions homeOptions1 = new MobileDeviceHomeOptions();
	  device.home(homeOptions1);

	  IMobileWebDriver visualDriver = device.getVisualDriver();
      IMobileWebDriver webDriver = 	device.getDOMDriver(MobileBrowserType.DEFAULT);
      IMobileWebDriver nativeDriver = device.getNativeDriver();
      	  

	  sleep(3000);
	  visualDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  visualDriver.findElement(By.linkText("Email Kamera"));
	  
	  device.close();
	  System.out.println("finished");
	  

  }
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
  
}
