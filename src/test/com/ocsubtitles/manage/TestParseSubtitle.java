package com.ocsubtitles.manage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.time.LocalTime;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mysql.cj.result.LocalTimeValueFactory;
import com.ocsubtitles.beans.SubtitleFileBean;
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.beans.exceptions.FileFormatException;
import com.ocsubtitles.manage.ParseSubtitle;

public class TestParseSubtitle {
	private String pathResources ="";
	
	
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Before
	public void setUp() {
		pathResources = this.getClass().getPackage().getName().replace(".", "/") + 
		"/resources/";
	}
	@Test
	public void testParseFileOk() throws UnsupportedEncodingException {
		SubtitleFileBean sub = new SubtitleFileBean();
		ParseSubtitle parseSub = new ParseSubtitle(sub);
		ClassLoader classLoader = getClass().getClassLoader();
		
		URL resourcesPath = classLoader.getResource(pathResources+ "testSmall.srt");
		String path = resourcesPath.getPath();
		path = URLDecoder.decode(path, "UTF-8");
		File file = new File(path);
		try {
			parseSub.parse(file);
			SubtitleTripletBean triplet =  sub.getSubtitles().get(0);
			assertEquals(triplet.getNumber(), 1);
			LocalTime date1 = LocalTime.parse("00:00:40.190");
			LocalTime date2 = LocalTime.parse("00:01:00.000");
			assertEquals(triplet.getStart(), date1);
			assertEquals(triplet.getEnd(), date2);
			assertEquals(triplet.getOriginalFileName(), "testSmall.srt");
			assertEquals(triplet.getText(), "Orginally by Bokutox. Fixings by Muhib@Subscene");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testParseFileOkWithNoReturn() throws UnsupportedEncodingException {
		SubtitleFileBean sub = new SubtitleFileBean();
		ParseSubtitle parseSub = new ParseSubtitle(sub);
		ClassLoader classLoader = getClass().getClassLoader();
		
		URL resourcesPath = classLoader.getResource(pathResources+ "testEndFormat.srt");
		String path = resourcesPath.getPath();
		path = URLDecoder.decode(path, "UTF-8");
		File file = new File(path);
		try {
			parseSub.parse(file);
			SubtitleTripletBean triplet =  sub.getSubtitles().get(sub.getSubtitles().size()-1);
			assertEquals(triplet.getNumber(), 4);
			LocalTime date1 = LocalTime.parse("00:01:24.340");
			LocalTime date2 = LocalTime.parse("00:01:27.459");
			assertEquals(triplet.getStart(), date1);
			assertEquals(triplet.getEnd(), date2);
			assertEquals(triplet.getOriginalFileName(), "testEndFormat.srt");
			assertEquals(triplet.getText(), "Pull it in. Go on. Go on. Go on. Pull it in.");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void testParseFileKoNumber() throws Exception {
		ParseSubtitle parseSub = new ParseSubtitle(new SubtitleFileBean());
		ClassLoader classLoader = getClass().getClassLoader();
		URL resourcesPath = classLoader.getResource(pathResources + "testParseNumber.srt");
		String path = resourcesPath.getPath();
		path = URLDecoder.decode(path, "UTF-8");
		File file = new File(path);
		exceptionRule.expect(FileFormatException.class);
		exceptionRule.expectMessage("Failed to parse subtitles files");
		parseSub.parse(file);
	
	}
	
	@Test
	public void testParseFileKoTime() throws Exception {
		ParseSubtitle parseSub = new ParseSubtitle(new SubtitleFileBean());
		ClassLoader classLoader = getClass().getClassLoader();
		URL resourcesPath = classLoader.getResource(pathResources + "testParseTime.srt");
		String path = resourcesPath.getPath();
		path = URLDecoder.decode(path, "UTF-8");
		File file = new File(path);
		exceptionRule.expect(FileFormatException.class);
		exceptionRule.expectMessage("Failed to parse subtitles files");
		parseSub.parse(file);
	
	}

}
