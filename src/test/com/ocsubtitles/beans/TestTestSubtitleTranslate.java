package com.ocsubtitles.beans;

import static org.junit.Assert.*;

import java.time.LocalTime;


import org.junit.Before;
import org.junit.Test;

public class TestTestSubtitleTranslate {
private SubtitleTranslateBean translation;
private static final String TRIPLET_TEXT="TEXT EN";
private static final String TRANSLATION_TEXT="TRANSLATION";
	@Before
	public void setUp() {
		SubtitleTripletBean triplet = new SubtitleTripletBean(1);
		triplet.setText(TRIPLET_TEXT);
		triplet.setStart(LocalTime.MIDNIGHT);
		triplet.setEnd(LocalTime.MAX);
		translation = new SubtitleTranslateBean(triplet, TRANSLATION_TEXT);
		
	}

	@Test
	public void testToString() {
		assertEquals(translation.toString(), 
				"SubtitleTranslateBean [triplet=" + "SubtitleTripletBean [number=" + 1 + ", start=" + LocalTime.MIDNIGHT.toString() +
				", end=" + LocalTime.MAX.toString() + ", text=" + TRIPLET_TEXT
				+ "]" + ", translation=" + TRANSLATION_TEXT + "]");
		
	}

	@Test
	public void testGetNumber() {
		assertTrue(translation.getNumber()==1);
	}

	@Test
	public void testGetStart() {
		assertTrue(translation.getStart().equals(LocalTime.MIDNIGHT.toString()));
	}

	@Test
	public void testGetEnd() {
		assertTrue(translation.getEnd().equals(LocalTime.MAX.toString()));
		
	}

	@Test
	public void testGetText() {
		assertTrue(translation.getText().equals(TRIPLET_TEXT));	}

	@Test
	public void testGetTranslation() {
		assertTrue(translation.getTranslation().equals(TRANSLATION_TEXT));	
	}

	@Test
	public void testSetTranslation() {
		String newTxt = "NEW TRANSLATION";
		translation.setTranslation(newTxt);
		assertTrue(translation.getTranslation().equals(newTxt));	
	}

}
