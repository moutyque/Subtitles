package com.ocsubtitles.manage;

import java.util.List;

import com.ocsubtitles.beans.SubtitleTripletBean;

public class LineManagerText implements SubtitleLineManager {
	SubtitleTripletBean triplet;
	public LineManagerText(SubtitleTripletBean triplet) {
		this.triplet = triplet;
	}

	@Override
	public SubtitleLineManager add(String line) {
	if(line.isEmpty()) {
		return new LineManagerNumber(triplet);
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
