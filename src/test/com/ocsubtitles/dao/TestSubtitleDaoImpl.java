package com.ocsubtitles.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.ocsubtitles.beans.SubtitleFileBean;
import com.ocsubtitles.beans.SubtitleTranslateBean;
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
		
		List<SubtitleTranslateBean> subs = subDAO.findMovie("the-lord-of-the-rings-the-return-of-the-king-yify-english.srt");
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
		Random rand = new Random();
		int num =Math.abs(rand.nextInt());
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
	
	@Test
	public void testCreateTable() {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		Random rand = new Random();
		int num =Math.abs(rand.nextInt());
		final String SQL_CREATE_TABLE ="CREATE TABLE TABLETEST"+num+" ("
				+"ID INT NOT NULL AUTO_INCREMENT,"
				+ "TESTTEXT VARCHAR(200),"
				+ "PRIMARY KEY (ID))";
		
		assertTrue(subDAO.createTable(SQL_CREATE_TABLE));
		
	}
}
