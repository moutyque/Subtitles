package com.ocsubtitles.manage;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ocsubtitles.beans.SubtitleTranslateBean;
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.DAOFactory;
import com.ocsubtitles.manage.SubtitleGatherManager;

public class TestSubtitleGatherManager {
private static final String FILE_NAME ="the-lord-of-the-rings-the-return-of-the-king-yify-english.srt";
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testGetSaveSubtitle() {
		int index = 1;
		SubtitleGatherManager sgm = new SubtitleGatherManager(FILE_NAME);
		List<SubtitleTranslateBean> listTranslate = sgm.getSubtitles();
		SubtitleTripletBean triplet = new SubtitleTripletBean(index);
		triplet.setOriginalFileName(FILE_NAME);
		LocalTime date1 = LocalTime.parse("00:00:40.19");
		LocalTime date2 = LocalTime.parse("00:01:19.29");
		triplet.setStart(date1);
		triplet.setEnd(date2);
		triplet.setText("Orginally by Bokutox. Fixings by Muhib@Subscene");
		SubtitleTranslateBean translate = new SubtitleTranslateBean(triplet, "");		
		assertEquals(listTranslate.get(index), translate);
	}

}
