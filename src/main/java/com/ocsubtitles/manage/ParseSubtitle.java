package ocsubtitles.manage;

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

import ocsubtitles.beans.SubtitleFileBean;
import ocsubtitles.beans.SubtitleTranslateBean;
import ocsubtitles.beans.SubtitleTripletBean;
import ocsubtitles.beans.exceptions.FileFormatException;

public class ParseSubtitle implements FileParser {
	
	private final static Logger LOGGER = Logger.getLogger(ParseSubtitle.class.getName());
	private SubtitleFileBean subtitleFile = new SubtitleFileBean();
	public ParseSubtitle(SubtitleFileBean subtitleFile) {
		this.subtitleFile = subtitleFile;
	}
	
	@Override
	public void parse(File file) throws Exception {
		
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(file.toPath())) {
			list = stream.collect(Collectors.toList());
			//Avoid bad formating
			if(!list.get(list.size() - 1).isEmpty()) {
				list.add("");
			}
			checkFileCompliance(list);
		} catch (IOException | FileFormatException e) {
			throw e;
		}

	}
	private boolean checkFileCompliance(List<String> lines) throws FileFormatException {
	boolean returnValue =true;
	int i=0;
	List<SubtitleTranslateBean> outputList = new ArrayList<>();
	
		try {
			LineSubtitleManager subtileLine = new LineNumberManager(new SubtitleTripletBean());
			for(i = 0; i< lines.size();i++ ) {
				subtileLine =subtileLine.add(lines.get(i));
				subtileLine.addSub(outputList);
			}
			subtitleFile.setSubtitles(outputList);
		}
		catch(Exception e) {
			StringBuilder sb = new StringBuilder();
			sb.append("Failed to parse subtitles files").append("\n").append("Error on line : ").append(String.valueOf(i+1)).append(e.toString());
			subtitleFile.setSubtitles(new ArrayList<SubtitleTranslateBean>());
			throw new FileFormatException(sb.toString());

		}		
		return returnValue;
		
	}

	
}
