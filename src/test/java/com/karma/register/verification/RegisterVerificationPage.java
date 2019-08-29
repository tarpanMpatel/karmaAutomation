package com.karma.register.verification;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.internal.annotations.IAnnotationFinder;

import com.karma.init.TestCommons;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class RegisterVerificationPage{

	public boolean verifyAppLaunch(AndroidDriver<MobileElement> androidDriver) {
		MobileElement btnRegister = androidDriver.findElement(By.xpath("//android.widget.TextView[@text='REGISTER']"));
		MobileElement btnLogin = androidDriver.findElement(By.xpath("//android.widget.TextView[@text='LOGIN']"));
		return btnRegister.isDisplayed() && btnLogin.isDisplayed();	
	}
	
		
	public boolean verifyRegisterPage(AndroidDriver<MobileElement> androidDriver) {
		MobileElement btnRegister = androidDriver.findElementByAccessibilityId("phone-testId");
		MobileElement pswd = androidDriver.findElementByAccessibilityId("password-testId");
		MobileElement termsOf = androidDriver.findElement(By.xpath("//android.widget.TextView[@text='Terms of service']"));
		
		
		String actualMsg = termsOf.getText();
		TestCommons.log(actualMsg);
		
		return actualMsg.equalsIgnoreCase("Terms of service") && btnRegister.isDisplayed() &&
				pswd.isDisplayed() && termsOf.isDisplayed();
		
	}
	
	public boolean verifyCountryFlagCode(AndroidDriver<MobileElement> androidDriver) {
		MobileElement code = androidDriver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='phoneContainer-testId']/android.view.ViewGroup/android.widget.TextView"));
		MobileElement countryFlag = androidDriver.findElementByAccessibilityId("phoneContainer-testId");
		
		return code.isDisplayed() && countryFlag.isDisplayed();
	}
	
	public boolean verifyCountryOptions(AndroidDriver<MobileElement> androidDriver) {
		List<MobileElement> countries = androidDriver.findElements(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.ScrollView[1]/android.view.ViewGroup/android.view.ViewGroup"));
		TestCommons.log("Total Countries displayed on first screen:: "+countries.size());
		
		MobileElement cross = androidDriver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.ImageView"));
		TestCommons.log("Cross (x) icon displayed successfully");
		return countries.size() > 5 && cross.isDisplayed();	
	}
	

}
