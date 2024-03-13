package com.shift.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.shift.pom.ShiftPage;
import com.shift.util.Util;
import com.shift.util.WebDriverManager;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShiftTest {
    private WebDriver driver;
    private WindowsDriver<WindowsElement> shiftDriver;
    private ShiftPage obituariesPage;
    Util util = new Util();
    String shiftVersion = "";
    String DOWNLOAD_FOLDER_PATH= "C:\\Users\\pande\\Downloads";
    String appName = "";
    String firstUser = "pratiksha.p2703@gmail.com";
    String firstUserPass = "Canada@1234";
    String secUser = "pratikshha2703@hotmail.com";
    String secUserPass = "Canada@1234";
    
    @BeforeMethod
    public void setUp() {
        driver = WebDriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        obituariesPage = new ShiftPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        WebDriverManager.quitDriver();
    }

    @Test(dataProvider = "searchQueries",priority=1)
    public void testSearchFunctionality(String searchQuery, int expectedResultsCount) {
    	obituariesPage.open("https://tryshift.com/");
    	driver.findElement(By.xpath("//a[text()='Download Shift' and @class='btn btn-shift']"));
    	try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 // Check for the presence of Shift download in the download folder
        File downloadFolder = new File(DOWNLOAD_FOLDER_PATH);
        File[] listOfFiles = downloadFolder.listFiles();

        boolean shiftDownloadFound = false;

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().startsWith("shift") && file.getName().endsWith(".exe")) {
                    shiftDownloadFound = true;
                    appName = file.getName();
                    shiftVersion = file.getName().split("-")[1];
                    break;
                }
            }
        }

        // Assertions
        Assert.assertTrue(shiftDownloadFound,"Shift download not found in the download folder.");
        Assert.assertFalse(shiftVersion.isEmpty(), "Shift version could not be extracted.");

        // Additional verifications or actions based on extracted version
        System.out.println("Shift version: " + shiftVersion);
    
    }
    
    @Test(priority=2)
    public void testNameSortFunctionality() {
    	obituariesPage.installShift(appName);
    	shiftDriver = obituariesPage.launchShiftBrowser();
    	String actualVersion = obituariesPage.getShiftBrowserVersion(shiftDriver);
    	Assert.assertEquals(actualVersion, shiftVersion);
    }
    
    @Test(priority=3)
    public void testDobSortFunctionality() {
    	 try {
             obituariesPage.LoginToGmail(shiftDriver, firstUser, firstUserPass);
             boolean loginSuccess = obituariesPage.verifyLoginSuccess(shiftDriver);

             if (loginSuccess) {
                 int unreadEmailCount = obituariesPage.getUnreadEmailCount(shiftDriver);
                 Assert.assertEquals(unreadEmailCount, 3);
             } else {
            	 Assert.assertTrue(false,"Login failed");
             }

             // Close the Shift browser
             driver.quit();

         } catch (Exception e) {
             e.printStackTrace();
         }       
    }
    
   @Test(priority=4)
    public void testDodSortFunctionality() {
	   obituariesPage.addWorkspace(shiftDriver,"workspace1", secUser);
       int unreadEmailCount = obituariesPage.getUnreadEmailCount(shiftDriver);
       Assert.assertEquals(unreadEmailCount, 3);
    }
   
   @Test(priority=5)
   public void testCase5() {
	  obituariesPage.addMessenger(shiftDriver, firstUser, firstUserPass);
      Assert.assertTrue(false,"Login is not successfull");
   }
}
