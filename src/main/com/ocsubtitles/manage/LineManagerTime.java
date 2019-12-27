package com.ocsubtitles.manage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.beans.exceptions.FileFormatException;

public class LineManagerTime implements SubtitleLineManager {
	SubtitleTripletBean triplet;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");

	public LineManagerTime(SubtitleTripletBean triplet) {
		this.triplet = triplet;
	}

	@Override
	public SubtitleLineManager add(String line)  {
		LocalTime[] parsedTime;
		try {
			parsedTime = parseTime(line);
			this.triplet.setStart(parsedTime[0]);
			this.triplet.setEnd(parsedTime[1]);
		} catch (FileFormatException e) {
			System.err.println(e.toString());
			this.triplet.setStart(LocalTime.parse("00:00:00,0000",formatter));
			this.triplet.setEnd(LocalTime.parse("00:00:00,0000",formatter));
		}
		return new LineManagerText(triplet);
	}
	private LocalTime[] parseTime(String textTime) throws FileFormatException {
		LocalTime[] parsedTime = new LocalTime[2];
		if(textTime.contains(SubtitleTripletBean.splitTimeString)) {
			String[] splitTime = textTime.split(SubtitleTripletBean.splitTimeString);
			parsedTime[0] = LocalTime.parse(splitTime[0], formatter);
			parsedTime[1] = LocalTime.parse(splitTime[1], formatter);
		}
		else {
			throw new FileFormatException("The format of time is incorrect : " + textTime);
		}
		return parsedTime;
	}
	@Override
	public SubtitleTripletBean getTriplet() {
		return triplet;
	}

	@Override
	public void addTriplet(List<SubtitleTripletBean> outputTriplets) {
		// TODO Auto-generated method stub
		
	}
}
