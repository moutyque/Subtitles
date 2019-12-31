package com.ocsubtitles.manage;

import java.util.ArrayList;
import java.util.List;

import com.ocsubtitles.beans.SubtitleTranslateBean;
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.DAOFactory;
import com.ocsubtitles.dao.SubtitleDao;


public class SubtitleGatherManager {
	private List<SubtitleTripletBean> subs;
	public SubtitleGatherManager(String fileName) {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDao subDAO = factory.getSubtitleDao();
		
		subs = subDAO.findMovie(fileName);
		
		
		
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
