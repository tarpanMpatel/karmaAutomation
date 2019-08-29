package com.karma.init;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.karma.register.indexpage.RegisterIndexPage;
import com.karma.register.verification.RegisterVerificationPage;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;

public class AppiumInit {

	public AndroidDriver<MobileElement> androidDriver;
	

	public static File apkFile = new File("App/karma-signed.apk");
	public static String platformVersion = "9";

	public static String name = "";

	public static boolean isAddLabel = true;
	public static ConnectionState state;

	
	public static String suiteNameBS ;
	
	String userName = "prakashkhandelwa3";
	String accessKey = "tSXFXGPfD1rACjKx3JZq";
	
	public String testName = "";
	public static String targetBrowser;
	
	public static String appPath = "bs://d63c091abb542c3d86cc2a50898cc8ff615add4b";
	
	public RegisterIndexPage registerIndexPage;
	public RegisterVerificationPage registerVerificationPage;
	
	
	@BeforeSuite(alwaysRun = true)
	public void setCurrentDateTime(ITestContext testContext){
			// String[] spliturl1 = TestData.getURL().split(".falkonry");
			// String splitted = spliturl1[0].replace("https://", "").trim();
			
	//	suiteNameBS	= "["+TestData.readPropertiesFile("Resources/config.properties","testurl")+"] "+testContext.getSuite().getName()+" ["+TestData.getCurrentDateTime()+"]";
		
			suiteNameBS	= "[KARMA][Android] "+testContext.getSuite().getName()+" ["+TestData1.getCurrentDateTime()+"]";
			System.err.println("..#...#....->"+suiteNameBS);
	}
	
	@BeforeTest
	public void setUp(ITestContext testContext) throws Exception {
		
		
		DesiredCapabilities cap = null;
		targetBrowser = testContext.getCurrentXmlTest().getParameter("appium.browser");
		
		if (targetBrowser.contains("local")){
		
					
			  URL remote_grid = new URL("http://" + "localhost" + ":" + "4723" +"/wd/hub");
			  
			  cap = new DesiredCapabilities();
			  
			  cap.setCapability("app",apkFile.getAbsolutePath());
			  cap.setCapability("platformVersion", "9");
			  cap.setCapability("platformName", "Android"); 
			  cap.setCapability("deviceName","Galaxy J8");
			  cap.setCapability("device", "6818e065");
			  cap.setCapability("appPackage", "com.karmareactnativeapp");
			  cap.setCapability("appActivity", "com.karmareactnativeapp.MainActivity");
			  cap.setCapability("autoGrantPermissions", true);
			  cap.setCapability("unicodeKeyboard", true); 
			  cap.setCapability("noReset",false); 
			  cap.setCapability("newCommandTimeout", 700);
			  cap.setCapability("resetKeyboard", true);
			  
			  androidDriver = new AndroidDriver<MobileElement>(remote_grid, cap);
			 
		}
				// BrowserStackSetup

		else if(targetBrowser.contains("browserstack")){
			
		
			String bsURL = "https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
	
			cap = new DesiredCapabilities();
			
			cap.setCapability("device", "Google Nexus 6");
			cap.setCapability("os_version", "6.0");
			cap.setCapability("app", appPath);
			
			cap.setCapability("build",suiteNameBS);
		    cap.setCapability("name", testContext.getName());
			
			cap.setCapability("autoGrantPermissions", true);
			cap.setCapability("unicodeKeyboard", true);
			cap.setCapability("browserstack.debug", "true");
	
			androidDriver = new AndroidDriver<MobileElement>(new URL(bsURL), cap);
			
		}
		
		
			// androidDriver=new AndroidDriver<MobileElement>(remote_grid, cap);
			androidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			/* androidDriver.toggleData(); */
			state = androidDriver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().build());
			System.out.println("Wifi Enabled ...");
	
			//portalIndexPage = new LoginIndexPage();
			//portalVerification = new LoginVerification();
			registerIndexPage = new RegisterIndexPage();
			registerVerificationPage = new RegisterVerificationPage();
			
	
			TestCommons.log("--------------------------------------------------------------------------");
			TestCommons.log("------------------- Initiating the Mobile App Session -------------------");
			TestCommons.log("--------------------------------------------------------------------------");
	
