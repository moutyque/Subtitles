package com.ocsubtitles.beans;

import java.util.ArrayList;

import java.util.List;

public class SubtitleFileBean {
	public static final String SUBTITLE_EXTENSION = ".srt";
	private String name;
	private String path;
	private List<SubtitleTranslateBean> subtitles = new ArrayList<>();

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

	public List<SubtitleTranslateBean> getSubtitles() {
		return this.subtitles;
	}
	public void setSubtitles(List<SubtitleTranslateBean> subtitles) {
		this.subtitles = subtitles;
	}



}
