/**
 * 
 */
package com.karma.init;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

/**
 * @author Ankit P
 *
 */
public class TestCommons {

	public static void type(AndroidDriver<MobileElement> driver, MobileElement element, String text) {

		element.clear();

		hideKeyboard(driver);

		element.sendKeys(text);

		hideKeyboard(driver);

		pause(1);
	}

	public static void hideKeyboard(AndroidDriver<MobileElement> driver) {
		try {
			//driver.hideKeyboard();
		} catch (Exception e) {
			System.err.println("Keyboard Hidden Already");
		}
	}

	public static int getRandomNumber(int maxNum) {
		return new Random().nextInt(maxNum) + 1;
	}

	public static void pause(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			System.err.println("Error in Thread - Wait");
			e.printStackTrace();
		}
	}

	public static void addLabel(AndroidDriver<MobileElement> driver, String msg) {

		if (AppiumInit.isAddLabel)
		{
			//driver.label(msg);
		}
		else {
			TestCommons.log(msg);
		}

	}

	public static String generateRandomChars(int length) {
		String random = RandomStringUtils.randomAlphabetic(length);
		return random;
	}
	
	public static String generateRandomNum(int length) {
		String random = RandomStringUtils.randomNumeric(length);
		return random;
	}
	
	public static int randBetween(int start, int end) {
		  return start + (int) Math.round(Math.random() * (end - start));
	}
	
	public static String getTestDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c= Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH,7);
        String newDate=sdf.format(c.getTime());
		return newDate;
	}
	
	public static WebElement waitForElement(WebDriver androidWebDriver,WebElement element)
	{
		WebDriverWait wait=new WebDriverWait(androidWebDriver, 20);
		WebElement waitElement=wait.until(ExpectedConditions.elementToBeClickable(element));
		return waitElement;
	}
	
	public static WebElement waitForElementVisible(WebDriver androidWebDriver,String xpath1)
	{
		WebDriverWait wait=new WebDriverWait(androidWebDriver, 60);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath1)));
		WebElement waitElement = androidWebDriver.findElement(By.xpath(xpath1)); 
		return waitElement;
	}
		
	public static boolean isElementDisplayed(WebDriver androidDriver, MobileElement element) {
		try {
			if(element.isDisplayed())
			{
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getCurrentTimeStampString() {

		java.util.Date date = new java.util.Date();

		SimpleDateFormat sd = new SimpleDateFormat("MMddHHmmssSS");
		TimeZone timeZone = TimeZone.getDefault();
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(timeZone.getOffset(date.getTime()), "GMT"));
		sd.setCalendar(cal);
		return sd.format(date);
	}
	
	public static void navigateBack(AndroidDriver<MobileElement> androidDriver) {
		pause(3);
		TestCommons.log("Navigate back.");
		TestCommons.addLabel(androidDriver, "Navigate back.");
		androidDriver.navigate().back();
		pause(6);
	}
	
	public static void logVerification(String log)
	{
		System.out.println(""+log+"</br>");
		Reporter.log("<b style='color:DarkCyan;'>"+log+"</b></br>");
	}
	
	public static void logCase(String log)
	{
		System.out.println(""+log+"</br>");
		Reporter.log("<h4 style='color:Purple;'>"+log+"</h4>");
	}
	
	public static void logStep(String log)
	{
		System.out.println("<br>"+log+"</br>");
		Reporter.log("<br><b>"+log+"</b></br>");
	}
	
	public static void log(String log)
	{
		System.out.println("<br>"+log+"</br>");
		Reporter.log("<br>"+log+"</br>");
	}
	
	public static void makeScreenshot(WebDriver driver, String screenshotName) {

		WebDriver augmentedDriver = new Augmenter().augment(driver);

		/* Take a screenshot */
		File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		String nameWithExtention = screenshotName + ".png";

		/* Copy screenshot to specific folder */
		try {
			/*
			 * String reportFolder = "target" + File.separator +
			 * "failsafe-reports" + File.separator + "firefox" + File.separator;
			 */
			String reportFolder = "test-output" + File.separator;
			String screenshotsFolder = "screenshots";
			File screenshotFolder = new File(reportFolder + screenshotsFolder);
			if (!screenshotFolder.getAbsoluteFile().exists()) {
				screenshotFolder.mkdir();
			}
			FileUtils.copyFile(screenshot,
					new File(screenshotFolder + File.separator + nameWithExtention).getAbsoluteFile());
		} catch (IOException e) {
			log("Failed to capture screenshot: " + e.getMessage());
		}
		log(getScreenshotLink(nameWithExtention, nameWithExtention)); // add
		
	}
	
	public static String getScreenshotLink(String screenshot_name, String link_text) {

		log("<br><Strong><font color=#FF0000>--Failed</font></strong>");
		return "<a href='../test-output/screenshots/" + screenshot_name + "' target='_new'>" + link_text + "</a>";
	}
	
	public static void switchtoNative(AndroidDriver<MobileElement> driver)
	{
		Set<String> contexts = driver.getContextHandles();
		for (String context : contexts) {
			//System.out.println("context: " + context);
			//System.out.println("Is Native :" + context.equals("NATIVE_APP"));
			/*
			 * if (!context.equals("NATIVE_APP")) { driver.context(context); }
			 */

		}
		
		driver.context("NATIVE_APP");
	}
	
	public static MobileElement getElementBytext(AndroidDriver<MobileElement> driver,String text)
	{
		return driver.findElementByXPath("//*[contains(@text,'"+text+"')]");
	}
	
	public static void moveSeekbar(AndroidDriver<MobileElement> driver)
	{
		
		WebElement seekBar = driver.findElementByClassName("android.widget.SeekBar");
		
		int startX = seekBar.getLocation().getX();
	   // System.out.println(startX);
	    //Get end point of seekbar.
	    int endX = seekBar.getSize().getWidth();
	   // System.out.println(endX);
	    //Get vertical location of seekbar.
	    int yAxis = seekBar.getLocation().getY();
	    //Set slidebar move to position.
	    // this number is calculated based on (offset + 3/4width)
	    int moveToXDirectionAt = endX-160;
	   // System.out.println("Moving seek bar at " + moveToXDirectionAt+" In X direction.");
	    //Moving seekbar using TouchAction class.
	   
	    TouchAction act=new TouchAction(driver);
	    PointOption pointop = new PointOption();
	   
	    act.longPress(pointop.point(startX, yAxis)).moveTo( pointop.point(moveToXDirectionAt, yAxis)).release().perform();
	}
	
	public static void EditmoveSeekbar(AndroidDriver<MobileElement> driver)
	{
		
		WebElement seekBar = driver.findElementByClassName("android.widget.SeekBar");
		
		int startX = seekBar.getLocation().getX();
	   // System.out.println(startX);
	    //Get end point of seekbar.
	    int endX = seekBar.getSize().getWidth();
	   // System.out.println(endX);
	    //Get vertical location of seekbar.
	    int yAxis = seekBar.getLocation().getY();
	    //Set slidebar move to position.
	    // this number is calculated based on (offset + 3/4width)
	    int moveToXDirectionAt = endX-100;
	   // System.out.println("Moving seek bar at " + moveToXDirectionAt+" In X direction.");
	    //Moving seekbar using TouchAction class.
	   
	    TouchAction act=new TouchAction(driver);
	    PointOption pointop = new PointOption();
	   
	    act.longPress(pointop.point(startX, yAxis)).moveTo( pointop.point(moveToXDirectionAt, yAxis)).release().perform();
	}
	
	public static void switchtoWebview(AndroidDriver<MobileElement> driver)
	{
		Set<String> contexts = driver.getContextHandles();
		for (String context : contexts) {
			System.out.println("context: " + context);
			//System.out.println("Is Native :" + context.equals("NATIVE_APP"));
			/*
			 * if (!context.equals("NATIVE_APP")) { driver.context(context); }
			 */

		}
		
		driver.context("WEBVIEW_com.kiddopay");
	}
	
	public static void scrollByDirection(AndroidDriver<MobileElement> driver,String direction)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", direction);
		js.executeScript("mobile: scroll", scrollObject);
	}
	
	public static void scrollvertically(AndroidDriver<MobileElement> driver)
	{
		
		Dimension dimens = driver.manage().window().getSize();
		int x = (int) (dimens.getWidth());
		int startY = (int) (dimens.getHeight()*0.8);
		int endY = (int) (dimens.getHeight()*0.2);
		// lest say if above code calculated correct scroll area as per your device then just add static loop to scroll to no of times you want to scroll
	   
	    TouchAction act=new TouchAction(driver);
	    PointOption pointop = new PointOption();
	   
	    act.press(pointop.point(x/2, startY))
	    .waitAction( WaitOptions.waitOptions(Duration.ofSeconds(2) ) )
	    .moveTo( pointop.point(x/2, endY))
	    .release().perform();
	}
	
	public static AndroidDriver<MobileElement> openApplication()
	{
		
		AndroidDriver<MobileElement> newAndroidDriver;
		
		
		//DesiredCapabilities cap= new DesiredCapabilities();
		URL remote_grid = null ;
		try {
			remote_grid = new URL("http://" + "localhost" + ":" + "4723" + "/wd/hub");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("app", AppiumInit.apkFile.getAbsolutePath());
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "7.1.1");
		caps.setCapability("udid","emulator-5554");
		caps.setCapability("deviceName", "Pixel 2");
		//caps.setCapability("fullReset", "true");
		caps.setCapability("automationName", "Appium");
		//caps.setCapability("chromedriverExecutable", "D:\\Ankit.P\\Softwares\\chromedriver_Appium.exe");
		caps.setCapability("autoGrantPermissions", true);
		caps.setCapability("noReset", false);
		caps.setCapability("newCommandTimeout", 900);
		//caps.setCapability("autoWebview", "true");
		
		
		
		//androidDriver = Factory.createAndroidDriver(remote_grid, cap);
		newAndroidDriver = new AndroidDriver<MobileElement>(remote_grid, caps);
		newAndroidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		return newAndroidDriver;
	}
	
	
	public static WebDriver openbrowser(String emial)
	{
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setBrowserName("chrome");
		File chromedriver = new File("Resources/chromedriver_windows_chrome74.exe");
		System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());
		
		WebDriver chromdriver = new ChromeDriver();
		String[] email= emial.split("@");
		
		chromdriver.get("https://www.mailinator.com/v3/index.jsp?zone=public&query="+email[0]);
		
		return chromdriver;
	}
	
	public static String mainWindow = "";
	public static void switchWindows(WebDriver driver)
	{
		 mainWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		
		for(String handle : handles)
		{
			driver.switchTo().window(handle);
		}
	}
	
	public static void returntoMainwindow(WebDriver driver)
	{
		driver.switchTo().window(mainWindow);
	}
	
	public static void scrollverticallyUp(AndroidDriver<MobileElement> driver)
	{
		
		Dimension dimens = driver.manage().window().getSize();
		int x = (int) (dimens.getWidth());
		int startY = (int) (dimens.getHeight()*0.8);
		int endY = (int) (dimens.getHeight()*0.2);
		// lest say if above code calculated correct scroll area as per your device then just add static loop to scroll to no of times you want to scroll
	   
	    TouchAction act=new TouchAction(driver);
	    PointOption pointop = new PointOption();
	   
	    act.press(pointop.point(x/2, endY))
	    .waitAction( WaitOptions.waitOptions(Duration.ofSeconds(2) ) )
	    .moveTo(pointop.point(x/2, startY))
	    .release().perform();
	}
	
	public static AndroidDriver<MobileElement> openchromeLogoutFB(AndroidDriver<MobileElement> newAndroidDriver)
	{
		
		//DesiredCapabilities cap= new DesiredCapabilities();
		URL remote_grid = null ;
		try {
			remote_grid = new URL("http://" + "localhost" + ":" + "4723" + "/wd/hub");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File chromeDriver = new File("Resources/chromedriver_forChrome61.exe");
		DesiredCapabilities caps = new DesiredCapabilities();
		//caps.setCapability("app", AppiumInit.apkFile.getAbsolutePath());
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", AppiumInit.platformVersion);
		caps.setCapability("deviceName", "Pixel 2");
		//caps.setCapability("fullReset", "true");
		caps.setCapability("automationName", "Appium");
		caps.setCapability("chromedriverExecutable", chromeDriver.getAbsolutePath());
		caps.setCapability("browserName", "Chrome");
		caps.setCapability("autoGrantPermissions", true);
		caps.setCapability("noReset", false);
		caps.setCapability("newCommandTimeout", 900);
		//caps.setCapability("autoWebview", "true");
		
		
		
		//androidDriver = Factory.createAndroidDriver(remote_grid, cap);
		newAndroidDriver.quit();
		newAndroidDriver = new AndroidDriver<MobileElement>(remote_grid, caps);
		newAndroidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		newAndroidDriver.get("http://m.facebook.com/logout.php");
		return newAndroidDriver;
	}
}
