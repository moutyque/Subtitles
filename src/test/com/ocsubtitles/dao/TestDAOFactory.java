package com.ocsubtitles.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ocsubtitles.dao.DAOFactory;
import com.ocsubtitles.dao.SubtitleDao;

public class TestDAOFactory {

	@Test
	public void testGetInstance() {
	DAOFactory subDao =	DAOFactory.getInstance();
	}

}
