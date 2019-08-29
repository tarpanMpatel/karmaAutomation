package com.karma.register.index;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.karma.init.AppiumInit;

public class RegisterIndex extends AppiumInit {

	@Test(priority=1)
	public void verifyRegister() throws UnsupportedEncodingException, URISyntaxException, IOException, InterruptedException {
		
		int numberOfFailure=0;
		int step = 1;
		int exp = 1;
		testcaseId("Totla Test Cases Grouped: TC_Reg_01, TC_Reg_02, TC_Reg_04, TC_Reg_05");
		
		
		testcaseId("TC_Reg_01");
		testDescription("To verify launch of Karma Android app..");
		
		log("Step " + step++ + ": Open the 'Karma' Android Application from mobile.");
		//Thread.sleep(2000);
		
		logverification(exp,"To verify 'Karma' application open successfullly.");
		if(registerVerificationPage.verifyAppLaunch(androidDriver)) {
			log("Application Open successfully...");
			logStatus(1);	
		}else {
			logStatus(2);
			numberOfFailure++;
		}
		
		
		testcaseId("TC_Reg_02");
		testDescription("To verify 'REGISTER' button functionality..");
		
		log("Step " + step++ + ": Proceed for Registeration.");
		registerIndexPage.clickRegister(androidDriver);
		
		logverification(exp++,"To verify 'Register' page open successfullly.");
		if(registerVerificationPage.verifyRegisterPage(androidDriver)) {
			log("Register Page Open successfully...");
			logStatus(1);	
		}else {
			logStatus(2);
			numberOfFailure++;
		}
		
		
		testcaseId("TC_Reg_04");
		testDescription("To verify default country flag/country code from phone number text field...");
		
		logverification(exp++,"To verify 'Country Flag' and 'Country Code' for UK (+44) displayed succesfully.");
		if(registerVerificationPage.verifyCountryFlagCode(androidDriver)) {
			log("Country Code and Country Flag for UK country displayed successfully...");
			logStatus(1);	
		}else {
			logStatus(2);
			numberOfFailure++;
		}
		
		
		testcaseId("TC_Reg_05");
		testDescription("To verify country flag/country code from phone number text field...");
		
		log("Step " + step++ + ": Country Flag drop down functionality check..");
		registerIndexPage.tapCountryFlag(androidDriver);
		
		logverification(exp++,"To verify Countries list should get open successfully.");
		if(registerVerificationPage.verifyCountryOptions(androidDriver)) {
			logStatus(1);	
		}else {
			logStatus(2);
			numberOfFailure++;
		}
	
		if(numberOfFailure > 0){
				Assert.assertTrue(false);
			}
		
		}
	
	
}
