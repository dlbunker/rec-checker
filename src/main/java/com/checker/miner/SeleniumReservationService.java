package com.checker.miner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
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

	private static final String ENTRANCE_ID_PREFIX = "entranceId=";

	static Logger logger = Logger.getLogger(SeleniumReservationService.class);

	private static String buildEntranceUrl(Park park) {
		String url = "http://www.recreation.gov/permits//r/wildernessAreaDetails.do?page=details&contractCode=NRSO&parkId="
				+ park.getSysId();
		logger.log(Level.DEBUG, "Searching park(" + park + ") and url(" + url + ")");
		return url;
	}

	private static String buildReservationUrl(String currentDay, Park park, Entrance entrance) {
		String url = "http://www.recreation.gov/permits//r/entranceDetails.do?arvdate=" + currentDay
				+ "&contractCode=NRSO&parkId=" + park.getSysId() + "&entranceId=" + entrance.getSysId();
		logger.log(Level.DEBUG, "Searching currentDay(" + currentDay + ") and url(" + url + ")");
		return url;
	}

	private static List<WebElement> getReservedStatuses(WebDriver driver, String currentDay) {
		List<WebElement> reservedStatuses = driver.findElements(By.className("permitStatus"));

		logger.log(Level.DEBUG, "reservedStatuses size for " + currentDay + ": " + reservedStatuses.size());
		return reservedStatuses;
	}

	@Autowired
	ApplicationProperties applicationProperties;

	@Autowired
	RIDBFacilityRestSerivce facilityRestService;

	private FirefoxBinary firefoxBinary;

	@Autowired
	ServerSideRepositoryService serverSideRepositoryService;

	@Autowired
	SettingIntepreterService settingIntepreterService;

	private boolean useHTMLUnit;

	private Iterable<LocalDate> getDateRangeIterableFromSettings() {
		DateRange dateRange = this.settingIntepreterService.getSettingAsDateRange("dates",
				new DateRange(this.applicationProperties.getDefaultDates()));
		logger.log(Level.DEBUG, "Searching date range: " + dateRange);
		Iterable<LocalDate> iterable = dateRange.getIterable();
		return iterable;
	}

	private WebDriver getWebDriver() {
		WebDriver driver;
		if (this.useHTMLUnit) {
			driver = new HtmlUnitDriver();
		} else {
			driver = new FirefoxDriver(this.firefoxBinary, null);
		}
		return driver;
	}

	@PostConstruct
	public void init() {
		this.useHTMLUnit = this.applicationProperties.isUseHTMLUnit();
		if (this.useHTMLUnit) {
			return;
		}

		this.firefoxBinary = new FirefoxBinary();
		String xvfbDisplayPort = this.applicationProperties.getXvfbDisplayPort();
		if (this.applicationProperties.isUseXvfb() && xvfbDisplayPort != null) {
			this.firefoxBinary.setEnvironmentProperty("DISPLAY", xvfbDisplayPort);
		}
	}

	private void persistNewMineResult(LocalDate day, Park park, Entrance entrance) {
		MineResult mineResult = this.serverSideRepositoryService.findByDateAndEntranceAndPark(day, entrance, park);
		if (mineResult == null) {
			mineResult = new MineResult();
			mineResult.setDate(day);

			mineResult.setPark(park);

			mineResult.setEntrance(entrance);

			this.serverSideRepositoryService.saveNewMineResult(mineResult);
		}
	}

	public void run() {
		boolean on = this.settingIntepreterService.getSettingAsBoolean("on", false);
		if (!on) {
			return;
		}
		WebDriver driver = this.getWebDriver();
		try {
			Iterable<LocalDate> dateRangeIterable = this.getDateRangeIterableFromSettings();
			int skipCount = 0;
			for (LocalDate day : dateRangeIterable) {
				if (skipCount > 0) {
					skipCount--;
					continue;
				}
				String currentDay = DateRange.dateToString(day);

				ArrayList<Park> parksList = this.facilityRestService.getParks();
				for (Park park : parksList) {

					logger.log(Level.DEBUG, "Checking park(" + park.getSysId() + ", " + park.getName() + ")");
					String url = buildEntranceUrl(park);
					driver.get(url);
					WebElement permitListButton;
					try {
						permitListButton = driver.findElement(By.id("entranceSearch"));
					} catch (NoSuchElementException e) {
						logger.log(Level.DEBUG,
								"Park(" + park.getSysId() + ", " + park.getName() + ") does not have permits.");
						System.err.println("Continuing...");
						continue;
					}
					permitListButton.click();
					List<WebElement> entranceLinks = driver.findElements(By.className("now"));
					ArrayList<Entrance> entrancesList = new ArrayList<>();
					for (WebElement entranceLinkElement : entranceLinks) {
						String href = entranceLinkElement.getAttribute("href");

						int entranceIdStartIndex = href.indexOf(ENTRANCE_ID_PREFIX);
						entranceIdStartIndex += ENTRANCE_ID_PREFIX.length();
						int entranceIdEndIndex = href.indexOf("&", entranceIdStartIndex);
						String entranceIDstring = href.substring(entranceIdStartIndex, entranceIdEndIndex);
						long entranceID = Long.parseLong(entranceIDstring);

						System.err.println("entranceId = '" + entranceIDstring + "'");

						Entrance entrance = this.serverSideRepositoryService.findEntranceIfExistsOrCreateNew(entranceID,
								park);
						entrancesList.add(entrance);
					}
					for (final Entrance entrance : entrancesList) {
						url = SeleniumReservationService.buildReservationUrl(currentDay, park, entrance);
						driver.get(url);

						WebElement findButton;
						try {
							findButton = driver.findElement(By.name("permitAvailabilitySearchButton"));
						} catch (NoSuchElementException e) {
							continue;
						}
						findButton.click();

						List<WebElement> reservedStatuses = getReservedStatuses(driver, currentDay);
						if (reservedStatuses.size() == 14) {
							skipCount = reservedStatuses.size();
						}
						if (reservedStatuses.size() == 0) {
							logger.log(Level.DEBUG, "permit found at: " + currentDay);
							this.persistNewMineResult(day, park, entrance);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e);
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}

}
