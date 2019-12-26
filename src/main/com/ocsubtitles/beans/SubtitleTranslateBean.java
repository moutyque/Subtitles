package com.ocsubtitles.beans;

import org.apache.catalina.util.ToStringUtil;

public class SubtitleTranslateBean {
	
	@Override
	public String toString() {
		return "SubtitleTranslateBean [triplet=" + triplet.toString() + ", translation=" + translation + "]";
	}

	private SubtitleTripletBean triplet;
	private String translation;
	
	public long getNumber() {
		return triplet.getNumber();
	}
	public String getStart() {
		return triplet.getStart().toString();
	}
	public String getEnd() {
		return triplet.getEnd().toString();
	}
	public String getText() {
		return triplet.getText();
	}
	
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String otherLangageText) {
		this.translation = otherLangageText;
	}
	
	public SubtitleTranslateBean(SubtitleTripletBean triplet,String text) {
		this.triplet = triplet;
		this.translation = text;
	}

	
}
