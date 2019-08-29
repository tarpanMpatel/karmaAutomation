package com.karma.init;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public abstract class AbstractPage extends AppiumInit {

	//Common common = new Common(driver);

	public int DRIVER_WAIT = 5;

	/**
	 * Initialize UserAbstractPage.
	 * 
	 * @param Driver
	 *            .
	 */
	public AbstractPage(AndroidDriver<MobileElement> driver) {
		this.androidDriver = driver;
		
	}
	
}