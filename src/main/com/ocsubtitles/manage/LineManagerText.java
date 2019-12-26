package com.ocsubtitles.manage;

import java.util.List;

import com.ocsubtitles.beans.SubtitleTripletBean;

public class LineManagerText implements SubtitleLineManager {
	SubtitleTripletBean triplet;
	public LineManagerText(SubtitleTripletBean triplet) {
		this.triplet = triplet;
	}

	@Override
	public SubtitleLineManager add(String line,List<SubtitleTripletBean> list) {
	if(line.isEmpty()) {
		list.add(this.triplet);
		return new LineManagerNumber(triplet);
	}
	else {
		this.triplet.setText(this.triplet.getText().concat(line));
		return this;
	}

	}

}
