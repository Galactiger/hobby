package com.corey;

import jdk.jfr.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;

/**
 * TestNG test class for signing in to Google.
 * @author Corey
 * @version 1 - Aug. 21 2019 - ~4pm Pacific
 */
public class GoogleSignInTest {
    //private static GeckoDriverService serviceFF;
    private static ChromeDriverService serviceGC;
    protected static final String FF_EXE_LOCATION = "C:\\Users\\galac\\Downloads\\geckodriver-v0.24.0-win64\\geckodriver.exe";
    protected static final String GC_EXE_LOCATION = "C:\\Users\\galac\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe";
    protected static final String GOOGLE_START_ADDRESS = "https://www.google.com";
    protected static final By GOOGLE_SIGNIN_LINK = By.linkText("Sign in");
    protected static final By GOOGLE_USERNAME_FIELD = By.tagName("input");
    protected static final By GOOGLE_PASSWORD_FIELD = By.xpath("//div[@id='password']//input[@name='password']");
    protected static final String GOOGLE_USERNAME = "this.gal.s.cancer.allies";
    protected static final String GOOGLE_PASSWORD = "g3m1n1stw1n!";
    protected static final int SHORT_WAIT = 2000;
    protected static final By GOOGLE_ACCOUNT_DROPDOWN_BUTTON_LINK = By.xpath("//a[@href='https://accounts.google.com/SignOutOptions?hl=en&continue=https://www.google.com/' and @role='button']");
    protected static final By GOOGLE_ACCOUNT_DROPDOWN = By.xpath("//a[@href='https://accounts.google.com/SignOutOptions?hl=en&continue=https://www.google.com/' and @role='button']//span");
    protected static final By GOOGLE_SIGNOUT_LINK = By.xpath("//a[contains(text(),'Sign out') and @target='_top']");
    protected static final By GOOGLE_SIGNOUT_BUTTON = By.xpath("//button[@id='signout']");
    protected static final By GOOGLE_DROPDOWN_SIGNOUT_BUTTON = By.linkText("Sign out");

