package com.shift.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Util {
	public void scrollUntilVisible(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
    }
	
	public void waitUntilPageIsReady(WebDriver driver) {
        try {
        	WebDriverWait wait = new WebDriverWait(driver, 30); // You can adjust the timeout as needed
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public String getFilePath(String fileName) {
		String csvFilePath = "";
		try {
			// Get the resource file path
	        URL resource = getClass().getClassLoader().getResource(fileName);
	        // Convert the URL to a File object
	        File file = new File(resource.toURI());
	        // Now, you can use the file.getAbsolutePath() to get the absolute path
	        csvFilePath = file.getAbsolutePath();
		}catch(Exception e) {
            e.printStackTrace();
        }
		return csvFilePath;
	}
	
	public void installApp(String appName) {
		try {
            String installCommand = "cmd /c start /wait "+appName;

            // Run the command
            Process process = new ProcessBuilder("cmd", "/c", installCommand).start();
            
            // Wait for the installation to complete (optional)
            int exitCode = process.waitFor();

            // Check the exit code to determine if the installation was successful
            if (exitCode == 0) {
                System.out.println("Shift installation successful.");
            } else {
                System.out.println("Shift installation failed. Exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
	}
}
