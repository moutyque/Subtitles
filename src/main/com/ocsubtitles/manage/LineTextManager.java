package com.ocsubtitles.manage;

import java.util.List;

import com.ocsubtitles.beans.SubtitleTripletBean;

public class LineTextManager implements LineSubtitleManager {
	SubtitleTripletBean triplet;
	public LineTextManager(SubtitleTripletBean triplet) {
		this.triplet = triplet;
	}

	@Override
	public LineSubtitleManager add(String line) {
	if(line.isEmpty()) {
		return new LineNumberManager(triplet);
	}
	else {
		this.triplet.setText(this.triplet.getText().concat(line));
		return this;
	}

	}
		
	@Override
	public SubtitleTripletBean getTriplet() {
		return triplet;
	}

	@Override
	public void addTriplet(List<SubtitleTripletBean> outputTriplets) {
		
	}
}
