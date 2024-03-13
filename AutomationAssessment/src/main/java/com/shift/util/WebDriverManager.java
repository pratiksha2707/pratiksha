package com.shift.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverManager {

    private static WebDriver driver;
    static Util util = new Util();
    
    static String chromeBinaryPath = "Chrome\\chrome-win64\\chrome.exe";
    static String chromeDriverPath = "Drivers\\chromedriver.exe";

    public static WebDriver getDriver() {
        if (driver == null) {
            setupChromeDriver(util.getFilePath(chromeDriverPath));
            ChromeOptions options = new ChromeOptions();
    		options.setBinary(util.getFilePath(chromeBinaryPath));
    		options.addArguments("--remote-allow-origins=*");
    		driver = new ChromeDriver(options);
        }
        return driver;
    }

    private static void setupChromeDriver(String chromeDriverPath) {
        if (chromeDriverPath != null && !chromeDriverPath.isEmpty()) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        } else {
        	 io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
