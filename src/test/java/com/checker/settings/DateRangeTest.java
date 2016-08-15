package com.checker.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateRangeTest {

	@Test
	public void testDateRange() {
		DateRange dateRange = new DateRange();
		Date startDate = dateRange.getStartDate();
		Date endDate = dateRange.getEndDate();
		assertEquals(startDate, endDate);
		String startString = DateRange.dateToString(startDate);
		String todayString = DateRange.dateToString(new Date());
		assertEquals(startString, todayString);
	}

	@Test
	public void testDateRangeString() {
		String startString = "01/01/2016";
		String endString = "01/02/2016";
		DateRange dateRange = new DateRange(startString + "-" + endString);
		Date startDate = dateRange.getStartDate();
		assertEquals(startString, DateRange.dateToString(startDate));
		Date endDate = dateRange.getEndDate();
		assertEquals(endString, DateRange.dateToString(endDate));
	}

	@Test
	public void testDateRangeDateDate() {
		String startString = "01/01/2016";
		String endString = "01/02/2016";
		DateRange dateRange = new DateRange(DateRange.stringToDate(startString), DateRange.stringToDate(endString));
		Date startDate = dateRange.getStartDate();
		assertEquals(startString, DateRange.dateToString(startDate));
		Date endDate = dateRange.getEndDate();
		assertEquals(endString, DateRange.dateToString(endDate));
	}

	@Test
	public void testDateRangeStringString() {
		String startString = "01/01/2016";
		String endString = "01/02/2016";
		DateRange dateRange = new DateRange(startString, endString);
		Date startDate = dateRange.getStartDate();
		assertEquals(startString, DateRange.dateToString(startDate));
		Date endDate = dateRange.getEndDate();
		assertEquals(endString, DateRange.dateToString(endDate));
	}

	//@Test
	public void testGetIterable() {
		checkIterable(new DateRange());		
		checkIterable(new DateRange("11/01/2016-11/18/2016"));
		checkIterable(new DateRange("11/01/2016-11/1/2016"));
		checkIterable(new DateRange("11/01/2016-11/2/2016"));
		checkIterable(new DateRange("11/2/2016-11/1/2016"));
		checkIterable(new DateRange("11/01/2016-11/1/2017"));
	}

	private static void checkIterable(DateRange dateRange) {
		Date startDate = dateRange.getStartDate();
		Date endDate = dateRange.getEndDate();
		Iterable<Date> iterable = dateRange.getIterable();
		assertTrue("There must always be at least one iteration.", iterable.iterator().hasNext());
		boolean atLeastOneDayMatchesStartDate = false;
		boolean atLeastOneDayMatchesEndDate = false;
		for (Date date : iterable) {
			if(DateRange.dateToString(startDate).equals(DateRange.dateToString(date))){
				atLeastOneDayMatchesStartDate = true;
			}
			if(DateRange.dateToString(endDate).equals(DateRange.dateToString(date))){
				atLeastOneDayMatchesEndDate = true;
			}
			assertTrue("startDate(" + startDate + ") cannot be later than date(" + date + ")", startDate.compareTo(date) <= 0);
			assertTrue("endDate(" + endDate + ") cannot be sooner than date(" + date + ")", endDate.compareTo(date) >= 0);
		}
		assertTrue("startDate(" + startDate + ") did not match any days.", atLeastOneDayMatchesStartDate);
		assertTrue("endDate(" + endDate + ") did not match any days.", atLeastOneDayMatchesEndDate);
	}
}
