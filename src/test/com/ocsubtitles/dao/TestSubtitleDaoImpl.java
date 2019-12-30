package com.ocsubtitles.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ocsubtitles.beans.SubtitleFileBean;
import com.ocsubtitles.beans.SubtitleTripletBean;


public class TestSubtitleDaoImpl {

	@Test
	public void testCreate() {
		SubtitleTripletBean triplet = new SubtitleTripletBean((long) Math.random());
		triplet.setOriginalFileName("Test.srt");
		triplet.setText("Test text");
		triplet.setStart(LocalTime.now());
		triplet.setEnd(LocalTime.now());

		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		subDAO.create(triplet);
	}
	
	@Test
	public void testFindMovie() {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		
		List<SubtitleTripletBean> subs = subDAO.findMovie("the-lord-of-the-rings-the-return-of-the-king-yify-english.srt");
		assertFalse(subs.isEmpty());
	}
	
	@Test
	public void testFindEntry() {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		double num = Math.random();
		SubtitleTripletBean tripletSet = new SubtitleTripletBean(1);
		tripletSet.setStart(LocalTime.parse("00:00:40.190"));
		tripletSet.setEnd(LocalTime.parse("00:01:00.000"));
		tripletSet.setText("Orginally by Bokutox. Fixings by Muhib@Subscene");
		tripletSet.setOriginalFileName(num+".srt");
		subDAO.create(tripletSet);
		SubtitleTripletBean tripletGet = subDAO.findEntry(1,num+".srt");
		
		assertEquals(tripletGet, tripletSet);
	}

	@Test
	public void testSave() {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		double num = Math.random();
		SubtitleFileBean subFile = new SubtitleFileBean();
		
		SubtitleTripletBean tripletSet = new SubtitleTripletBean(1);
		tripletSet.setStart(LocalTime.parse("00:00:40.190"));
		tripletSet.setEnd(LocalTime.parse("00:01:00.000"));
		tripletSet.setText("Orginally by Bokutox. Fixings by Muhib@Subscene");
		tripletSet.setOriginalFileName(num+".srt");
		List<SubtitleTripletBean> tripletList = new ArrayList<SubtitleTripletBean>();
		tripletList.add(tripletSet);
		subFile.setSubtitles(tripletList);
		subDAO.save(subFile);
		SubtitleTripletBean tripletGet = subDAO.findEntry(1,num+".srt");
		
		assertEquals(tripletGet, tripletSet);
	}
	
	
}
