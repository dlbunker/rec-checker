package com.checker.settings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DateRange {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("M/d/yyyy");

	public static String dateToString(LocalDate date) {
		if (date == null) {
			return null;
		}
		return date.format(DATE_FORMAT);
	}

	public static LocalDate stringToDate(String date) {
		if (date == null) {
			return null;
		}
		try {
			return LocalDate.parse(date, DATE_FORMAT);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	private LocalDate endDate;

	private LocalDate startDate;

	public DateRange() {
		this.endDate = LocalDate.now();
		this.startDate = LocalDate.now();
	}

	public DateRange(LocalDate start, LocalDate end) {
		this.setStartDate(start);
		this.setEndDate(end);
	}

	public DateRange(String range) {
		String[] dates = range.split("-");
		if (dates.length == 2) {
			this.setStartDate(stringToDate(dates[0]));
			this.setEndDate(stringToDate(dates[1]));
		}
	}

	public DateRange(String start, String end) {
		this.setStartDate(stringToDate(start));
		this.setEndDate(stringToDate(end));
	}

	private void checkDateOrdering() {
		if (this.startDate == null || this.endDate == null) {
			return;
		}
		if (this.startDate.compareTo(this.endDate) <= 0) {
			return;
		}
		LocalDate temp = this.startDate;
		this.startDate = this.endDate;
		this.endDate = temp;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	/**
	 * Get an iterable of all the dates inclusive between startDate and endDate.
	 * If either of those are null, return an empty iterable.
	 *
	 * @return an iterable of Date objects.
	 */
	public Iterable<LocalDate> getIterable() {
		ArrayList<LocalDate> list = new ArrayList<>();
		if (this.startDate == null || this.endDate == null) {
			return list;
		}

		LocalDate currentDate = this.startDate;
		while (currentDate.isBefore(this.endDate) || currentDate.equals(this.endDate)) {
			list.add(currentDate);
			currentDate = currentDate.plusDays(1);
		}

		return list;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public void setEndDate(LocalDate end) {
		this.endDate = end;
		this.checkDateOrdering();
	}

	public void setStartDate(LocalDate start) {
		this.startDate = start;
		this.checkDateOrdering();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(dateToString(this.startDate));
		stringBuilder.append("-");
		stringBuilder.append(dateToString(this.endDate));
		return stringBuilder.toString();
	}
}
