package ocsubtitles.manage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ocsubtitles.beans.SubtitleTripletBean;
import ocsubtitles.beans.exceptions.FileFormatException;

public class LineTimeManager implements LineSubtitleManager {
	SubtitleTripletBean triplet;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");

	public LineTimeManager(SubtitleTripletBean triplet) {
		this.triplet = triplet;
	}

	@Override
	public LineSubtitleManager add(String line)  {
		LocalTime[] parsedTime;
		try {
			parsedTime = parseTime(line);
			this.triplet.setStart(parsedTime[0]);
			this.triplet.setEnd(parsedTime[1]);
		} catch (FileFormatException e) {
			System.err.println(e.toString());
			this.triplet.setStart(LocalTime.MIDNIGHT);
			this.triplet.setEnd(LocalTime.MIDNIGHT);
		}
		return new LineTextManager(triplet);
	}
	private LocalTime[] parseTime(String textTime) throws FileFormatException {
		LocalTime[] parsedTime = new LocalTime[2];
		if(textTime.contains(SubtitleTripletBean.splitTimeString)) {
			String[] splitTime = textTime.split(SubtitleTripletBean.splitTimeString);
			parsedTime[0] = LocalTime.parse(splitTime[0], formatter);
			parsedTime[1] = LocalTime.parse(splitTime[1], formatter);
		}
		else {
			throw new FileFormatException("The format of time is incorrect : " + textTime);
		}
		return parsedTime;
	}
	@Override
	public SubtitleTripletBean getTriplet() {
		return triplet;
	}
}
