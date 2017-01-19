package com.edward.Timeoption.controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edward.Timeoption.service.TimeoptionService;


@RestController
public class TimeoptionController {

	@Autowired
	private TimeoptionService timeoptionService;
	
	
	@RequestMapping(value = "/convertShiftIntoOpening", method = RequestMethod.POST)
	public Map<String, Object> convertShiftIntoOpening(@RequestParam(value = "shiftHours") String shiftHours)
	{
		//shiftHours = "[{week:'Monday', start:'9am', end:'4pm'},{week:'Sunday', start:'10am', end:'7pm'}]"
		
		Map<String, Object> response = new HashMap<>();
		try {
			if (StringUtils.isBlank(shiftHours))
				 throw new Exception("invalid-shift-hours");
			
			String result = timeoptionService.convert(shiftHours);
			
			response.put("result", "ok");
			response.put("data", result);
			
		} catch (Exception e) {
			response.put("result", "error");
			response.put("reason", e.getMessage());
		}
		
		return response;
	}
	
	
}