			Thread.sleep(5000);
		
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult) {
		
		try {

			testName = testResult.getName();

			if (!testResult.isSuccess()) {
				markFail();
				/* Print test result to Jenkins Console */
				System.out.println();
				System.out.println("TEST FAILED - " + testName);
				System.out.println();
				System.out.println("ERROR MESSAGE: "
						+ testResult.getThrowable());
				System.out.println("\n");
				Reporter.setCurrentTestResult(testResult);

				/* Make a screenshot for test that failed */
				String screenshotName = TestData1.getCurrentDateTime().replaceAll(":", "")
						+ testName;
				
				System.out.println("========++++++"+TestData1.getCurrentDateTime()+"========++++++++++");
				Reporter.log("<br> <b>Please look to the screenshot - </b>");
				TestCommons.makeScreenshot(androidDriver, screenshotName);
				Reporter.log("<hr size='4px' noshade color='red'>");
			} else {
				
				System.out.println("TEST PASSED - " + testName + "\n"); // Print
																		// test
																		// result
																		// to
																		// Jenkins
																		// Console
				
				markPass();
				Reporter.log("<hr size='4px' noshade color='green'>");
			}

			//driver.manage().deleteAllCookies();
			
			androidDriver.quit();
			//driver1.closeApp();
			androidDriver.resetApp();

		} catch (Throwable throwable) {

		}
		
	}

	
	public void tearDown() {
		androidDriver.quit();
		TestCommons.log("--------------------------------------------------------------------------");
		TestCommons.log("------------------------- Exiting APP -------------------------");
		TestCommons.log("--------------------------------------------------------------------------");

	}
	
	public void markPass() throws URISyntaxException, UnsupportedEncodingException, IOException {
		URI uri = new URI("https://"+userName+":"+accessKey+"@api-cloud.browserstack.com/app-automate/sessions/"+androidDriver.getSessionId().toString()+".json");
		System.err.println(" #...#..PASSED..#....--->>>"+uri.toString());  
		
		HttpPut putRequest = new HttpPut(uri);

		  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		  nameValuePairs.add((new BasicNameValuePair("status", "completed")));
		  nameValuePairs.add((new BasicNameValuePair("reason", "pass")));
		  putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		  HttpClientBuilder.create().build().execute(putRequest);
		}	

	
	public void markFail() throws URISyntaxException, UnsupportedEncodingException, IOException {
		  URI uri = new URI("https://"+userName+":"+accessKey+"@api-cloud.browserstack.com/app-automate/sessions/"+androidDriver.getSessionId().toString()+".json");
		  
		  System.err.println(" #...#..FAILED..#....--->>>"+uri.toString()); 
		  
		  HttpPut putRequest = new HttpPut(uri);

		  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		  nameValuePairs.add((new BasicNameValuePair("status", "error")));
		  nameValuePairs.add((new BasicNameValuePair("reason", "failed")));
		  putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		  HttpClientBuilder.create().build().execute(putRequest);
		}
	

	public void turnONOffWifi(String paar) throws Exception {
		URI uri = new URI(
				"https://"+userName+":"+accessKey+"@api-cloud.browserstack.com/app-automate/sessions/"
						+ ((AndroidDriver) androidDriver).getSessionId().toString() + "/update_network.json");
		System.err.println(" #...#..UPDATED NETWORK..#....--->>>" + uri.toString());

		HttpPut putRequest = new HttpPut(uri);

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add((new BasicNameValuePair("networkProfile", paar)));
		// nameValuePairs.add((new BasicNameValuePair("reason", "pass")));
		putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		HttpClientBuilder.create().build().execute(putRequest);
	}

	public void logStatus(int Status) throws UnsupportedEncodingException, URISyntaxException, IOException {
        //System.out.println(Status);  
        
		if (Status == 1) {
			log(" <Strong><font color=#008000><b> &#10004 PASS</b></font></strong>");		
			//markPass();
			
		} else if (Status == 2) {
			log("<br><Strong><font color=#FF0000><b>&#10008 FAIL</b></font></strong></br>");
			//markFail();
			 /*Make a screenshot for test that failed */
			/*
			 * String screenshotName = Common.getCurrentTimeStampString() + currentTest;
			 * Reporter.log("<br> <b>Please look to the screenshot - </b>");
			 * Common.makeScreenshot(appiumDriver, screenshotName);
			 */
		}
	}
	
	
	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	public static void log(String msg) {
		System.out.println(msg);
		Reporter.log("<br></br>" + msg);
	}
	public static void logList(ArrayList<String> msg) {
		System.out.println(msg);
		Reporter.log("<br></br>" + msg);
	}
	public static void testDescription(String msg) {
		System.out.println(msg);
		Reporter.log("<strong> <h4 style=\"color:DarkViolet\"> " +"Testcase Description: "+ msg
				+ "</h4> </strong>");
	}
	public static void testcaseId(String msg) {
		System.out.println(msg);
		Reporter.log("<strong> <h4 style=\"color:DarkViolet\"> " +"Test Case ID: "+ msg
				+ "</h4> </strong>");
	}
	public static void logverification(int i , String msg) {
		System.out.println(msg);
		Reporter.log("<br></br><b style=\"color:OrangeRed \"> Expected Result-"+i+": </b><b>"+msg + "</b>");
	}
	
}
