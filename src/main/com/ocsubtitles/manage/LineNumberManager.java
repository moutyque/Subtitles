package com.ocsubtitles.manage;

import java.util.List;

import com.ocsubtitles.beans.SubtitleTripletBean;

public class LineNumberManager implements LineSubtitleManager {
	SubtitleTripletBean triplet;
	public LineNumberManager(SubtitleTripletBean triplet) {
			this.triplet = triplet;
	}
	@Override
	public LineSubtitleManager add(String line) {
		triplet = new SubtitleTripletBean(Integer.parseInt(line.trim()));
		return new LineTimeManager(triplet);
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
