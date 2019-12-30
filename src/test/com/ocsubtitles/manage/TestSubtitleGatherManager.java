package com.ocsubtitles.manage;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.Test;

import com.ocsubtitles.beans.SubtitleTranslateBean;
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.DAOFactory;
import com.ocsubtitles.manage.SubtitleGatherManager;

public class TestSubtitleGatherManager {

	@Test
	public void testGetSaveSubtitle() {
		int index = 7;
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		SubtitleGatherManager sgm = new SubtitleGatherManager(daoFactory.getSubtitleDao());
		String movieName ="The Irishman_en.srt";
		List<SubtitleTranslateBean> listTranslate = sgm.getSubtitles(movieName);
		SubtitleTripletBean triplet = new SubtitleTripletBean((long) (index+1) );
		triplet.setOriginalFileName("The Irishman_en.srt");
		LocalTime date1 = LocalTime.parse("00:02:01.000");
		LocalTime date2 = LocalTime.parse("00:02:02.000");
		triplet.setStart(date1);
		triplet.setEnd(date2);
		triplet.setText("And then...");
		SubtitleTranslateBean translate = new SubtitleTranslateBean(triplet, "");
		System.out.println(listTranslate.get(index).toString());
		System.out.println(translate.toString());
		
		assertEquals(listTranslate.get(index), translate);
	}

}
