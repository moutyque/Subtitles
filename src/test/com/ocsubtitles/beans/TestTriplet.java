package com.ocsubtitles.beans;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.apache.tomcat.jni.Local;
import org.junit.Before;
import org.junit.Test;

public class TestTriplet {
private static final SubtitleTripletBean TRIPLET = new SubtitleTripletBean(1);
	@Before
	public void setUp() throws Exception {
		TRIPLET.setText("TEXT");
		
		TRIPLET.setOriginalFileName("STAR WARS");
		TRIPLET.setStart(LocalTime.MIDNIGHT);
		TRIPLET.setEnd(LocalTime.MAX);
		
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

}
