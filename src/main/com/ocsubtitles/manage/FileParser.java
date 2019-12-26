package com.ocsubtitles.manage;

import java.io.File;
import java.io.IOException;

public interface FileParser {

	public void parse(File file) throws IOException, Exception;
	
}
