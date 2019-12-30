package com.ocsubtitles.manage;

import java.util.List;

import com.ocsubtitles.beans.SubtitleTripletBean;

public interface LineSubtitleManager {
	
public LineSubtitleManager add(String line);

public SubtitleTripletBean getTriplet();

public default void addTriplet(List<SubtitleTripletBean> outputTriplets) {
	}

}
