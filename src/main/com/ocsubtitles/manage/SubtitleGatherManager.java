package com.ocsubtitles.manage;

import java.util.ArrayList;
import java.util.List;

import com.ocsubtitles.beans.SubtitleTranslateBean;
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.DAOFactory;
import com.ocsubtitles.dao.SubtitleDao;


public class SubtitleGatherManager {
	private List<SubtitleTranslateBean> subs = new ArrayList<SubtitleTranslateBean>();
	public SubtitleGatherManager(String fileName) {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDao subDAO = factory.getSubtitleDao();
		subs = subDAO.findMovie(fileName);
		
		
		
	}

	public List<SubtitleTranslateBean> getSubtitles() {
		return subs;
		
	}

}
