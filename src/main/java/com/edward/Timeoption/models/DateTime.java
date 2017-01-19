package com.edward.Timeoption.models;

public class DateTime implements Comparable<DateTime> {
	
	public static final String AM = "AM";
	public static final String PM = "PM";

	private int start;
	private int end;
	
	
	public DateTime(String start, String end)
	{
		start = start.toUpperCase();
		end = end.toUpperCase();
		
		if (!start.contains(AM) && !start.contains(PM))
			throw new IllegalArgumentException("invalid-start-date");
		if (!end.contains(AM) && !end.contains(PM))
			throw new IllegalArgumentException("invalid-end-date");
		
		if (start.contains(PM)) {
			if (start.equals("12PM")) { //noon 12
				this.setStart(12);
			} else {
				start = start.replaceAll(PM, "");
				this.setStart(12 + Integer.parseInt(start));
			}
		} else {
			if (start.equals("12AM")) {  //midnight 00
				this.setStart(0);
			} else {
				start = start.replaceAll(AM, "");
				this.setStart(Integer.parseInt(start));
			}
		}
		
		if (end.contains(PM)) {
			if (end.equals("12PM")) {
				this.setEnd(12);
			} else {
				end = end.replace(PM, "");
				this.setEnd(12 + Integer.parseInt(end));
			}
		} else {
			if (end.equals("12AM")) {
				this.setEnd(0);
			} else {
				end = end.replace(AM, "");
				this.setEnd(Integer.parseInt(end));
			}
		}
		
		if (this.start > this.end)
			throw new IllegalArgumentException("invalid-start-end-date");
	}
	
	public DateTime(int start, int end)
	{
		if (start > end)
			throw new IllegalArgumentException("invalid-start-end-date");
			
		this.setStart(start);
		this.setEnd(end);
	}

	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getEnd() {
		return end;
	}


	public void setEnd(int end) {
		this.end = end;
	}
	
	@Override
	public int hashCode() 
	{
		return this.start;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (null == obj)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final DateTime dateTime = (DateTime) obj;
		return this.start == dateTime.start && this.end == dateTime.end;
	}
	
	@Override
	public int compareTo(DateTime obj) 
	{
		if (this.start > obj.start) {
			return 1;
		} else {
			return -1;
		}
	}
	
	@Override
	public String toString() 
	{
		return String.format("%s ~ %s", convertDateStr(start), convertDateStr(end));
	}
	
	private String convertDateStr(int date) 
	{
		switch (date) {
		case 0: return "12AM";
		case 12: return "12PM";
		default :
			if (date < 12) {
				return date + AM;
			} else { 
				return (date - 12) + PM;
			}
		}
	}
	
	
}
