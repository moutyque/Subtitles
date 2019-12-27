package com.ocsubtitles.manage;

import java.util.List;

import com.ocsubtitles.beans.SubtitleTripletBean;

public interface SubtitleLineManager {
	
public SubtitleLineManager add(String line);

public SubtitleTripletBean getTriplet();

public void addTriplet(List<SubtitleTripletBean> outputTriplets);

}
