package com.ocsubtitles.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;



public class TestDAOFactory {

	@Test
	public void testGetInstance() {
	DAOFactory subDao =	DAOFactory.getInstance();
	}
	
	@Test
	public void testPrepareStatement() throws SQLException {
	DAOFactory subDao =	DAOFactory.getInstance();
	Connection connection = subDao.getConnection();
	
	
	PreparedStatement prepStat =   subDao.initialisationRequetePreparee(connection, "SELECT * FROM Subtitles ;", false);
	ResultSet res =  prepStat.executeQuery();
	assertEquals(1, res.findColumn("number"));
	assertEquals(2, res.findColumn("start"));
	assertEquals(3, res.findColumn("end"));
	assertEquals(4, res.findColumn("text"));
	
	}



}
