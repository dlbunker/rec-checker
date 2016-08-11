package com.checker.miner;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Service;

/**
 * Get a Selenium page based on a mine request
 * 
 * @author craftmaster2190
 *
 */
@Service
public class SeleniumReservationService {

	private FirefoxBinary firefoxBinary;

	public SeleniumReservationService() {
		firefoxBinary = new FirefoxBinary();
	}

	public void get() {
		WebDriver driver = new FirefoxDriver(firefoxBinary, null);
		try {
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
		} catch (Exception e) {
			
		} finally {
			driver.quit();
		}
	}

}
