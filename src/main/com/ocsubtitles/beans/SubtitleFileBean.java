package com.ocsubtitles.beans;

import java.util.ArrayList;

import java.util.List;

public class SubtitleFileBean {
	public static final String SUBTITLE_EXTENSION = ".srt";
	private String name;
	private String path;
	private List<SubtitleTripletBean> subtitles = new ArrayList<SubtitleTripletBean>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<SubtitleTripletBean> getSubtitles() {
		return this.subtitles;
	}
	public void setSubtitles(List<SubtitleTripletBean> subtitles) {
		this.subtitles = subtitles;
	}



}