    /**
     * Set up WebDriver executable locations and start Google Chrome service.
     * @throws IOException
     */
    @BeforeSuite
    @Description(value = "Sets up where to find WebDriver executables and how to instantiate Google Chrome service")
    public void setUp() throws IOException {
        System.setProperty("webdriver.gecko.driver", FF_EXE_LOCATION);
        //serviceFF = new GeckoDriverService.Builder()
        //        .usingAnyFreePort()
        //        .build();
        //serviceFF.start();
        serviceGC = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(GC_EXE_LOCATION))
                .usingAnyFreePort()
                .build();
        serviceGC.start();

    }

    /**
     * General sign in for Google.
     * @param driver initialized WebDriver instance
     * @throws InterruptedException
     */
    public void gSignInHelper(WebDriver driver) throws InterruptedException {
        final WebElement gSignInLink;
        final WebElement gUsernameField;
        final WebElement gPasswordField;
        driver.get(GOOGLE_START_ADDRESS);
        gSignInLink = driver.findElement(GOOGLE_SIGNIN_LINK);
        gSignInLink.click();
        gUsernameField = driver.findElement(GOOGLE_USERNAME_FIELD);
        gUsernameField.sendKeys(GOOGLE_USERNAME);
        gUsernameField.sendKeys(Keys.RETURN);
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(GOOGLE_PASSWORD_FIELD));
        gPasswordField = driver.findElement(GOOGLE_PASSWORD_FIELD);
        gPasswordField.sendKeys(GOOGLE_PASSWORD);
        gPasswordField.sendKeys(Keys.RETURN);
    }

    /**
     * General sign out for Google.
     * @param driver initialized WebDriver instance
     */
    public void gSignOutHelper(WebDriver driver){
        final WebElement gDropdownButton;
        final WebElement gSignOutButton;
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(GOOGLE_ACCOUNT_DROPDOWN_BUTTON_LINK));
        gDropdownButton = driver.findElement(GOOGLE_ACCOUNT_DROPDOWN_BUTTON_LINK);
        gDropdownButton.click();
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(GOOGLE_SIGNOUT_BUTTON));
        gSignOutButton = driver.findElement(GOOGLE_SIGNOUT_BUTTON);
        gSignOutButton.click();
    }

    /**
     * Modified general sign out for Google on Mozilla Firefox.
     * @param driver initialized WebDriver instance
     */
    public void gSignOutHelperFF(WebDriver driver) throws InterruptedException{
        final WebElement gDropdownButton;
        final WebElement gSignOutLink;
        final WebElement gSignOutButton;
        final Actions builder;
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(GOOGLE_ACCOUNT_DROPDOWN_BUTTON_LINK));
        gDropdownButton = driver.findElement(GOOGLE_ACCOUNT_DROPDOWN_BUTTON_LINK);
        builder = new Actions(driver);
        builder.moveToElement(gDropdownButton).click().build().perform();
        //builder.click(gDropdownButton).build().perform();
        //builder.moveToElement(gDropdownButton).click().build().perform();
        //gDropdownButton.click();
        //gDropdownButton.sendKeys(Keys.RETURN);
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(GOOGLE_DROPDOWN_SIGNOUT_BUTTON));
        gSignOutButton = driver.findElement(GOOGLE_DROPDOWN_SIGNOUT_BUTTON);
        gSignOutButton.click();
    }

    /**
     * Modified general sign out for Google on Mozilla Firefox.
     * @param driver initialized WebDriver instance
     */
    public void gSignOutHelperFF2(WebDriver driver) {
        final WebElement gDropdownButton;
        final WebElement gSignOutLink;
        final WebElement gSignOutButton;
        driver.manage().window().maximize();
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(GOOGLE_ACCOUNT_DROPDOWN));
        gDropdownButton = driver.findElement(GOOGLE_ACCOUNT_DROPDOWN);
        jsExecutor.executeScript("arguments[0].click();", gDropdownButton);
        jsExecutor.executeScript("arguments[0].click();", gDropdownButton);
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(GOOGLE_DROPDOWN_SIGNOUT_BUTTON));
        gSignOutButton = driver.findElement(GOOGLE_DROPDOWN_SIGNOUT_BUTTON);
        gSignOutButton.click();
    }

    /**
     * Modified general sign out for Google on Google Chrome.
     * @param driver initialized WebDriver instance
     */
    public void gSignOutHelperGC(WebDriver driver) {
        final WebElement gDropdownButton;
        final WebElement gSignOutLink;
        final WebElement gSignOutButton;
        final Actions builder = new Actions(driver);
        driver.manage().window().maximize();
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(GOOGLE_ACCOUNT_DROPDOWN_BUTTON_LINK));
        gDropdownButton = driver.findElement(GOOGLE_ACCOUNT_DROPDOWN_BUTTON_LINK);
        builder.moveToElement(gDropdownButton).click().build().perform();
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(GOOGLE_DROPDOWN_SIGNOUT_BUTTON));
        gSignOutButton = driver.findElement(GOOGLE_DROPDOWN_SIGNOUT_BUTTON);
        gSignOutButton.click();
    }

    /**
     * Test for starting the browser, signing in and out of Google, and then quitting the browser,
     * using Mozilla Firefox browser.
     * @throws InterruptedException
     *
     */
    @Test(groups = { "basic" })
    @Description(value = "Signs in to Google using Mozilla Firefox browser")
    public void gSignInAndOutTestFF() throws InterruptedException {
        final WebDriver driver = new FirefoxDriver();
        gSignInHelper(driver);
        gSignOutHelperFF(driver);
        driver.quit();
    }

    /**
     * Test for starting the browser, signing in and out of Google, and then quitting the browser,
     * using Google Chrome service.
     * @throws InterruptedException
     */
    @Test(groups = { "basic" })
    @Description(value = "Signs in to Google using Google Chrome service")
    public void gSignInAndOutTestGC() throws InterruptedException {
        final WebDriver driver = new ChromeDriver(serviceGC);
        gSignInHelper(driver);
        gSignOutHelperGC(driver);
        driver.quit();
    }

    /**
     * Stop the Google Chrome service.
     */
    @AfterSuite
    @Description(value = "Stop the service needed by the Google Chrome tests")
    public static void stopService() {
        serviceGC.stop();
    }
}
