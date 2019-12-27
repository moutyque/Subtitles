package com.ocsubtitles.manage;

import java.util.List;

import com.ocsubtitles.beans.SubtitleTripletBean;

public class LineManagerNumber implements SubtitleLineManager {
	SubtitleTripletBean triplet;
	public LineManagerNumber(SubtitleTripletBean triplet) {
			this.triplet = triplet;
	}
	@Override
	public SubtitleLineManager add(String line) {
		triplet = new SubtitleTripletBean(Integer.parseInt(line.trim()));
		return new LineManagerTime(triplet);
	}
	@Override
	public SubtitleTripletBean getTriplet() {
		return triplet;
	}
	@Override
	public void addTriplet(List<SubtitleTripletBean> outputTriplets) {
		outputTriplets.add(triplet);
	}


}
