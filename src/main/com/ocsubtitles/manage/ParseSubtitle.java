package com.ocsubtitles.manage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.beans.exceptions.FileFormatException;

public class ParseSubtitle implements FileParser {
	
	private final static Logger LOGGER = Logger.getLogger(ParseSubtitle.class.getName());
	
	private List<SubtitleTripletBean> subtitles = new ArrayList<SubtitleTripletBean>();
	@Override
	public void parse(File file) throws Exception {
		
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(file.toPath())) {
			list = stream.collect(Collectors.toList());
			//Avoid bad formating
			if(!list.get(list.size() - 1).isEmpty()) list.add("\n");
			checkFileCompliance(list);
			subtitles.forEach(s->s.setMovieName(file.getName()));
			
		} catch (IOException | FileFormatException e) {
			throw e;
		}

	}
	private boolean checkFileCompliance(List<String> list) throws FileFormatException {
	boolean returnValue =true;
	int i=0;
		try {
			
			SubtitleLineManager subtileLine = new LineManagerNumber(new SubtitleTripletBean());
			for(i = 0; i< list.size();i++ ) {
				subtileLine = subtileLine.add(list.get(i),subtitles);
				
			}
		}
		catch(Exception e) {
			StringBuilder sb = new StringBuilder();
			sb.append("Failed to parse subtitles files").append("\n").append("Error on line : ").append(String.valueOf(i+1)).append(e.toString());
			throw new FileFormatException(sb.toString());

		}
		
		
		return returnValue;
		
	}

	public List<SubtitleTripletBean> getSubtitles(){
		return this.subtitles;
	}
	
}
