package com.ocsubtitles.beans;

public class SubtitleModifyBean {
	SubtitleTripletBean triplet;
	String otherLangageText;
	public SubtitleModifyBean(SubtitleTripletBean triplet,String text) {
		this.triplet = triplet;
		this.otherLangageText = text;
	}
}
