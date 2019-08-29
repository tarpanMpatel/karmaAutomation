package com.karma.init;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.testng.annotations.DataProvider;

/**
 * Define Seller Test Data
 *
 */



public class TestData1
{
	@DataProvider(name="logdata")
	public static Object[][] Logindata()
	{
		Object obj[][]={{"login1","pass"}};

		return obj;
	}
	
	
	public static String getCurrentDateTime() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Calcutta");
		String t = timeZone.getID().toString();
		df.setTimeZone(TimeZone.getTimeZone(t));
		System.out.println(df.format(calobj.getTime()));
		String time = df.format(calobj.getTime());
		return time;
	}
}