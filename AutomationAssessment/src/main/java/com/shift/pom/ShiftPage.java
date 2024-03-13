package com.shift.pom;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import com.shift.util.Util;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

public class ShiftPage {
    private final WebDriver driver;
    private By fullName = By.xpath("//input[@placeholder='Full Name']");
    private By date = By.id("date_range");
    private By find = By.xpath("//button[text()='Find']");
    private By records = By.xpath("//ul[contains(@class,'table-row-group')]//li");
    private By sortName = By.xpath("//span[text()='Sort by Name']//parent::div");
    private By sortDob = By.xpath("//span[text()='Sort by DOB']//parent::div");
    private By sortDod = By.xpath("//span[text()='Sort by DOD']//parent::div");
    private By firstName = By.xpath("//ul[contains(@class,'table-row-group')]/li[1]//span[1]");
    private By firstDobDod = By.xpath("//ul[contains(@class,'table-row-group')]/li[1]//span[2]");
    private By ok = By.xpath("//button[text()='OK']");
    
    Util util = new Util();
    
    public ShiftPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public WindowsDriver<WindowsElement> launchShiftBrowser() {
    	String shiftBrowserVersion="";
    	DesiredCapabilities appCapabilities = new DesiredCapabilities();
        appCapabilities.setCapability("app", "v10z8vjag6ke6");
        WindowsDriver<WindowsElement> driver=null;
        
        try {
           driver = new WindowsDriver<>(new URL("http://127.0.0.1:4723"), appCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        return driver;
    }
    
    
    public String getShiftBrowserVersion(WindowsDriver<WindowsElement> driver) {
        try {
            WindowsElement versionElement = driver.findElementByName("VersionLabel");
            String shiftBrowserVersion = versionElement.getText();

            return shiftBrowserVersion;

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to retrieve Shift Browser version";
        }
    }
    
    public void open(String url) {
        driver.get(url);
        util.waitUntilPageIsReady(driver);
    }
    
    public void installShift(String appName) {
    	util.installApp(appName);
    }

    public void enterSearchQuery(String query) {
        WebElement searchInput = driver.findElement(fullName);
        util.scrollUntilVisible(driver, searchInput);
        searchInput.clear();
        searchInput.sendKeys(query);
    }
    
    public void clearSearchQuery() {
        WebElement searchInput = driver.findElement(fullName);
        util.scrollUntilVisible(driver, searchInput);
        searchInput.clear();
    }
    
    public void selectDateRange(String range) {
    	// Create a Select object
        Select dropdown = new Select(driver.findElement(date));
        // Select by visible text
        dropdown.selectByVisibleText(range);
    }

    public void submitSearch() {
        WebElement searchButton = driver.findElement(find);
        searchButton.click();
        util.waitUntilPageIsReady(driver);
    }
    
    public int getSearchResultsCount() {
    	util.scrollUntilVisible(driver, driver.findElement(sortDob));
        List<WebElement> resultsCountElement = driver.findElements(records);
        return resultsCountElement.size();
    }
    
    public void clickOnSortName() {
    	WebElement name = driver.findElement(sortName);
    	util.scrollUntilVisible(driver, name);
    	name.click();
    	util.waitUntilPageIsReady(driver);
    }
    
    public void clickOnSortDob() {
    	WebElement name = driver.findElement(sortDob);
    	util.scrollUntilVisible(driver, name);
    	name.click();
    	util.waitUntilPageIsReady(driver);
    }
    
    public void clickOnSortDod() {
    	WebElement name = driver.findElement(sortDod);
    	util.scrollUntilVisible(driver, name);
    	name.click();
    	util.waitUntilPageIsReady(driver);
    }
    
    public String getFirstRecordName() {
    	WebElement name = driver.findElement(firstName);
    	util.scrollUntilVisible(driver,name);
    	return name.getText();
    }
 
    public String getFirstRecordDobDod() {
    	WebElement name = driver.findElement(firstDobDod);
    	util.scrollUntilVisible(driver, name);
    	return driver.findElement(firstDobDod).getText();
    }
    
    public void LoginToGmail(WindowsDriver<WindowsElement> driver, String username, String password) {
        try {
            
            WindowsElement usernameField = driver.findElement(By.name("username"));
            WindowsElement passwordField = driver.findElement(By.name("password"));
            WindowsElement loginButton = driver.findElement(By.name("loginButton"));

            // Input username and password
            usernameField.sendKeys(username);
            passwordField.sendKeys(password);

            // Click on the login button
            loginButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyLoginSuccess(WindowsDriver<WindowsElement> driver) {
        try {
            WindowsElement loggedInIndicator = driver.findElement(By.name("loggedInIndicator")); // Replace with the actual element identification

            return loggedInIndicator.isDisplayed();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getUnreadEmailCount(WindowsDriver<WindowsElement> driver) {
        try {
            WindowsElement unreadEmailCountElement = driver.findElement(By.name("unreadEmailCount"));

            String countText = unreadEmailCountElement.getText().replaceAll("\\D+", "");
            return Integer.parseInt(countText);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void addWorkspace(WindowsDriver<WindowsElement> driver,String workspaceName, String emailAccount) {
        try {
            WindowsElement addButton = driver.findElement(By.name("+"));
            addButton.click();

            driver.findElement(By.name("WorkspaceNameField")).sendKeys(workspaceName);

            WindowsElement emailAccountField = driver.findElement(By.name("EmailAccountField"));
            emailAccountField.sendKeys(emailAccount);

            WindowsElement addWorkspaceButton = driver.findElement(By.name("AddWorkspaceButton"));
            addWorkspaceButton.click();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addMessenger(WindowsDriver<WindowsElement> driver, String username, String password) {
        try {
           
            WindowsElement addButton = driver.findElement(By.name("AddButton"));
            addButton.click();

            WindowsElement messengerButton = driver.findElement(By.name("MessengerButton"));
            messengerButton.click();

            WindowsElement usernameField = driver.findElement(By.name("MessengerUsernameField"));
            WindowsElement passwordField = driver.findElement(By.name("MessengerPasswordField"));
            WindowsElement loginButton = driver.findElement(By.name("MessengerLoginButton"));

            // Input username and password
            usernameField.sendKeys(username);
            passwordField.sendKeys(password);

            // Click on the login button
            loginButton.click();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

