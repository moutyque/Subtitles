package com.ocsubtitles.beans;

import java.time.LocalTime;

public class SubtitleTripletBean {
	@Override
	public String toString() {
		return "SubtitleTripletBean [number=" + number + ", start=" + start + ", end=" + end + ", text=" + text
				+ ", movieName=" + movieName + "]";
	}
	public static final String splitTimeString = " --> ";
	private long number = 0;
	private LocalTime  start;
	private LocalTime end;
	private String text ="";
	private String movieName="";

	
	public long getNumber() {
		return number;
	}
	public LocalTime getStart() {
		return start;
	}
	public void setStart(LocalTime start) {
		this.start = start;
	}
	public LocalTime getEnd() {
		return end;
	}
	public void setEnd(LocalTime end) {
		this.end = end;
	}
	public String getText() {
		return text;
	}
	public void setText(String txt) {
		this.text = txt;
	}

	
public SubtitleTripletBean (long l) {
	this.number = l;
}

public SubtitleTripletBean() {

}

public String getMovieName() {
	return movieName;
}
public void setMovieName(String movieName) {
	this.movieName = movieName;
}





}
