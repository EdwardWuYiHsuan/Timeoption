package com.edward.Timeoption.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.edward.Timeoption.models.DateTime;
import com.edward.Timeoption.models.Week;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class TimeoptionService {

	
	public String convert(String shiftHours)
	{
		JsonArray jArray = new JsonParser().parse(shiftHours).getAsJsonArray();
		Map<Week, Set<DateTime>> shiftHourList = new HashMap<>();
		for (int i = 0; i < jArray.size(); i++) {
			JsonObject shiftHour = jArray.get(i).getAsJsonObject();
			Week week = Week.getWeekByLetter(shiftHour.get("week").getAsString());
			if (null == week)
				throw new IllegalArgumentException("invalid-shift-hours");
			
			String start = shiftHour.get("start").getAsString();
			String end = shiftHour.get("end").getAsString();
			DateTime dateTime = new DateTime(start, end);
			
			if (shiftHourList.containsKey(week)) {
				shiftHourList.get(week).add(dateTime);
			} else {
				shiftHourList.put(week, new TreeSet<>(Arrays.asList(dateTime)));
			}
		}
		
		Map<Week, List<DateTime>> OpeningHour = algorithm(shiftHourList);
		StringBuilder result = new StringBuilder("   == Opening Hours ==\n");
		for (Map.Entry<Week, List<DateTime>> entry : OpeningHour.entrySet()) {
			result.append(entry.getKey().name()).append("  \t: ");
			
			List<DateTime> dateTimeList = entry.getValue();
			if (null != dateTimeList && !dateTimeList.isEmpty()) {
				for (DateTime datetime : dateTimeList) {
					result.append(datetime).append(" , ");
				}
				result.deleteCharAt(result.length() - 2);
			} else {
				result.append("Closed");
			}
			
			result.append("\n");
		}
		
		return result.toString();
	}
	
	public Map<Week, List<DateTime>> algorithm(Map<Week, Set<DateTime>> shiftHourList)
	{
		Map<Week, List<DateTime>> openingHours = new LinkedHashMap<>();
		for (Week week : Week.values()) {
			if (shiftHourList.containsKey(week)) 
			{
				List<DateTime> hours = new ArrayList<>();
				Set<DateTime> dtSet = shiftHourList.get(week);  //algorithm logic.
				DateTime prev = null;
				for (DateTime dt : dtSet) 
				{
					if (null == prev) {
						prev = dt;
					} else {
						if (prev.getEnd() > dt.getStart() || prev.getEnd() == dt.getStart()) {
							if (prev.getEnd() < dt.getEnd()) {
								prev = new DateTime(prev.getStart(), dt.getEnd());
							}
						} else {
							hours.add(prev);
							prev = dt;
						}
					}
				}
				hours.add(prev);
				
				openingHours.put(week, hours);
			} else {
				openingHours.put(week, new ArrayList<DateTime>());
			}
		}
		
		return openingHours;
	}
	
	
}

