package com.ocsubtitles.beans;

import static org.junit.Assert.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.time.LocalTime;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.beans.exceptions.FileFormatException;
import com.ocsubtitles.manage.ParseSubtitle;

public class TestParseSubtitle {
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void testParseFileOk() throws UnsupportedEncodingException {
		ParseSubtitle parseSub = new ParseSubtitle(new SubtitleFileBean());
		ClassLoader classLoader = getClass().getClassLoader();
		URL resourcesPath = classLoader.getResource("The Irishman_en.srt");
		String path = resourcesPath.getPath();
		path = URLDecoder.decode(path, "UTF-8");
		File file = new File(path);
		try {
			parseSub.parse(file);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testParseFileKoNumber() throws Exception {
		ParseSubtitle parseSub = new ParseSubtitle(new SubtitleFileBean());
		ClassLoader classLoader = getClass().getClassLoader();
		URL resourcesPath = classLoader.getResource("testParseNumber.srt");
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
		URL resourcesPath = classLoader.getResource("testParseTime.srt");
		String path = resourcesPath.getPath();
		path = URLDecoder.decode(path, "UTF-8");
		File file = new File(path);
		exceptionRule.expect(FileFormatException.class);
		exceptionRule.expectMessage("Failed to parse subtitles files");
		parseSub.parse(file);
	
	}

	@Test
	public void testSomeSub() throws UnsupportedEncodingException {
		SubtitleFileBean sampleSub = new SubtitleFileBean();
		ParseSubtitle parseSub = new ParseSubtitle(sampleSub);
		ClassLoader classLoader = getClass().getClassLoader();
		URL resourcesPath = classLoader.getResource("testSmall.srt");
		String path = resourcesPath.getPath();
		path = URLDecoder.decode(path, "UTF-8");
		File file = new File(path);
		try {
			parseSub.parse(file);
			List<SubtitleTripletBean> subs = sampleSub.getSubtitles();
			SubtitleTripletBean triplet = subs.get(0);
			assertTrue(triplet.getNumber()==1);
			assertTrue(triplet.getText().equals("until I wasn't no more.\n"));
			LocalTime date1 = LocalTime.parse("00:01:57,000");
			LocalTime date2 = LocalTime.parse("00:01:58,750");
			assertTrue(triplet.getStart().equals(date1));
			assertTrue(triplet.getEnd().equals(date2));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
