package com.checker.miner;


import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LodorePageCheck {
 
	public static void main2(String [] args) {
		
		int index = 0;

		while(true) {
			index++;
			System.out.println("attempting check: " + index);
//			FirefoxProfile prof = new FirefoxProfile();
//			prof.setPreference("xpinstall.signatures.required", false);
//			prof.setPreference("toolkit.telemetry.reportingpolicy.firstRun", false);
//			prof.setPreference("browser.startup.homepage_override.mstone", "ignore");
//			prof.setPreference("startup.homepage_welcome_url.additional",  "about:blank");
			WebDriver driver = new FirefoxDriver();
//			WebDriver driver = new PhantomJSDriver();
//			JBrowserDriver driver = new JBrowserDriver();
			
	        driver.get("http://www.recreation.gov/permits//r/entranceDetails.do?arvdate=06/01/2016&contractCode=NRSO&parkId=115139&entranceId=356817");
	
	        WebElement findButton = driver.findElement(By.name("permitAvailabilitySearchButton"));
	        findButton.click();
	        
	        List<WebElement> reservedStatuses = driver.findElements(By.className("permitStatus"));

        	System.out.println("reservedStatuses size for June 1: " + reservedStatuses.size());
	        if(reservedStatuses.size() > 0) {
	        	
	        	for(WebElement elm: reservedStatuses) {
	        		if(!elm.getText().equalsIgnoreCase("r")) {
	                	System.out.println("permit found at: " + new Date());
	    	            break;
	        		}
	        	}
	        }
	        
	        driver.get("http://www.recreation.gov/permits//r/entranceDetails.do?arvdate=06/15/2016&contractCode=NRSO&parkId=115139&entranceId=356817");

	        reservedStatuses = driver.findElements(By.className("permitStatus"));

        	System.out.println("reservedStatuses size for June 15: " + reservedStatuses.size());
	        if(reservedStatuses.size() > 0) {
	        	
	        	for(WebElement elm: reservedStatuses) {
	        		if(!elm.getText().equalsIgnoreCase("r")) {
	                	System.out.println("permit found at: " + new Date());
	    	            break;
	        		}
	        	}
	        }

	        //Close the browser
	        driver.quit();

	        try {
				Thread.sleep(30000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
    }
}