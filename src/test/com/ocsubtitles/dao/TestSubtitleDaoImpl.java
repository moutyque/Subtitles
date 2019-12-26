package com.ocsubtitles.dao;

import java.time.LocalTime;


import org.junit.Test;

import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.DAOFactory;
import com.ocsubtitles.dao.SubtitleDaoImpl;

public class TestSubtitleDaoImpl {

	@Test
	public void testCreate() {
		SubtitleTripletBean triplet = new SubtitleTripletBean((long) Math.random());
		triplet.setMovieName("Test.srt");
		triplet.setText("Test text");
		triplet.setStart(LocalTime.now());
		triplet.setEnd(LocalTime.now());

		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		subDAO.create(triplet);
	}

}
