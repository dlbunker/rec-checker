package com.checker.settings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DateRange {
	private static final long NUMBER_OF_MILLIS_PER_DAY = 86400000L;
	private Date endDate;
	private Date startDate;

	public DateRange() {
		this.endDate = new Date();
		this.startDate = new Date();
	}

	public DateRange(String range) {
		String[] dates = range.split("-");
		if (dates.length == 2) {
			setStartDate(stringToDate(dates[0]));
			setEndDate(stringToDate(dates[1]));
		}
	}

	public DateRange(Date start, Date end) {
		setStartDate(start);
		setEndDate(end);
	}

	public DateRange(String start, String end) {
		setStartDate(stringToDate(start));
		setEndDate(stringToDate(end));
	}

	private void checkDateOrdering() {
		if (this.startDate == null || this.endDate == null) {
			return;
		}
		if (this.startDate.compareTo(this.endDate) <= 0) {
			return;
		}
		Date temp = this.startDate;
		this.startDate = this.endDate;
		this.endDate = temp;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * Get an iterable of all the dates inclusive between startDate and endDate.
	 * If either of those are null, return an empty iterable.
	 * 
	 * @return an iterable of Date objects.
	 */
	public Iterable<Date> getIterable() {
		if (this.startDate == null || this.endDate == null) {
			return new ArrayList<>();
		}
		if(dateToString(this.startDate).equals(dateToString(this.endDate))){
			ArrayList<Date> arrayList = new ArrayList<>();
			arrayList.add(this.startDate);
			return arrayList;
		}
		long start = this.startDate.getTime();
		String end = dateToString(this.endDate);
		ArrayList<Date> list = new ArrayList<>();
		boolean readyToBreak = false;
		while (true) {
			list.add(new Date(start));
			start = start + NUMBER_OF_MILLIS_PER_DAY;
			if(readyToBreak){
				break;
			}
			if(dateToString(new Date(start)).equals(end)){
				readyToBreak = true;
			}
		}
		return list;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setEndDate(Date end) {
		this.endDate = end;
		checkDateOrdering();
	}

	public void setStartDate(Date start) {
		this.startDate = start;
		checkDateOrdering();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(dateToString(this.startDate));
		stringBuilder.append("-");
		stringBuilder.append(dateToString(this.endDate));
		return stringBuilder.toString();
	}

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	public static Date stringToDate(String date) {
		try {
			return DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMAT.format(date);
	}
}
