package com.edward.Timeoption.models;

public enum Week {
	Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
	
	public static Week getWeekByLetter(String text)
	{
		for (Week week : Week.values()) {
			if (text.equalsIgnoreCase(week.name())) {
				return week;
			}
		}
		return null;
	}
	
	
}
