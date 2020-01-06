package ocsubtitles.dao;

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

import ocsubtitles.beans.SubtitleFileBean;
import ocsubtitles.beans.SubtitleTranslateBean;
import ocsubtitles.beans.SubtitleTripletBean;


public class TestSubtitleDaoImpl {

	@Test
	public void testCreate() {
		SubtitleTripletBean triplet = new SubtitleTripletBean((long) Math.random());
		triplet.setText("Test text");
		triplet.setStart(LocalTime.now());
		triplet.setEnd(LocalTime.now());

		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		subDAO.create(new SubtitleTranslateBean(triplet, ""),"STAR WARS");
	}
	
	@Test
	public void testFindMovie() {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		
		SubtitleFileBean subFile = subDAO.findMovie("STAR WARS");
		assertFalse(subFile.getSubtitles().isEmpty());
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
		
		SubtitleTranslateBean subSet = new SubtitleTranslateBean(tripletSet);
		subDAO.create(subSet,num+".srt");
		SubtitleTranslateBean subGet = subDAO.findEntry(1,num+".srt");
		
		assertEquals(subGet, subSet);
	}

	@Test
	public void testSave() {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		Random rand = new Random();
		int num =Math.abs(rand.nextInt());
		SubtitleFileBean subFile = new SubtitleFileBean();
		subFile.setName(num+".srt");
		SubtitleTripletBean tripletSet = new SubtitleTripletBean(1);
		tripletSet.setStart(LocalTime.parse("00:00:40.190"));
		tripletSet.setEnd(LocalTime.parse("00:01:00.000"));
		tripletSet.setText("Orginally by Bokutox. Fixings by Muhib@Subscene");
		List<SubtitleTranslateBean> subs = new ArrayList<>();
		SubtitleTranslateBean subSet = new SubtitleTranslateBean(tripletSet);
		subs.add(subSet);
		subFile.setSubtitles(subs);
		subDAO.save(subFile);
		
		SubtitleTranslateBean subGet = subDAO.findEntry(1,num+".srt");
		
		assertEquals(subGet, subSet);
	}
	@Test
	public void testUpdate() {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		Random rand = new Random();
		int num =Math.abs(rand.nextInt());
		//Create sub
		SubtitleFileBean subFile = new SubtitleFileBean();
		subFile.setName(num+".srt");
		SubtitleTripletBean tripletSet = new SubtitleTripletBean(1);
		tripletSet.setStart(LocalTime.parse("00:00:40.190"));
		tripletSet.setEnd(LocalTime.parse("00:01:00.000"));
		tripletSet.setText("Orginally by Bokutox. Fixings by Muhib@Subscene");
		List<SubtitleTranslateBean> subs = new ArrayList<>();
		SubtitleTranslateBean subSet= new SubtitleTranslateBean(tripletSet, "");
		subs.add(subSet);
		subFile.setSubtitles(subs);
		subDAO.save(subFile);
		//Update sub
		subs = new ArrayList<>();
		subSet= new SubtitleTranslateBean(tripletSet, "translationRand"+num);
		subs.add(subSet);
		subFile.setSubtitles(subs);
		subDAO.update(subFile);
		
		SubtitleTranslateBean subGet = subDAO.findEntry(1,num+".srt");
		assertEquals(subGet.getTranslation(), subSet.getTranslation());
		assertEquals(subGet, subSet);
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
