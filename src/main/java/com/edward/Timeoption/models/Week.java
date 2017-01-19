package com.edward.Timeoption.models;

public enum Week {
	Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
	
	public static Week getWeekByLetter(String text)
	{
		Week result = null;
		for (Week week : Week.values()) {
			if (text.equalsIgnoreCase(week.name())) {
				result = week;
				break;
			}
		}
		return result;
	}
	
	
}
