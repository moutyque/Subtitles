package com.ocsubtitles.manage;

import java.io.File;
import java.io.IOException;

import com.ocsubtitles.beans.SubtitleFileBean;

public interface FileParser {


	void parse(File file) throws Exception;
	
}
