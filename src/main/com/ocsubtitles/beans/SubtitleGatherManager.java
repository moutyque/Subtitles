package com.ocsubtitles.beans;

import java.util.ArrayList;
import java.util.List;


import com.ocsubtitles.dao.SubtitleDao;


public class SubtitleGatherManager {
	private SubtitleDao subtitleDao;
	public SubtitleGatherManager(SubtitleDao subtitleDao) {
		this.subtitleDao =subtitleDao;
	}
	public List<SubtitleTranslateBean> getSubtitles(String movieName) {
		List<SubtitleTripletBean> tripletList = this.subtitleDao.findMovie(movieName);
		List<SubtitleTranslateBean> transSubList = new ArrayList<SubtitleTranslateBean>();
		for(SubtitleTripletBean triplet : tripletList) {
			transSubList.add(new SubtitleTranslateBean(triplet,""));
		}
		return transSubList;
	}

}
