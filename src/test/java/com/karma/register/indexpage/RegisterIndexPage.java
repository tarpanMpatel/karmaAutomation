package com.karma.register.indexpage;

import org.openqa.selenium.By;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

import com.karma.init.TestCommons;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;


public class RegisterIndexPage{

	public void getFocus(AndroidDriver<MobileElement> androidDriver) {
		androidDriver.pressKeyCode(187);
		TestCommons.pause(2);
		androidDriver.pressKeyCode(AndroidKeyCode.BACK);
	}
	
	
	public void clickRegister(AndroidDriver<MobileElement> androidDriver) {
		// TODO Auto-generated method stub
		getFocus(androidDriver);
		System.out.println("...Back to Application...");
		
		MobileElement btnRegister = androidDriver.findElement(By.xpath("//android.widget.TextView[@text='REGISTER']"));
		
		btnRegister.click();
		TestCommons.pause(1);
		TestCommons.log("Tap on 'Register' Button.");
	}
	
	public void tapCountryFlag(AndroidDriver<MobileElement> androidDriver) {
		MobileElement countryFlag = androidDriver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"phoneContainer-testId\"]/android.view.ViewGroup/android.widget.ImageView"));
		countryFlag.click();
		TestCommons.pause(1);
		TestCommons.log("Tap on 'Country flag' .");
	}

}
