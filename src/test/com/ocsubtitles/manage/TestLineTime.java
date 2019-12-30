package com.ocsubtitles.manage;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import com.ocsubtitles.beans.SubtitleTripletBean;

public class TestLineTime {
	private LineSubtitleManager line;
	private SubtitleTripletBean triplet;
	
	@Before
	public void setUp() throws Exception {
		triplet = new SubtitleTripletBean();
		triplet.setText("Text");
		line = new LineTimeManager(triplet);
	}

	@Test
	public void testAddTimeOK() {
		line = line.add("00:01:22,171 --> 00:01:24,164");
		assertEquals(line.getTriplet().getStart(), LocalTime.parse("00:01:22.171"));
		assertEquals(line.getTriplet().getEnd(), LocalTime.parse("00:01:24.164"));
	}

	@Test
	public void testAddTimeKO() {
		line = line.add("00:01:22,171 - 00:01:24,164");
		assertEquals(line.getTriplet().getStart(), LocalTime.parse("00:00:00"));
		assertEquals(line.getTriplet().getEnd(), LocalTime.parse("00:00:00"));
	}
	
	@Test
	public void testGetTriplet() {
		SubtitleTripletBean triplTmp = line.getTriplet();
		assertEquals(triplet, triplTmp);
	}

}
