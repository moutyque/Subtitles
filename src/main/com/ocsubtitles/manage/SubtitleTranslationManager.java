package com.ocsubtitles.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ocsubtitles.beans.SubtitleFileBean;
import com.ocsubtitles.beans.SubtitleTranslateBean;
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.DAOFactory;
import com.ocsubtitles.dao.SubtitleDao;


public class SubtitleTranslationManager {
	private SubtitleFileBean subFile = new SubtitleFileBean();
	private String fileName ="";
	public SubtitleTranslationManager(String fileName) {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDao subDAO = factory.getSubtitleDao();
		subFile = subDAO.findMovie(fileName);
		this.fileName = fileName;



	}

	public List<SubtitleTranslateBean> getSubtitles() {
		return subFile.getSubtitles();

	}

	public void parseTranslation(Map<String, String[]> translations) {
		final String KEY_WORD ="translation";

		for(String key : translations.keySet()) {
			if(key.contains(KEY_WORD)) {
				String strNumber = key.replace(KEY_WORD, "");
				int number = Integer.parseInt(strNumber);
				String[] strArr = translations.get(key);
				String value = strArr.length > 0 ? strArr[0] : "";
				subFile.getSubtitles().get(number-1).setTranslation(value);
			}
		}
	}
	
	public void save() {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDao subDAO = factory.getSubtitleDao();
		subDAO.update(subFile);
	}
	
	

}
