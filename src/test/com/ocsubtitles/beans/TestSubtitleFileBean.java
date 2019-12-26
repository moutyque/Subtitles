package com.ocsubtitles.beans;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestSubtitleFileBean {
	SubtitleFileBean subFile;

	@Before
	public void setUp() {
		subFile = new SubtitleFileBean();

	}

	@Test
	public void testGetName() {
		subFile.setName("Test");
		assertEquals("Test", subFile.getName());
	}

	@Test
	public void testGetPath() {
		subFile.setPath("C:\\Document\\File.txt");
		assertEquals("C:\\Document\\File.txt", subFile.getPath());
	}


}
