package com.checker.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateRangeTest {

	private static void checkIterable(DateRange dateRange) {
		LocalDate startDate = dateRange.getStartDate();
		LocalDate endDate = dateRange.getEndDate();
		Iterable<LocalDate> iterable = dateRange.getIterable();
		assertTrue("There must always be at least one iteration.", iterable.iterator().hasNext());
		boolean atLeastOneDayMatchesStartDate = false;
		boolean atLeastOneDayMatchesEndDate = false;
		for (LocalDate date : iterable) {
			if (DateRange.dateToString(startDate).equals(DateRange.dateToString(date))) {
				atLeastOneDayMatchesStartDate = true;
			}
			if (DateRange.dateToString(endDate).equals(DateRange.dateToString(date))) {
				atLeastOneDayMatchesEndDate = true;
			}
			assertTrue("startDate(" + startDate + ") cannot be later than date(" + date + ")",
					startDate.compareTo(date) <= 0);
			assertTrue("endDate(" + endDate + ") cannot be sooner than date(" + date + ")",
					endDate.compareTo(date) >= 0);
		}
		assertTrue("startDate(" + startDate + ") did not match any days.", atLeastOneDayMatchesStartDate);
		assertTrue("endDate(" + endDate + ") did not match any days.", atLeastOneDayMatchesEndDate);
	}

	@Test
	public void testDateRange() {
		DateRange dateRange = new DateRange();
		LocalDate startDate = dateRange.getStartDate();
		LocalDate endDate = dateRange.getEndDate();
		assertEquals(startDate, endDate);
		String startString = DateRange.dateToString(startDate);
		String todayString = DateRange.dateToString(LocalDate.now());
		assertEquals(startString, todayString);
	}

	@Test
	public void testDateRangeDateDate() {
		String startString = "01/01/2016";
		String endString = "01/02/2016";
		DateRange dateRange = new DateRange(DateRange.stringToDate(startString), DateRange.stringToDate(endString));
		LocalDate startDate = dateRange.getStartDate();
		assertEquals(DateRange.stringToDate(startString), startDate);
		LocalDate endDate = dateRange.getEndDate();
		assertEquals(DateRange.stringToDate(endString), endDate);
	}

	@Test
	public void testDateRangeString() {
		String startString = "01/01/2016";
		String endString = "01/02/2016";
		DateRange dateRange = new DateRange(startString + "-" + endString);
		LocalDate startDate = dateRange.getStartDate();
		assertEquals(DateRange.stringToDate(startString), startDate);
		LocalDate endDate = dateRange.getEndDate();
		assertEquals(DateRange.stringToDate(endString), endDate);
	}

	@Test
	public void testDateRangeStringString() {
		String startString = "01/01/2016";
		String endString = "01/02/2016";
		DateRange dateRange = new DateRange(startString, endString);
		LocalDate startDate = dateRange.getStartDate();
		assertEquals(DateRange.stringToDate(startString), startDate);
		LocalDate endDate = dateRange.getEndDate();
		assertEquals(DateRange.stringToDate(endString), endDate);
	}

	@Test
	public void testGetIterable() {
		checkIterable(new DateRange());
		checkIterable(new DateRange("11/01/2016-11/18/2016"));
		checkIterable(new DateRange("11/01/2016-11/1/2016"));
		checkIterable(new DateRange("11/01/2016-11/2/2016"));
		checkIterable(new DateRange("11/2/2016-11/1/2016"));
		checkIterable(new DateRange("11/01/2016-11/1/2017"));
	}
}
