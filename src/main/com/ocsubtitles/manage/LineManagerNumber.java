package com.ocsubtitles.manage;

import java.util.List;

import com.ocsubtitles.beans.SubtitleTripletBean;

public class LineManagerNumber implements SubtitleLineManager {
	SubtitleTripletBean triplet;
	public LineManagerNumber(SubtitleTripletBean triplet) {
			this.triplet = triplet;
	}
	@Override
	public SubtitleLineManager add(String line, List<SubtitleTripletBean> list) {
		triplet = new SubtitleTripletBean(Integer.parseInt(line.trim()));
		return new LineManagerTime(triplet);
	}


}
