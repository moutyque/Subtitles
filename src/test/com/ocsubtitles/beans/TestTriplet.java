package com.ocsubtitles.beans;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.apache.tomcat.jni.Local;
import org.junit.Before;
import org.junit.Test;

public class TestTriplet {
private static final SubtitleTripletBean TRIPLET = new SubtitleTripletBean(1);
private SubtitleTripletBean tripletTmp = null;
	@Before
	public void setUp() throws Exception {
		TRIPLET.setText("TEXT");
		TRIPLET.setOriginalFileName("STAR WARS");
		TRIPLET.setStart(LocalTime.MIDNIGHT);
		TRIPLET.setEnd(LocalTime.MAX);
		tripletTmp = new SubtitleTripletBean(TRIPLET.getNumber());
		tripletTmp.setEnd(TRIPLET.getEnd());
		tripletTmp.setOriginalFileName(TRIPLET.getOriginalFileName());
		tripletTmp.setStart(TRIPLET.getStart());
		tripletTmp.setText(TRIPLET.getText());
	}
	

	@Test
	public void testToString() {
		String str = "SubtitleTripletBean [number=" + TRIPLET.getNumber() + ", start=" + TRIPLET.getStart() + ", end=" + TRIPLET.getEnd() + ", text=" + TRIPLET.getText()
		+ ", fileName=" + TRIPLET.getOriginalFileName() + "]";
		assertEquals(str, TRIPLET.toString());
	}

	@Test
	public void testGetNumber() {
		assertEquals(1, TRIPLET.getNumber());
	}

	@Test
	public void testGetStart() {
		assertEquals(LocalTime.MIDNIGHT, TRIPLET.getStart());
	}

	@Test
	public void testGetEnd() {
		assertEquals(LocalTime.MAX, TRIPLET.getEnd());
	}

	@Test
	public void testGetText() {
		assertEquals("TEXT", TRIPLET.getText());
	}

	@Test
	public void testGetOriginalFileName() {
		assertEquals("STAR WARS", TRIPLET.getOriginalFileName());
	}

	@Test
	public void testEqualOK() {
		assertEquals(TRIPLET, tripletTmp);
	}
	
	@Test
	public void testEqualKOText() {
		tripletTmp.setText("LOOOOOL");
		assertNotEquals(TRIPLET, tripletTmp);
		tripletTmp.setText(null);
		assertNotEquals(tripletTmp,TRIPLET);
	}
	
	@Test
	public void testEqualKOStart() {
		tripletTmp.setStart(LocalTime.MAX);
		assertNotEquals(TRIPLET, tripletTmp);
		tripletTmp.setStart(null);
		assertNotEquals(tripletTmp,TRIPLET);
	}
	
	@Test
	public void testEqualKOEnd() {
		tripletTmp.setEnd(LocalTime.MIN);
		assertNotEquals(TRIPLET, tripletTmp);
		tripletTmp.setEnd(null);
		assertNotEquals(tripletTmp,TRIPLET);
	}
	
	@Test
	public void testEqualKOFileName() {
		tripletTmp.setOriginalFileName("LIL");
		assertNotEquals(TRIPLET, tripletTmp);
		tripletTmp.setOriginalFileName(null);
		assertNotEquals(tripletTmp,TRIPLET);
	}
	
	
	@Test
	public void testEqualKONewTriplet() {
		tripletTmp = new SubtitleTripletBean(33);
		assertNotEquals(tripletTmp,TRIPLET);
	}
	
	@Test
	public void testEqualhasCode() {
		
		assertEquals(TRIPLET.hashCode(),-1895453841);
	}
}
