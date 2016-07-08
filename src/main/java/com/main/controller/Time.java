package com.main.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time")
public class Time {

	@RequestMapping(value = "/UTC", method = RequestMethod.GET)
	public String getUTCTime(){
/*		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(timeZone);
		SimpleDateFormat simpleDateFormat = 
		       new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		simpleDateFormat.setTimeZone(timeZone);
		
		System.out.println("Time zone: " + timeZone.getID());
		System.out.println("default time zone: " + TimeZone.getDefault().getID());
		System.out.println();

		System.out.println("UTC:     " + simpleDateFormat.format(calendar.getTime()));
		System.out.println("Default: " + calendar.getTime());
		
		return simpleDateFormat.format(calendar.getTime());*/
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return sdf.format(today);
	} 
	
	@RequestMapping(value="/PST", method = RequestMethod.GET)
	public String getPSTTime(){
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("PST"));
		
		return sdf.format(today);
	}
}
