package com.checker.miner;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checker.ApplicationProperties;
import com.checker.settings.DateRange;
import com.checker.settings.SettingIntepreterService;

/**
 * Get a Selenium page based on a mine request
 * 
 * @author craftmaster2190
 *
 */
@Service
public class SeleniumReservationService {

	@Autowired
	SettingIntepreterService settingIntepreterService;
	
	@Autowired
	ApplicationProperties applicationProperties;

	private FirefoxBinary firefoxBinary;

	public SeleniumReservationService() {
		this.firefoxBinary = new FirefoxBinary();
	}

	public void get() {	
		if(true){return;}
		
		WebDriver driver = new FirefoxDriver(this.firefoxBinary, null);
		try {
			DateRange dateRange = settingIntepreterService.getSettingAsDateRange("dates", new DateRange());
			System.err.println("Testing " + dateRange);
			Iterable<Date> iterable = dateRange.getIterable();
			int skipCount = 0;
			for (Date day : iterable) {
				if(skipCount > 0){
					skipCount--;
					continue;
				}
				String currentDay = DateRange.dateToString(day);
				
				driver.get(
						"http://www.recreation.gov/permits//r/entranceDetails.do?arvdate=" + currentDay + "&contractCode=NRSO&parkId=115139&entranceId=356817");

				WebElement findButton = driver.findElement(By.name("permitAvailabilitySearchButton"));
				findButton.click();

				List<WebElement> reservedStatuses = driver.findElements(By.className("permitStatus"));

				System.out.println("reservedStatuses size for " + currentDay + ": " + reservedStatuses.size());
				skipCount = reservedStatuses.size();
				if (reservedStatuses.size() == 0) {
					System.out.println("permit found at: " + currentDay);
				}
			}
		} catch (Exception e) {

		} finally {
			driver.quit();
		}
	}

}
