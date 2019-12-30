package com.ocsubtitles.manage;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ocsubtitles.beans.SubtitleTripletBean;

public class TestLineNumberManager {
private LineNumberManager line;
private SubtitleTripletBean triplet;
	@Before
	public void setUp() throws Exception {
		triplet = new SubtitleTripletBean();
		triplet.setText("Text");
		line = new LineNumberManager(triplet);
	}


	@Test
	public void testAdd() {
		line.add("1");
		assertEquals(line.getTriplet().getNumber(), 1);
		
	}

	@Test
	public void testGetTriplet() {
		SubtitleTripletBean triplTmp = line.getTriplet();
		assertEquals(triplet, triplTmp);
	}

}
